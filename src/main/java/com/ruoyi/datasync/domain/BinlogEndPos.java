package com.ruoyi.datasync.domain;

/**
 * binlog查询结束值记录实体
 */
public class BinlogEndPos {
    // 唯一标识
    private int id;
    // 日志文件名
    private String fileName;
    // 结束值
    private int endPos;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getEndPos() {
        return endPos;
    }

    public void setEndPos(int endPos) {
        this.endPos = endPos;
    }
}
