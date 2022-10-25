package com.ruoyi.datasync.mapper;

import com.ruoyi.datasync.domain.BinlogEndPos;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * binlog查询结束值记录 数据层
 * 
 * @author ruoyi
 */
public interface BinlogEndPosMapper
{
    // 根据文件名查询是否有结束值
    BinlogEndPos getEndPosByFileName(String fileName);

    // 根据文件名修改binlog结束值
    void updateEndPosByFileName(@Param("fileName")String fileName, @Param("endPos")String endPos);

    // 根据文件名初始结束值为0
    void initEndPosByFileName(String logFileName);


    int insertBinlogEndPos(BinlogEndPos binlogEndPos);

    //根据v_sys_binary_logs表，给位移表初始值
    void initEndPosByLog();

    //清空库表
    int deleteBinlogEndPos();



}