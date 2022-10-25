package com.ruoyi.datasync.service;

import com.ruoyi.datasync.domain.BinlogInfo;
import net.sf.jsqlparser.JSQLParserException;

import java.util.List;
import java.util.Map;

/**
 * binlog数据信息 服务层
 * 
 * @author ruoyi
 */
public interface IBinlogInfoService
{

    /**
     * 获取所有binlog日志信息
     * @return
     */
    List<BinlogInfo> selectBinlogByBinlogFile(String fileName);

    /**
     * 将获取得sql信息进行过滤
     * @param binlogInfoList
     * @return
     */
    void filterSql(List<BinlogInfo> binlogInfoList) throws JSQLParserException;
}
