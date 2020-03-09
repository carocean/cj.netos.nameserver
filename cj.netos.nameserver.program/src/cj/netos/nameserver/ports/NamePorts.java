package cj.netos.nameserver.ports;

import cj.lns.chip.sos.cube.framework.ICube;
import cj.lns.chip.sos.cube.framework.IDocument;
import cj.lns.chip.sos.cube.framework.IQuery;
import cj.netos.nameserver.entities.NodeState;
import cj.netos.nameserver.entities.PortInfo;
import cj.netos.nameserver.openports.INamePorts;
import cj.studio.ecm.annotation.CjService;
import cj.studio.ecm.annotation.CjServiceRef;
import cj.studio.ecm.net.CircuitException;
import cj.studio.openport.ISecuritySession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CjService(name = "/nameports.service")
public class NamePorts implements INamePorts {
    final String _KEY_COL_NAME = "flow.network.nodes";
    @CjServiceRef(refByName = "mongodb.netos.home")
    ICube home;

    @Override
    public List<PortInfo> workablePortList(ISecuritySession securitySession) throws CircuitException {
        String cjql = "select {'tuple.peerName':1,'tuple.ports.openports':1} from tuple ?(colname) ?(clazz) where {'tuple.isOpened':true}";
        IQuery<Map<String, Object>> query = home.createQuery(cjql);
        query.setParameter("colname", _KEY_COL_NAME);
        query.setParameter("clazz", HashMap.class.getName());
        List<PortInfo> list = new ArrayList<>();
        List<IDocument<Map<String, Object>>> docs = query.getResultList();
        for (IDocument<Map<String, Object>> doc : docs) {
            PortInfo portInfo = new PortInfo();
            portInfo.setNodeName(doc.tuple().get("peerName") + "");
            portInfo.setOpenports(((Map<String,Object>)doc.tuple().get("ports")).get("openports") + "");
            list.add(portInfo);
        }
        return list;
    }

}
