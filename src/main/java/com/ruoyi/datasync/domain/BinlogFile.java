package com.ruoyi.datasync.domain;

/**
 * binlog日志文件信息 实体
 */
public class BinlogFile {
    // 日志文件名
    private String logfileName;
    // 文件大小
    private String fileSize;

    public String getLogfileName() {
        return logfileName;
    }

    public void setLogfileName(String logfileName) {
        this.logfileName = logfileName;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }
}
