package com.houkunlin.snowflake.starter;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * Snowflake 算法生成参数
 *
 * @author HouKunLin
 * @see com.littlenb.snowflake.support.MillisIdGeneratorFactory
 */
@Data
@ToString
@EqualsAndHashCode
@Configuration
@ConfigurationProperties("dao.snowflake")
public class SnowflakeProperties {
    /**
     * 增量毫秒 bit 长度
     */
    private int timeBits = 41;
    /**
     * 数据中心 bit 长度
     */
    private int workerBits = 10;
    /**
     * 随机序列 bit 长度
     */
    private int seqBits = 12;
    /**
     * 开始时间戳，默认 UTC 2020-01-01 00:00:00
     */
    private long epochTimestamp = 1577808000000L;
    /**
     * 时间单位，默认毫秒
     */
    private TimeUnit timeUnit = TimeUnit.MILLISECONDS;
    /**
     * 数据中心ID
     */
    private long workerId = 1L;
}
