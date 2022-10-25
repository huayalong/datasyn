package com.ruoyi.datasync.mapper;

import com.ruoyi.datasync.domain.BinlogInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * binlog数据信息 数据层
 * 
 * @author ruoyi
 */
public interface BinlogInfoMapper
{

    // 获取所有binlog日志信息
    List<BinlogInfo> selectBinlogByBinlogFile(@Param("fileName") String fileName);
}