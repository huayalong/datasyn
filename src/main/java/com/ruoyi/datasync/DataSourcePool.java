package com.ruoyi.datasync;

import com.ruoyi.common.utils.CacheMap;
import com.ruoyi.datasync.domain.DsNodeDbConfig;
import com.ruoyi.datasync.domain.DsSameDataDbConfig;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class DataSourcePool {

	public static void initPool(DsNodeDbConfig dsNodeDbConfig){
	     DriverManagerDataSource dataSource=new DriverManagerDataSource();
//         dataSource.setDriverClassName("com.mysql.jdbc.Driver");
         dataSource.setDriverClassName("com.oscar.Driver");
//         dataSource.setUrl("jdbc:mysql://192.168.31.150:3306/route?useSSL=false");
         dataSource.setUrl("jdbc:oscar://"+dsNodeDbConfig.getInstanceIp()+":"+dsNodeDbConfig.getInstancePort()+"/"+dsNodeDbConfig.getInstanceDb()+"?useSSL=false");
         dataSource.setUsername(dsNodeDbConfig.getUserName());
         dataSource.setPassword(dsNodeDbConfig.getPassWord());
         JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
         CacheMap.map.put(dsNodeDbConfig.getId().toString(),jdbcTemplate);
	}
	
}
