package com.houkunlin.snowflake.starter;

import com.littlenb.snowflake.sequence.IdGenerator;
import com.littlenb.snowflake.support.ElasticIdGeneratorFactory;
import com.littlenb.snowflake.support.IdGeneratorFactory;
import com.littlenb.snowflake.worker.SimpleWorkerIdAssigner;
import com.littlenb.snowflake.worker.WorkerIdAssigner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * DAO雪花算法自动配置，支持 Jpa 和 MyBatis-Plus
 *
 * @author HouKunLin
 */
@ComponentScan
public class SnowflakeStarter {
    private static final Logger logger = LoggerFactory.getLogger(SnowflakeStarter.class);
    private final SnowflakeProperties snowflakeProperties;

    public SnowflakeStarter(SnowflakeProperties snowflakeProperties) {
        this.snowflakeProperties = snowflakeProperties;
    }

    /**
     * 提供一个机器标识代码，数据中心代码
     *
     * @return WorkerIdAssigner 机器标识代码
     */
    @ConditionalOnMissingBean
    @Bean
    public WorkerIdAssigner workerIdAssigner() {
        logger.debug("workerIdAssigner workerId {}", snowflakeProperties.getWorkerId());
        return new SimpleWorkerIdAssigner(snowflakeProperties.getWorkerId());
    }

    /**
     * 雪花算法唯一主键生成器
     *
     * @param idGeneratorFactory 雪花算法对象构建工厂
     * @param workerIdAssigner   数据中心标识代码，机器标识代码
     * @return IdGenerator 雪花算法唯一主键生成器对象
     */
    @ConditionalOnMissingBean
    @Bean
    public IdGenerator idGenerator(IdGeneratorFactory idGeneratorFactory, WorkerIdAssigner workerIdAssigner) {
        logger.debug("idGenerator workerId {}", workerIdAssigner.assignWorkerId());
        return idGeneratorFactory.create(workerIdAssigner);
    }

    /**
     * 雪花算法对象构建工厂
     *
     * @return IdGeneratorFactory 雪花算法对象构建工厂
     */
    @ConditionalOnMissingBean
    @Bean
    public IdGeneratorFactory idGeneratorFactory() {
        logger.debug("idGeneratorFactory {}", snowflakeProperties);
        ElasticIdGeneratorFactory factory = new ElasticIdGeneratorFactory();
        factory.setTimeBits(snowflakeProperties.getTimeBits());
        factory.setWorkerBits(snowflakeProperties.getWorkerBits());
        factory.setSeqBits(snowflakeProperties.getSeqBits());
        factory.setEpochTimestamp(snowflakeProperties.getEpochTimestamp());
        factory.setTimeUnit(snowflakeProperties.getTimeUnit());
        return factory;
    }
}
