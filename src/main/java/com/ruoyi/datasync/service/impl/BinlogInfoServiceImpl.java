package com.ruoyi.datasync.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ruoyi.common.constant.CacheKeyConstants;
import com.ruoyi.common.constant.DataSynchronizationConstant;
import com.ruoyi.common.utils.CacheUtils;
import com.ruoyi.common.utils.IpUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.datanetty.server.handler.ServerMessageHandler;
import com.ruoyi.datasync.domain.BinlogInfo;
import com.ruoyi.datasync.domain.DsConfigResult;
import com.ruoyi.datasync.domain.DsNodeDbConfig;
import com.ruoyi.datasync.domain.DsSameDataDbConfig;
import com.ruoyi.datasync.mapper.BinlogInfoMapper;
import com.ruoyi.datasync.service.IBinlogInfoService;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.update.Update;

/**
 * binlog数据信息 服务层实现
 * 
 * @author ruoyi
 */
@Service
public class BinlogInfoServiceImpl implements IBinlogInfoService
{
    @Autowired
    private BinlogInfoMapper binlogInfoMapper;

    @Value("${coreNode.ip}")
    private String localNode;

    @Autowired
    private DsNodeDbConfigServiceImpl dsNodeDbConfigService;

    /**
     * 获取所有binlog日志信息
     * @return
     */
    @Override
    public List<BinlogInfo> selectBinlogByBinlogFile(String fileName) {
        return binlogInfoMapper.selectBinlogByBinlogFile(fileName);
    }

    @Override
    public void filterSql(List<BinlogInfo> binlogInfoList) throws JSQLParserException {

    	//先获取缓存中本节点的信息
        List<DsConfigResult> dsConfigResults = (List<DsConfigResult>) CacheUtils.get(CacheKeyConstants.DATA_SYNC_KEY);
        DsConfigResult dsConfigResult = dsConfigResults.stream().filter(e -> e.getNodeIp().equals(IpUtils.getHostIp())).findFirst().get();
        //获取本节点下所有需要同步得模式和表
        List<DsSameDataDbConfig> sameDBSyncConfigList = dsConfigResult.getSameDBSyncConfigList();

        //对binlogInfoList集合进行循环遍历，解析sql，获取模式名称,若模式名称没有在schemaList中，则该数据放弃，
        // 若存在该模式名称，表不是需要同步得表，则该数据也放弃
        for (BinlogInfo binlogInfo : binlogInfoList) {
        	
            String sql = binlogInfo.getRedoSql();
            String eventType = binlogInfo.getEventType();
            Table table = null;
            
            if (eventType.equals(DataSynchronizationConstant.DATA_INSERT)) {
                Insert parse = (Insert) CCJSqlParserUtil.parse(sql);
                table = parse.getTable();
            } else if (eventType.equals(DataSynchronizationConstant.DATA_UPDATE)) {
                Update parse = (Update) CCJSqlParserUtil.parse(sql);
                table = parse.getTable();
            } else {
                Delete parse = (Delete) CCJSqlParserUtil.parse(sql);
                table = parse.getTable();
            }
            //获取模式名
            String schemaName = table.getSchemaName();
            //模式名中包含双引号，所以需要将双引号去掉
            String finalSchemaName = schemaName.replace("\"", "");
            //获取表名
            String tableName = table.getName();
            //表名中包含双引号，所以需要将双引号去掉
            String finalTableName = tableName.replace("\"", "");
            
            for (DsSameDataDbConfig dbf : sameDBSyncConfigList) {
            	if(DataSynchronizationConstant.DS_SAME_DATA_DBCONFIG_IS_USE.equals(dbf.getIsUse())){
                	if(dbf.getSourceSchema().equals(finalSchemaName)){
                		if (StringUtils.isEmpty(dbf.getSourceTable())) {
                            syncMessage(dbf,binlogInfo);
                        } else if (finalTableName.equals(dbf.getSourceTable())) {
                        	syncMessage(dbf,binlogInfo);
                        }
                	}
                }
			}
        }
    }

    private void syncMessage(DsSameDataDbConfig dsSameDataDbConfig,BinlogInfo binlogInfo) {
        
    	String targetDbId = dsSameDataDbConfig.getTargetDbId();
        DsNodeDbConfig dsNodeDbConfig = dsNodeDbConfigService.selectDsNodeDbConfigById(Long.parseLong(targetDbId));
        
        String redoSql = binlogInfo.getRedoSql();
        String resultSql = dsNodeDbConfig.getId() + ":" + redoSql;
        ServerMessageHandler.sendMessage(dsNodeDbConfig.getInstanceIp(),resultSql);
        
    }
}
