package cj.netos.nameserver.entities;

public class NodeState {
    String routerName;
    String nodeName;
    String isRunning;

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

    public String isRunning() {
        return isRunning;
    }

    public void setRunning(String running) {
        isRunning = running;
    }
}
