package com.ruoyi.datasync.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 同构数据同步配置对象 DS_SAME_DATA_DBCONFIG
 * 
 * @author qubo
 * @date 2022-10-11
 */
public class DsSameDataDbConfig extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 唯一标识 */
    private Long id;

    /** 源数据库id */
    @Excel(name = "源数据库id")
    private String sourceDbId;
    private String sourceNodeId;
    private String sourceDbName;

    /** 目标数据库id */
    @Excel(name = "目标数据库id")
    private String targetDbId;
    private String targetNodeId;
    private String targetDbName;

    /** 源模式名 */
    @Excel(name = "源模式名")
    private String sourceSchema;

    /** 目标模式名 */
    @Excel(name = "目标模式名")
    private String targetSchema;

    /** 源表名 */
    @Excel(name = "源表名")
    private String sourceTable;

    /** 目标表名 */
    @Excel(name = "目标表名")
    private String targetTable;

    /** 是否启用 */
    private String isUse;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSourceDbId() {
        return sourceDbId;
    }

    public void setSourceDbId(String sourceDbId) {
        this.sourceDbId = sourceDbId;
    }

    public String getSourceDbName() {
        return sourceDbName;
    }

    public void setSourceDbName(String sourceDbName) {
        this.sourceDbName = sourceDbName;
    }

    public String getTargetDbId() {
        return targetDbId;
    }

    public void setTargetDbId(String targetDbId) {
        this.targetDbId = targetDbId;
    }

    public String getTargetDbName() {
        return targetDbName;
    }

    public void setTargetDbName(String targetDbName) {
        this.targetDbName = targetDbName;
    }

    public String getSourceSchema() {
        return sourceSchema;
    }

    public void setSourceSchema(String sourceSchema) {
        this.sourceSchema = sourceSchema;
    }

    public String getTargetSchema() {
        return targetSchema;
    }

    public void setTargetSchema(String targetSchema) {
        this.targetSchema = targetSchema;
    }

    public String getSourceTable() {
        return sourceTable;
    }

    public void setSourceTable(String sourceTable) {
        this.sourceTable = sourceTable;
    }

    public String getTargetTable() {
        return targetTable;
    }

    public void setTargetTable(String targetTable) {
        this.targetTable = targetTable;
    }

    public String getIsUse() {
        return isUse;
    }

    public void setIsUse(String isUse) {
        this.isUse = isUse;
    }

    public String getSourceNodeId() {
        return sourceNodeId;
    }

    public void setSourceNodeId(String sourceNodeId) {
        this.sourceNodeId = sourceNodeId;
    }

    public String getTargetNodeId() {
        return targetNodeId;
    }

    public void setTargetNodeId(String targetNodeId) {
        this.targetNodeId = targetNodeId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("sourceDbId", getSourceDbId())
            .append("targetDbId", getTargetDbId())
            .append("sourceTable", getSourceTable())
            .append("targetTable", getTargetTable())
            .toString();
    }
}
