package com.ruoyi.datasync.support;

import java.io.Serializable;

/**
 * Ztree树结构实体类
 * 
 * @author qubo
 */
public class SchemaTree implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 节点ID */
    private String id;

    /** 节点父ID */
    private String pId;

    /** 节点名称 */
    private String name;

    /** 节点标题 */
    private String title;

    public SchemaTree() {
    }

    public SchemaTree(String id, String pId, String name, String title) {
        this.id = id;
        this.pId = pId;
        this.name = name;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

}
