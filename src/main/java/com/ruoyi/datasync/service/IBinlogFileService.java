package com.ruoyi.datasync.service;

import com.ruoyi.datasync.domain.BinlogFile;

import java.util.List;

/**
 * binlog日志文件信息 服务层
 * 
 * @author ruoyi
 */
public interface IBinlogFileService
{

    /**
     * 查询binlog文件列表
     * @return
     */
    List<BinlogFile> selectBinlogFile();
}
