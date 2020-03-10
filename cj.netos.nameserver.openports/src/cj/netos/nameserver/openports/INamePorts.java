package cj.netos.nameserver.openports;

import cj.studio.ecm.net.CircuitException;
import cj.studio.openport.IOpenportService;
import cj.studio.openport.ISecuritySession;
import cj.studio.openport.annotations.CjOpenport;
import cj.studio.openport.annotations.CjOpenports;

import java.util.List;
@CjOpenports(usage = "network开放口服务")
public interface INamePorts extends IOpenportService {
    @CjOpenport(usage = "获取可用的口地址列表")
    List<String> workablePortList(
            ISecuritySession securitySession
    ) throws CircuitException;
}
