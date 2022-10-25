package com.ruoyi.datasync.support;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.util.List;

/**
 * 数据库 工具类
 * 
 * @author qubo
 */
public class DBUtil
{

    /**
     * 初始化数据源
     * @return
     */
    public static DriverManagerDataSource initDatasource(String ip, String port, String db, String userName, String passWord)
    {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.oscar.Driver");
        dataSource.setUrl(String.format("jdbc:oscar://%s:%s/%s", ip, port, db));
        dataSource.setUsername(userName);
        dataSource.setPassword(passWord);
        return dataSource;
    }

    //获取数据源下所有模式列表
    public static List<String> getSchemaListBySource(DriverManagerDataSource dataSource)
    {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<String> schemas = jdbcTemplate.queryForList(SqlProperty.ALL_SCHEMA_SQL, String.class);
        return schemas;
    }

    //获取指定模式下所有表
    public static List<String> getTableListBySchema(DriverManagerDataSource dataSource, String schema)
    {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<String> tables = jdbcTemplate.queryForList(SqlProperty.ALL_TABLE_SQL.replace("#SCHEMA#", schema), String.class);
        return tables;
    }

}
