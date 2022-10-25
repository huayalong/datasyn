package com.ruoyi.quartz.task;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.ruoyi.common.constant.CacheKeyConstants;
import com.ruoyi.common.utils.CacheMap;
import com.ruoyi.common.utils.CacheUtils;
import com.ruoyi.common.utils.IpUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.datasync.domain.BinlogEndPos;
import com.ruoyi.datasync.domain.BinlogFile;
import com.ruoyi.datasync.domain.BinlogInfo;
import com.ruoyi.datasync.domain.DsConfigResult;
import com.ruoyi.datasync.domain.DsNodeDbConfig;
import com.ruoyi.datasync.service.IBinlogEndPosService;
import com.ruoyi.datasync.service.IBinlogInfoService;
import com.ruoyi.datasync.service.IDsNodeDbConfigService;

import net.sf.jsqlparser.JSQLParserException;

/**
 * 定时任务调度测试
 * 
 * @author ruoyi
 */
@Component("ryTask")
public class RyTask
{
    @Autowired
    private IBinlogInfoService binlogInfoService;

    @Autowired
    private IBinlogEndPosService binlogEndPosService;

    @Autowired
    private IDsNodeDbConfigService dsNodeDbConfigService;

    public void ryMultipleParams(String s, Boolean b, Long l, Double d, Integer i)
    {
        System.out.println(StringUtils.format("执行多参方法： 字符串类型{}，布尔类型{}，长整型{}，浮点型{}，整形{}", s, b, l, d, i));
    }

    public void ryParams(String params)
    {
        System.out.println("执行有参方法：" + params);
    }

    public void ryNoParams() throws JSQLParserException {
    	
        List<DsConfigResult> dsConfigResults = (List<DsConfigResult>) CacheUtils.get(CacheKeyConstants.DATA_SYNC_KEY);
        //找出主中心
        DsConfigResult dsConfigResultMaster = dsConfigResults.stream().filter(e -> e.getNodeIp().equals(IpUtils.getHostIp())).findFirst().get();
        if (!dsConfigResultMaster.getIsMaster().equals("0")) {
            return;
        }
        
        List<BinlogInfo> binlogList = new ArrayList<>();
        
        //获取主中心Ip，从数据库实例中找到唯一一个数据库链接，并获取他的ID
        DsNodeDbConfig dsNodeDbConfig = new DsNodeDbConfig();
        dsNodeDbConfig.setInstanceIp(dsConfigResultMaster.getNodeIp());
        List<DsNodeDbConfig> dsNodeDbConfigs = dsNodeDbConfigService.selectDsNodeDbConfigList(dsNodeDbConfig);
        
        //存储binlog文件的信息
        List<BinlogFile> binLogFileList = null;
        String dataSourceId =  null;
        
        //中心节点数据连接池
        Map<String, JdbcTemplate> map = CacheMap.getMap();
        if (!CollectionUtils.isEmpty(dsNodeDbConfigs) && dsNodeDbConfigs.size() == 1) {
            DsNodeDbConfig dbConfig = dsNodeDbConfigs.get(0);
            dataSourceId = dbConfig.getId().toString();
            JdbcTemplate jdbcTemplate = map.get(dataSourceId);
            String sql = "select logfile_name, file_size from v_sys_binary_logs";
            binLogFileList = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(BinlogFile.class));
        }
        
        if (!CollectionUtils.isEmpty(binLogFileList)) {
            JdbcTemplate jdbcTemplate = map.get(dataSourceId);
            binLogFileList.stream().forEach(item -> {
            	
                // 根据文件名查询是否有结束值，假如没有初始化为0
                BinlogEndPos binlogEndPos = binlogEndPosService.initEndPosByFileName(item.getLogfileName());

                String binLogSQL = " SELECT logfile_name, event_type, user_name, server_id, end_log_pos, csf, event_info, redo_sql " +
                        		   "   FROM show_binlog_events('"+item.getLogfileName() + "', "+binlogEndPos.getEndPos()+", 0, 100000) " +
                        		   "  WHERE event_type IN ('DELETE_ROW','UPDATE_ROW','INSERT_ROW') ORDER BY end_log_pos DESC ";

                List<BinlogInfo> binlogs = jdbcTemplate.query(binLogSQL, BeanPropertyRowMapper.newInstance(BinlogInfo.class));
                
                // 记录此次操作的结束值标识
                if(!CollectionUtils.isEmpty(binlogs)){
                    binlogEndPosService.updateEndPosByFileName(item.getLogfileName(), binlogs.get(0).getEndLogPos());
                }
                
                binlogList.addAll(binlogs);
            });
        }
        
        if (!CollectionUtils.isEmpty(binlogList)) {
            binlogInfoService.filterSql(binlogList);
        }
    }
}
