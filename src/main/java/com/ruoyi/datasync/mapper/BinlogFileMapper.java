package com.ruoyi.datasync.mapper;

import com.ruoyi.datasync.domain.BinlogFile;

import java.util.List;

/**
 * binlog 文件信息 数据层
 * 
 * @author ruoyi
 */
public interface BinlogFileMapper
{
    // 查询binlog文件列表
    List<BinlogFile> selectBinlogFile();
}