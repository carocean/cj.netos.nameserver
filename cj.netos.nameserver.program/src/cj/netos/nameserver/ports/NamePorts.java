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
    @CjServiceRef(refByName = "mongodb.netos.home")
    ICube home;

    @Override
    public List<PortInfo> workablePortList(ISecuritySession securitySession) throws CircuitException {
        String cjql = "select {'tuple.nodeName':1}.distinct() from tuple router.nodes.states ?(clazz) where {'tuple.isRunning':true}";
        IQuery<String> query = home.createQuery(cjql);
        query.setParameter("clazz", String.class.getName());
        List<IDocument<String>> docs = query.getResultList();
        List<PortInfo> list = new ArrayList<>();
        for (IDocument<String> doc : docs) {
            String nodeName = doc.tuple();
            PortInfo info = _getPortinfo(nodeName);
            if (info != null) {
                list.add(info);
            }
        }
        return list;
    }

    private PortInfo _getPortinfo(String nodeName) {
        String cjql = "select {'tuple':'*'}.limit(1) from tuple router.nodes.ports ?(clazz) where {'tuple.nodeName':'?(nodeName)'}";
        IQuery<PortInfo> query = home.createQuery(cjql);
        query.setParameter("clazz", PortInfo.class.getName());
        query.setParameter("nodeName", nodeName);
        IDocument<PortInfo> doc = query.getSingleResult();
        if (doc == null) {
            return null;
        }
        return doc.tuple();
    }
}
