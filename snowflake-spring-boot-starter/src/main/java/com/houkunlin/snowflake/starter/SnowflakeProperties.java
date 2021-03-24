package com.houkunlin.snowflake.starter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * Snowflake 算法生成参数
 *
 * @author HouKunLin
 * @see com.littlenb.snowflake.support.MillisIdGeneratorFactory
 */
@Configuration
@ConfigurationProperties("snowflake")
public class SnowflakeProperties {
    /**
     * 增量毫秒长度
     */
    private int timeBits = 41;
    /**
     * 数据中心长度
     */
    private int workerBits = 10;
    /**
     * 随机序列长度
     */
    private int seqBits = 12;
    /**
     * 开始时间戳，默认 UTC 2020-01-01 00:00:00
     */
    private long epochTimestamp = 1577808000000L;
    /**
     * 时间单位
     */
    private TimeUnit timeUnit = TimeUnit.MILLISECONDS;
    /**
     * 数据中心ID
     */
    private long workerId = 1L;

    public int getTimeBits() {
        return timeBits;
    }

    public void setTimeBits(int timeBits) {
        this.timeBits = timeBits;
    }

    public int getWorkerBits() {
        return workerBits;
    }

    public void setWorkerBits(int workerBits) {
        this.workerBits = workerBits;
    }

    public int getSeqBits() {
        return seqBits;
    }

    public void setSeqBits(int seqBits) {
        this.seqBits = seqBits;
    }

    public long getEpochTimestamp() {
        return epochTimestamp;
    }

    public void setEpochTimestamp(long epochTimestamp) {
        this.epochTimestamp = epochTimestamp;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }

    public Long getWorkerId() {
        return workerId;
    }

    public void setWorkerId(Long workerId) {
        this.workerId = workerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SnowflakeProperties that = (SnowflakeProperties) o;
        if (timeBits != that.timeBits) {
            return false;
        }
        if (workerBits != that.workerBits) {
            return false;
        }
        if (seqBits != that.seqBits) {
            return false;
        }
        if (epochTimestamp != that.epochTimestamp) {
            return false;
        }
        if (workerId != that.workerId) {
            return false;
        }
        return timeUnit == that.timeUnit;
    }

    @Override
    public int hashCode() {
        int result = timeBits;
        result = 31 * result + workerBits;
        result = 31 * result + seqBits;
        result = 31 * result + (int) (epochTimestamp ^ (epochTimestamp >>> 32));
        result = 31 * result + timeUnit.hashCode();
        result = 31 * result + (int) (workerId ^ (workerId >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "SnowflakeProperties{" +
                "timeBits=" + timeBits +
                ", workerBits=" + workerBits +
                ", seqBits=" + seqBits +
                ", epochTimestamp=" + epochTimestamp +
                ", timeUnit=" + timeUnit +
                ", workerId=" + workerId +
                '}';
    }
}
