package com.ruoyi.datasync.domain;

import java.io.Serializable;
import java.util.List;

public class DsConfigResult implements Serializable {

    /** 中心名称 */
    private String coreName;
    /** 是否主节点 */
    private String isMaster;
    /** 节点ip */
    private String nodeIp;
    /** 节点下数据源配置 */
    private List<DsNodeDbConfig> dbConfigList;
    /** 节点下数据同步配置 */
    private List<DsSameDataDbConfig> sameDBSyncConfigList;

    public String getCoreName() {
        return coreName;
    }

    public void setCoreName(String coreName) {
        this.coreName = coreName;
    }

    public String getIsMaster() {
        return isMaster;
    }

    public void setIsMaster(String isMaster) {
        this.isMaster = isMaster;
    }

    public String getNodeIp() {
        return nodeIp;
    }

    public void setNodeIp(String nodeIp) {
        this.nodeIp = nodeIp;
    }

    public List<DsNodeDbConfig> getDbConfigList() {
        return dbConfigList;
    }

    public void setDbConfigList(List<DsNodeDbConfig> dbConfigList) {
        this.dbConfigList = dbConfigList;
    }

    public List<DsSameDataDbConfig> getSameDBSyncConfigList() {
        return sameDBSyncConfigList;
    }

    public void setSameDBSyncConfigList(List<DsSameDataDbConfig> sameDBSyncConfigList) {
        this.sameDBSyncConfigList = sameDBSyncConfigList;
    }
}
