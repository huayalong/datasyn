package com.ruoyi.datasync.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 节点中心数据源配置对象 DS_NODE_DB_CONFIG
 * 
 * @author qubo
 * @date 2022-10-11
 */
public class DsNodeDbConfig extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 唯一标识 */
    private Long id;

    /** 所属节点 */
    @Excel(name = "所属节点")
    private String nodeId;
    private String coreName; //节点名称

    /** 实例端口 */
    @Excel(name = "实例端口")
    private String instancePort;

    /** 数据库名 */
    @Excel(name = "数据库名")
    private String instanceDb;

    /** 数据库用户名 */
    @Excel(name = "数据库用户名")
    private String userName;

    /** 数据库连接密码 */
    @Excel(name = "数据库连接密码")
    private String passWord;

    /** 实例名称 */
    @Excel(name = "实例名称")
    private String instanceName;

    /** 实例IP */
    @Excel(name = "实例IP")
    private String instanceIp;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setNodeId(String nodeId) 
    {
        this.nodeId = nodeId;
    }

    public String getNodeId() 
    {
        return nodeId;
    }
    public void setInstancePort(String instancePort) 
    {
        this.instancePort = instancePort;
    }

    public String getInstancePort() 
    {
        return instancePort;
    }
    public void setInstanceDb(String instanceDb) 
    {
        this.instanceDb = instanceDb;
    }

    public String getInstanceDb() 
    {
        return instanceDb;
    }
    public void setUserName(String userName) 
    {
        this.userName = userName;
    }

    public String getUserName() 
    {
        return userName;
    }
    public void setPassWord(String passWord) 
    {
        this.passWord = passWord;
    }

    public String getPassWord() 
    {
        return passWord;
    }
    public void setInstanceName(String instanceName) 
    {
        this.instanceName = instanceName;
    }

    public String getInstanceName() 
    {
        return instanceName;
    }
    public void setInstanceIp(String instanceIp) 
    {
        this.instanceIp = instanceIp;
    }

    public String getInstanceIp() 
    {
        return instanceIp;
    }

    public String getCoreName() {
        return coreName;
    }

    public void setCoreName(String coreName) {
        this.coreName = coreName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("nodeId", getNodeId())
            .append("instancePort", getInstancePort())
            .append("instanceDb", getInstanceDb())
            .append("userName", getUserName())
            .append("passWord", getPassWord())
            .append("instanceName", getInstanceName())
            .append("instanceIp", getInstanceIp())
            .append("createTime", getCreateTime())
            .toString();
    }
}
