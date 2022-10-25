package com.ruoyi.datasync.support;

import java.io.Serializable;

/**
 * 字段和表头实体
 * 
 * @author qubo
 */
public class TableColumn implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 表头 */
    private String title;
    /** 字段 */
    private String field;

    public TableColumn()
    {

    }

    public TableColumn(String title, String field)
    {
        this.title = title;
        this.field = field;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}
