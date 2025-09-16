package com.houkunlin.dao.snowflake.starter.jpa;

import jakarta.persistence.GenerationType;
import lombok.Data;
import org.hibernate.id.factory.spi.GenerationTypeStrategy;
import org.hibernate.id.factory.spi.GenerationTypeStrategyRegistration;
import org.hibernate.service.ServiceRegistry;

import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;

@Data
public class SnowflakeGenerationTypeStrategyRegistration implements GenerationTypeStrategyRegistration {
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

    @Override
    public void registerStrategies(final BiConsumer<GenerationType, GenerationTypeStrategy> registry, final ServiceRegistry serviceRegistry) {
        // ElasticIdGeneratorFactory idGeneratorFactory = new ElasticIdGeneratorFactory();
        // idGeneratorFactory.setTimeBits(getTimeBits());
        // idGeneratorFactory.setWorkerBits(getWorkerBits());
        // idGeneratorFactory.setSeqBits(getSeqBits());
        // idGeneratorFactory.setEpochTimestamp(getEpochTimestamp());
        // idGeneratorFactory.setTimeUnit(getTimeUnit());
        //
        // IdGenerator idGenerator = idGeneratorFactory.create(getWorkerId());
        //
        // registry.accept(GenerationType.UUID, new GenerationTypeStrategy() {
        //     @Override
        //     public IdentifierGenerator createIdentifierGenerator(GenerationType generationType, String generatorName, JavaType<?> javaType, Properties config, GeneratorDefinitionResolver definitionResolver, ServiceRegistry serviceRegistry) {
        //         return new JpaSnowflakeIdentifierSimpleGenerator(idGenerator);
        //     }
        // });
    }
}
