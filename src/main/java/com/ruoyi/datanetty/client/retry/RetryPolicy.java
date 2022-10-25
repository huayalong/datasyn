package com.ruoyi.datanetty.client.retry;

public interface RetryPolicy {

    /**
     * 此方法判断是否应该继续重试（重试次数耗尽则终止重试）
     * @param retryCount: 当前已经重新连接多少次
     * @return: true or false
     **/
    boolean willRetry(int retryCount);

    /**
     * 此方法根据重试次数计算两次重试之间的时间间隔
     * @param retryCount: 当前已经重新连接多少次
     * @return: long
     **/
    long retryInterval(int retryCount);
}