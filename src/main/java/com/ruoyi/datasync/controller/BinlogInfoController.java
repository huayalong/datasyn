package com.ruoyi.datasync.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ruoyi.common.constant.CacheKeyConstants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.utils.CacheMap;
import com.ruoyi.common.utils.CacheUtils;
import com.ruoyi.common.utils.IpUtils;
import com.ruoyi.datasync.domain.BinlogEndPos;
import com.ruoyi.datasync.domain.BinlogFile;
import com.ruoyi.datasync.domain.BinlogInfo;
import com.ruoyi.datasync.domain.DsConfigResult;
import com.ruoyi.datasync.domain.DsNodeDbConfig;
import com.ruoyi.datasync.service.IBinlogInfoService;
import com.ruoyi.datasync.service.IDsNodeDbConfigService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.jsqlparser.JSQLParserException;

/**
 * binlog数据信息
 * 
 * @author ruoyi
 */

@Api("binlog信息")
@Controller
@RequestMapping("/datasync/binLog")
public class BinlogInfoController extends BaseController
{

    @Autowired
    private IBinlogInfoService binlogInfoService;

    @Autowired
    private IDsNodeDbConfigService dsNodeDbConfigService;

    @ApiOperation("获取binlog信息")
    @ResponseBody
    @GetMapping("/binlogList")
//    @Scheduled(fixedRate = 5000)
    public R<List<BinlogInfo>> binlogList() throws JSQLParserException {

    	
        List<DsConfigResult> dsConfigResults = (List<DsConfigResult>) CacheUtils.get(CacheKeyConstants.DATA_SYNC_KEY);
        //找出主中心
        DsConfigResult dsConfigResultMaster = dsConfigResults.stream().filter(e -> e.getNodeIp().equals(IpUtils.getHostIp())).findFirst().get();
        if (!dsConfigResultMaster.getIsMaster().equals("0")) {
            return null;
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
        	
            String finalDataSourceId = dataSourceId;
            binLogFileList.stream().forEach(item -> {
            	
                // 根据文件名查询是否有结束值，假如没有初始化为0
                String getEndPosByFileName = "SELECT id, file_name, end_pos FROM TDCP.ds_binlog_end_pos WHERE file_name = '"+item.getLogfileName()+"'";
                JdbcTemplate jdbcTemplate = map.get(finalDataSourceId);
                BinlogEndPos binlogEndPos = null;
                
                try{
                    binlogEndPos = jdbcTemplate.queryForObject(getEndPosByFileName, BeanPropertyRowMapper.newInstance(BinlogEndPos.class));
                } catch (EmptyResultDataAccessException e) {
                	e.printStackTrace();
                }
                if (binlogEndPos == null) {
                    String initEndPosByFileName = "INSERT INTO TDCP.DS_BINLOG_END_POS (FILE_NAME, END_POS) VALUES('"+item.getLogfileName()+"', '0')";
                    jdbcTemplate.execute(initEndPosByFileName);
                }
                
                String selectBinlogByBinlogFile = " SELECT logfile_name, event_type, user_name, server_id, end_log_pos, csf, event_info, redo_sql " +
                        				  		  "   FROM show_binlog_events('"+item.getLogfileName()+"', 0, 0, 100000) " +
                        				  		  "  WHERE end_log_pos > (SELECT end_pos FROM TDCP.DS_BINLOG_END_POS WHERE file_name = '"+item.getLogfileName()+"') " +
                        				  		  "    AND event_type IN ('DELETE_ROW','UPDATE_ROW','INSERT_ROW') ORDER BY end_log_pos DESC";

                List<BinlogInfo> binlogs = jdbcTemplate.query(selectBinlogByBinlogFile, BeanPropertyRowMapper.newInstance(BinlogInfo.class));
                
                // 记录此次操作的结束值标识
                if(!CollectionUtils.isEmpty(binlogs)){
                    String updateEndPosByFileName = "UPDATE ds_binlog_end_pos SET end_pos = '"+binlogs.get(0).getEndLogPos()+"' WHERE file_name = '"+item.getLogfileName()+"'";
                    jdbcTemplate.execute(updateEndPosByFileName);
                }
                
                binlogList.addAll(binlogs);
            });
        }
        if (!CollectionUtils.isEmpty(binlogList)) {
            binlogInfoService.filterSql(binlogList);
        }
		return null;
    
    }

    @ApiOperation("发送binlog信息")
    @ResponseBody
    @GetMapping("/hello")
    public R sendBinLogList(){
        Map<String, JdbcTemplate> map = CacheMap.map;
        return R.ok();
    }

    //获取到查询出来的sql实体类之后，过滤需要同步得sql
    private List<String> filterSql(List<BinlogInfo> binlogList) {
        //从缓存中获取所有得配置信息
        return null;
    }

}
