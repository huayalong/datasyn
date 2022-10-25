package com.ruoyi.datasync.service.impl;

import com.ruoyi.datasync.domain.BinlogFile;
import com.ruoyi.datasync.mapper.BinlogFileMapper;
import com.ruoyi.datasync.service.IBinlogFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * binlog日志文件信息 服务层实现
 * 
 * @author ruoyi
 */
@Service
public class BinlogFileServiceImpl implements IBinlogFileService
{

    @Autowired
    private BinlogFileMapper binlogFileMapper;

    /**
     * 查询binlog文件列表
     * @return
     */
    @Override
    public List<BinlogFile> selectBinlogFile() {
        return binlogFileMapper.selectBinlogFile();
    }
}
