package com.ruoyi.datasync.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 节点中心配置对象 DS_NODE_CORE_CONFIG
 * 
 * @author qubo
 * @date 2022-10-11
 */
public class DsNodeCoreConfig extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 唯一标识 */
    private Long id;

    /** 中心名称 */
    @Excel(name = "中心名称")
    private String coreName;

    /** 是否主节点 */
    @Excel(name = "是否主节点")
    private String isMaster;

    /** 节点ip */
    @Excel(name = "节点ip")
    private String nodeIp;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public String getCoreName() {
        return coreName;
    }

    public void setCoreName(String coreName) {
        this.coreName = coreName;
    }

    public void setIsMaster(String isMaster)
    {
        this.isMaster = isMaster;
    }

    public String getIsMaster() 
    {
        return isMaster;
    }
    public void setNodeIp(String nodeIp) 
    {
        this.nodeIp = nodeIp;
    }

    public String getNodeIp() 
    {
        return nodeIp;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("coreName", getCoreName())
            .append("isMaster", getIsMaster())
            .append("nodeIp", getNodeIp())
            .append("createTime", getCreateTime())
            .toString();
    }
}
