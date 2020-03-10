package cj.netos.nameserver.ports;

import cj.lns.chip.sos.cube.framework.ICube;
import cj.lns.chip.sos.cube.framework.IDocument;
import cj.lns.chip.sos.cube.framework.IQuery;
import cj.netos.nameserver.openports.INamePorts;
import cj.studio.ecm.annotation.CjService;
import cj.studio.ecm.annotation.CjServiceRef;
import cj.studio.ecm.net.CircuitException;
import cj.studio.openport.ISecuritySession;

import java.util.ArrayList;
import java.util.List;

@CjService(name = "/nameports.service")
public class NamePorts implements INamePorts {
    final String _KEY_COL_NAME = "flow.network.nodes";
    @CjServiceRef(refByName = "mongodb.netos.home")
    ICube home;

    @Override
    public List<String> workablePortList(ISecuritySession securitySession) throws CircuitException {
        String cjql = "select {'tuple.ports.openports':1}.distinct() from tuple ?(colname) ?(clazz) where {'tuple.isOpened':true}";
        IQuery<String> query = home.createQuery(cjql);
        query.setParameter("colname", _KEY_COL_NAME);
        query.setParameter("clazz", String.class.getName());
        List<String> list = new ArrayList<>();
        List<IDocument<String>> docs = query.getResultList();
        for (IDocument<String> doc : docs) {
            String openports = doc.tuple();
            list.add(openports);
        }
        return list;
    }

}
