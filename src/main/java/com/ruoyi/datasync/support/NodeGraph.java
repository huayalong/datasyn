package com.ruoyi.datasync.support;

import java.io.Serializable;
import java.util.List;

/**
 * 节点中心图谱实体
 * 
 * @author qubo
 */
public class NodeGraph implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 节点ID */
    private Topo topo;

    public NodeGraph() {

    }
    public NodeGraph(Topo topo) {
        this.topo = topo;
    }

    public Topo getTopo() {
        return topo;
    }

    public void setTopo(Topo topo) {
        this.topo = topo;
    }

    public static class Topo{
        private List<Node> nodes;
        private List<Link> links;

        public Topo() {
        }

        public Topo(List<Node> nodes, List<Link> links) {
            this.nodes = nodes;
            this.links = links;
        }

        public List<Node> getNodes() {
            return nodes;
        }

        public void setNodes(List<Node> nodes) {
            this.nodes = nodes;
        }

        public List<Link> getLinks() {
            return links;
        }

        public void setLinks(List<Link> links) {
            this.links = links;
        }
    }

    public static class Node{
        private boolean knmp_on = true;
        private boolean snmp_on = true;
        private String ip;
        private int vlan = 1;
        private int flag = 0;
        private String netmask = "255.255.255.0";
        private String device_name;
        private String mac = "00-11-22-33-44-55";
        private boolean ip_on = true;
        private String id;
        private String gateway = "0.0.0.0";

        public Node() {

        }

        public Node(String id, String ip, String device_name) {
            this.id = id;
            this.ip = ip;
            this.device_name = device_name;
        }

        public boolean isKnmp_on() {
            return knmp_on;
        }

        public void setKnmp_on(boolean knmp_on) {
            this.knmp_on = knmp_on;
        }

        public boolean isSnmp_on() {
            return snmp_on;
        }

        public void setSnmp_on(boolean snmp_on) {
            this.snmp_on = snmp_on;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public int getVlan() {
            return vlan;
        }

        public void setVlan(int vlan) {
            this.vlan = vlan;
        }

        public int getFlag() {
            return flag;
        }

        public void setFlag(int flag) {
            this.flag = flag;
        }

        public String getNetmask() {
            return netmask;
        }

        public void setNetmask(String netmask) {
            this.netmask = netmask;
        }

        public String getDevice_name() {
            return device_name;
        }

        public void setDevice_name(String device_name) {
            this.device_name = device_name;
        }

        public String getMac() {
            return mac;
        }

        public void setMac(String mac) {
            this.mac = mac;
        }

        public boolean isIp_on() {
            return ip_on;
        }

        public void setIp_on(boolean ip_on) {
            this.ip_on = ip_on;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getGateway() {
            return gateway;
        }

        public void setGateway(String gateway) {
            this.gateway = gateway;
        }
    }

    public static class Link{
        private String source;
        private String target_port_disp;
        private String source_port_disp;
        private String target;

        public Link(String source, String target) {
            this.source = source;
            this.target = target;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getTarget_port_disp() {
            return target_port_disp;
        }

        public void setTarget_port_disp(String target_port_disp) {
            this.target_port_disp = target_port_disp;
        }

        public String getSource_port_disp() {
            return source_port_disp;
        }

        public void setSource_port_disp(String source_port_disp) {
            this.source_port_disp = source_port_disp;
        }

        public String getTarget() {
            return target;
        }

        public void setTarget(String target) {
            this.target = target;
        }
    }
}
