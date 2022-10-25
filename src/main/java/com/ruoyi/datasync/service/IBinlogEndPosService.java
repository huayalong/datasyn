package com.ruoyi.datasync.service;

import com.ruoyi.datasync.domain.BinlogEndPos;

/**
 * binlog查询结束值记录 服务层
 * 
 * @author ruoyi
 */
public interface IBinlogEndPosService
{

    /**
     * 根据文件名修改binlog查询结束值
     * @param fileName
     * @return
     */
    void updateEndPosByFileName(String fileName, String endPos);

    /**
     * 根据文件名查询是否有结束值，假如没有初始化为0
     * @param logFileName
     */
    BinlogEndPos initEndPosByFileName(String logFileName);

    /**
     * 根据v_sys_binary_logs表，给位移表初始值
     */
    void initEndPosByLog();

}
