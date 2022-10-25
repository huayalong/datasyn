package com.ruoyi.datasync;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.ruoyi.common.utils.CacheMap;
import com.ruoyi.datanetty.protocal.BinlogMessage;

/**
 * 处理接收到的消息
 * @author huayalong
 *
 */
public class ProcessMessage {
	
	protected final static Logger logger = LoggerFactory.getLogger(ProcessMessage.class);
	
	public static void doProcessMessage(BinlogMessage msg){
		
		String message = msg.getContent().toString();
		String sql = message.substring(message.indexOf(":") + 1);
		String dataSourceId = message.substring(0,message.indexOf(":"));
		logger.debug(sql);
		JdbcTemplate jdbcTemplate = CacheMap.map.get(dataSourceId);
		jdbcTemplate.execute(sql);
		logger.debug("数据同步执行成功！！！");
	}
	
	public static void main(String args[]){
		ProcessMessage.doProcessMessage(null);
	}
	
}
