package com.ruoyi.datasync.support;

/**
 * 通用常量信息
 * 
 * @author ruoyi
 */
public class SqlProperty
{
    /**
     * 获取所有模式SQL
     */
    public static final String ALL_SCHEMA_SQL = "SELECT TABLE_SCHEM FROM V_SYS_SCHEMAS";

    /**
     * 根据模式获取所有表SQL
     */
    public static final String ALL_TABLE_SQL = "SELECT TABLE_NAME FROM all_tables WHERE OWNER = '#SCHEMA#'";

    /**
     * 根据模式和表名获取所有字段SQL
     */
    public static final String ALL_COLUMN_SQL = "SELECT COLUMN_NAME FROM V_SYS_COLUMNS " +
            "WHERE TABLE_NAME = '#TABLE#' AND TABLE_SCHEM = '#SCHEMA#' AND COLUMN_NAME NOT IN ('SYSATTR_ROWVERSION', 'ROWID')";

    /**
     * 根据模式和表名获取所有字段SQL
     */
    public static final String SELECT_DATA_SQL = "SELECT * FROM #SCHEMA#.#TABLE# LIMIT 10";

    /**
     * 逻辑备份命令
     */
    public static final String BACKUP_CMD = " osrexp -u#USER#/#PASSWORD# -h#IP# -p#PORT# -d#DB# level=schema " +
            "file=#BACK_DB_FILE# log=#BACK_LOG_FILE# mode=entirety ignore=false " +
            "schema=#SCHEMA# excludetable=(public.test,sysdba.test) " +
            "view=true sequence=true procedure=true constraint=true trigger=true index=true";

    /**
     * 逻辑恢复命令
     */
    public static final String RESUME_CMD = "osrimp -u#USER#/#PASSWORD# -h#IP# -p#PORT# -d#DB# level=schema " +
            "file=#BACK_DB_FILE# log=#RESUME_LOG_FILE# mode=entirety ignore=true " +
            "schema=#SCHEMA# recreateschema=false view=true sequence=true " +
            "procedure=true recreateotherobject=true " +
            "excludetable=(sysdba.test,public.test) recreatetable=false " +
            "constraint=false deletetabledata=true trigger=false index=false";




}