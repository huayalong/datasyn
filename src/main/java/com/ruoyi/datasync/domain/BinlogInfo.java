package com.ruoyi.datasync.domain;

/**
 * binlog数据信息 实体
 */
public class BinlogInfo {
    // 日志文件名
    private String logFileName;
    // 操作类型
    private String eventType;
    // 操作用户
    private String userName;
    // 服务id
    private String serverId;
    // 当前日志文件大小数值
    private String endLogPos;

    private String csf;
    // 其它操作信息（操作的表名等）
    private String eventInfo;
    // 操作sql
    private String redoSql;
    //sql需要同步得节点得ip
    private String targetNodeIps;

    public String getTargetNodeIps() {
        return targetNodeIps;
    }

    public void setTargetNodeIps(String targetNodeIps) {
        this.targetNodeIps = targetNodeIps;
    }

    public String getLogFileName() {
        return logFileName;
    }

    public void setLogFileName(String logFileName) {
        this.logFileName = logFileName;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public String getEndLogPos() {
        return endLogPos;
    }

    public void setEndLogPos(String endLogPos) {
        this.endLogPos = endLogPos;
    }

    public String getCsf() {
        return csf;
    }

    public void setCsf(String csf) {
        this.csf = csf;
    }

    public String getEventInfo() {
        return eventInfo;
    }

    public void setEventInfo(String eventInfo) {
        this.eventInfo = eventInfo;
    }

    public String getRedoSql() {
        return redoSql;
    }

    public void setRedoSql(String redoSql) {
        this.redoSql = redoSql;
    }
}
