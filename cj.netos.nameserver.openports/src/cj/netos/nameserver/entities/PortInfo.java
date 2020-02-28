package cj.netos.nameserver.entities;

public class PortInfo {
    String routerName;
    String nodeName;
    String openports;

    public String getRouterName() {
        return routerName;
    }

    public void setRouterName(String routerName) {
        this.routerName = routerName;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getOpenports() {
        return openports;
    }

    public void setOpenports(String openports) {
        this.openports = openports;
    }
    public String getKey(){
        return String.format("%s.%s", routerName, nodeName);
    }
}
