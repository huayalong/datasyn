package com.ruoyi.datasync.service.impl;

import com.ruoyi.datasync.domain.BinlogEndPos;
import com.ruoyi.datasync.mapper.BinlogEndPosMapper;
import com.ruoyi.datasync.service.IBinlogEndPosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * binlog查询结束值记录 服务层实现
 * 
 * @author ruoyi
 */
@Service
public class BinlogEndPosServiceImpl implements IBinlogEndPosService
{

    @Autowired
    private BinlogEndPosMapper binlogEndPosMapper;

    /**
     * 根据文件名查询是否有结束值，假如没有初始化为0
     * @param logFileName
     */
    @Override
    @Transactional
    public BinlogEndPos initEndPosByFileName(String logFileName) {
        BinlogEndPos endPos = binlogEndPosMapper.getEndPosByFileName(logFileName);
        if(null == endPos){
            binlogEndPosMapper.initEndPosByFileName(logFileName);
            return binlogEndPosMapper.getEndPosByFileName(logFileName);
        } else {
            return endPos;
        }
    }

    @Override
    public void initEndPosByLog() {
        binlogEndPosMapper.deleteBinlogEndPos();
        binlogEndPosMapper.initEndPosByLog();
    }


    /**
     * 根据文件名修改binlog查询结束值
     * @param fileName
     * @return
     */
    @Override
    @Transactional
    public void updateEndPosByFileName(String fileName, String endPos) {
        binlogEndPosMapper.updateEndPosByFileName(fileName, endPos);
    }


}
