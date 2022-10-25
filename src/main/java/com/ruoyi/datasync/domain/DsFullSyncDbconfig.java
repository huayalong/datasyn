package com.ruoyi.datasync.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 全量数据同步配置对象 DS_FULL_SYNC_DBCONFIG
 * 
 * @author qubo
 * @date 2022-10-13
 */
public class DsFullSyncDbconfig extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 唯一标识 */
    private Long id;

    /** 源数据库id */
    @Excel(name = "源数据库id")
    private String sourceDbId;
    private String sourceDbName;

    /** 目标数据库id */
    @Excel(name = "目标数据库id")
    private String targetDbId;
    private String targetDbName;

    /** 源数据库模式 */
    @Excel(name = "源数据库模式")
    private String sourceSchema;

    /** 目标数据库模式 */
    @Excel(name = "目标数据库模式")
    private String targetSchema;

    /** 源表名 */
    @Excel(name = "源表名")
    private String sourceTable;

    /** 目标表名 */
    @Excel(name = "目标表名")
    private String targetTable;

    /** 同步级别 */
    @Excel(name = "同步级别")
    private String syncLevel;

    /** 是否开启（生效） */
    @Excel(name = "是否开启", readConverterExp = "生=效")
    private String isUse;

    /** 最后一次同步时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "最后一次同步时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date lastSyncTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setSourceDbId(String sourceDbId)
    {
        this.sourceDbId = sourceDbId;
    }

    public String getSourceDbId() 
    {
        return sourceDbId;
    }
    public void setTargetDbId(String targetDbId) 
    {
        this.targetDbId = targetDbId;
    }

    public String getTargetDbId() 
    {
        return targetDbId;
    }
    public void setSourceSchema(String sourceSchema) 
    {
        this.sourceSchema = sourceSchema;
    }

    public String getSourceSchema() 
    {
        return sourceSchema;
    }
    public void setTargetSchema(String targetSchema) 
    {
        this.targetSchema = targetSchema;
    }

    public String getTargetSchema() 
    {
        return targetSchema;
    }
    public void setSourceTable(String sourceTable) 
    {
        this.sourceTable = sourceTable;
    }

    public String getSourceTable() 
    {
        return sourceTable;
    }
    public void setTargetTable(String targetTable) 
    {
        this.targetTable = targetTable;
    }

    public String getTargetTable() 
    {
        return targetTable;
    }
    public void setSyncLevel(String syncLevel) 
    {
        this.syncLevel = syncLevel;
    }

    public String getSyncLevel() 
    {
        return syncLevel;
    }
    public void setIsUse(String isUse) 
    {
        this.isUse = isUse;
    }

    public String getIsUse() 
    {
        return isUse;
    }
    public void setLastSyncTime(Date lastSyncTime) 
    {
        this.lastSyncTime = lastSyncTime;
    }

    public Date getLastSyncTime() 
    {
        return lastSyncTime;
    }

    public String getSourceDbName() {
        return sourceDbName;
    }

    public void setSourceDbName(String sourceDbName) {
        this.sourceDbName = sourceDbName;
    }

    public String getTargetDbName() {
        return targetDbName;
    }

    public void setTargetDbName(String targetDbName) {
        this.targetDbName = targetDbName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("sourceDbId", getSourceDbId())
            .append("targetDbId", getTargetDbId())
            .append("sourceSchema", getSourceSchema())
            .append("targetSchema", getTargetSchema())
            .append("sourceTable", getSourceTable())
            .append("targetTable", getTargetTable())
            .append("syncLevel", getSyncLevel())
            .append("isUse", getIsUse())
            .append("lastSyncTime", getLastSyncTime())
            .append("createTime", getCreateTime())
            .toString();
    }
}
