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
import cj.studio.openport.annotations.CjOpenport;

import java.util.ArrayList;
import java.util.List;

@CjService(name = "/nameports.service")
public class NamePorts implements INamePorts {
    @CjServiceRef(refByName = "mongodb.netos.home")
    ICube home;

    @Override
    public List<PortInfo> workablePortList(ISecuritySession securitySession) throws CircuitException {
        String cjql = "select {'tuple':'*'} from tuple router.nodes.states ?(clazz) where {'tuple.isRunning':'true'}";
        IQuery<NodeState> query = home.createQuery(cjql);
        query.setParameter("clazz", NodeState.class.getName());
        List<IDocument<NodeState>> docs = query.getResultList();
        List<PortInfo> list = new ArrayList<>();
        for (IDocument<NodeState> doc : docs) {
            NodeState state = doc.tuple();
            PortInfo info = _getPortinfo(state);
            if (info != null) {
                list.add(info);
            }
        }
        return null;
    }

    private PortInfo _getPortinfo(NodeState state) {
        String cjql = "select {'tuple':'*'}.limit(1) from tuple router.nodes.ports ?(clazz) where {'tuple.routerName':'?(routerName)','tuple.nodeName':'?(nodeName)'}";
        IQuery<PortInfo> query = home.createQuery(cjql);
        query.setParameter("clazz", PortInfo.class.getName());
        query.setParameter("routerName", state.getRouterName());
        query.setParameter("nodeName", state.getNodeName());
        IDocument<PortInfo> doc = query.getSingleResult();
        if (doc == null) {
            return null;
        }
        return doc.tuple();
    }
}
