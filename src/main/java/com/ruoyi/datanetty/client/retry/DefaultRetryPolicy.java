package com.ruoyi.datanetty.client.retry;

import java.util.Random;


public class DefaultRetryPolicy implements RetryPolicy {

    private final Random random = new Random();

    /**
     * 重连次数最大，因为重连间隔时间是2为底的指数级数增长，因此2^13 = 8192刚好大于默认最大间隔2小时
     **/
    private int maxRetryCount = 13;

    /**
     * 默认最大间隔时间, 2小时
     **/
    private int maxIntervalSecond = 7200;

    public DefaultRetryPolicy() {
        this(13, 7200);
    }

    public DefaultRetryPolicy(int maxRetryCount, int maxIntervalSecond) {
        if (maxRetryCount < 1) {
            throw new IllegalArgumentException("maxRetryCount must be greater than 0.");
        }

        if (maxIntervalSecond < 1) {
            throw new IllegalArgumentException("maxIntervalSecond must be greater than 0.");
        }

        this.maxRetryCount = maxRetryCount;
        this.maxIntervalSecond = maxIntervalSecond;
    }

    @Override
    public boolean willRetry(int retryCount) {
        return retryCount <= maxRetryCount;
    }

    @Override
    public long retryInterval(int retryCount) {
        if (retryCount < 0) {
            retryCount = 0;
        }

        return Math.min(maxIntervalSecond, 1 << retryCount);
    }
}