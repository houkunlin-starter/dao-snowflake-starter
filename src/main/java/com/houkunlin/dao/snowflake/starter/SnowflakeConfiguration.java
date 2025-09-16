package com.houkunlin.dao.snowflake.starter;

import com.houkunlin.dao.snowflake.starter.jpa.JpaSnowflakeIdentifierGenerator;
import com.houkunlin.dao.snowflake.starter.jpa.SnowflakeHibernatePropertiesCustomizer;
import com.houkunlin.dao.snowflake.starter.mybatisplus.MybatisPlusSnowflakeIdentifierGenerator;
import com.littlenb.snowflake.sequence.IdGenerator;
import com.littlenb.snowflake.support.ElasticIdGeneratorFactory;
import com.littlenb.snowflake.support.IdGeneratorFactory;
import com.littlenb.snowflake.worker.WorkerIdAssigner;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * DAO雪花算法自动配置，支持 Jpa 和 MyBatis-Plus
 *
 * @author HouKunLin
 */
@Configuration(proxyBeanMethods = false)
public class SnowflakeConfiguration {
    private final SnowflakeProperties snowflakeProperties;

    public SnowflakeConfiguration(SnowflakeProperties snowflakeProperties) {
        this.snowflakeProperties = snowflakeProperties;
    }

    /**
     * 雪花算法唯一主键生成器
     *
     * @param idGeneratorFactoryObjectProvider 雪花算法对象构建工厂
     * @param workerIdAssignerObjectProvider   数据中心标识代码，机器标识代码
     * @return IdGenerator 雪花算法唯一主键生成器对象
     */
    @ConditionalOnMissingBean
    @Bean
    public IdGenerator idGenerator(ObjectProvider<IdGeneratorFactory> idGeneratorFactoryObjectProvider, ObjectProvider<WorkerIdAssigner> workerIdAssignerObjectProvider) {
        WorkerIdAssigner workerIdAssigner = workerIdAssignerObjectProvider.getIfAvailable();
        if (workerIdAssigner == null) {
            workerIdAssigner = snowflakeProperties::getWorkerId;
        }
        IdGeneratorFactory idGeneratorFactory = idGeneratorFactoryObjectProvider.getIfAvailable();
        if (idGeneratorFactory == null) {
            ElasticIdGeneratorFactory factory = new ElasticIdGeneratorFactory();
            factory.setTimeBits(snowflakeProperties.getTimeBits());
            factory.setWorkerBits(snowflakeProperties.getWorkerBits());
            factory.setSeqBits(snowflakeProperties.getSeqBits());
            factory.setEpochTimestamp(snowflakeProperties.getEpochTimestamp());
            factory.setTimeUnit(snowflakeProperties.getTimeUnit());
            idGeneratorFactory = factory;
        }
        return idGeneratorFactory.create(workerIdAssigner);
    }

    @Configuration(proxyBeanMethods = false)
    public static class MyBatisSnowflakeConfiguration {
        @ConditionalOnClass(com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator.class)
        @ConditionalOnMissingBean(com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator.class)
        @Bean
        public MybatisPlusSnowflakeIdentifierGenerator mybatisPlusSnowflakeIdentifierGenerator(IdGenerator idGenerator) {
            return new MybatisPlusSnowflakeIdentifierGenerator(idGenerator);
        }
    }

    @Configuration(proxyBeanMethods = false)
    public static class HibernateJpaConfiguration {

        @ConditionalOnClass(org.hibernate.id.IdentifierGenerator.class)
        @ConditionalOnMissingBean(org.hibernate.id.IdentifierGenerator.class)
        @Bean
        public JpaSnowflakeIdentifierGenerator jpaSnowflakeIdentifierGenerator() {
            return new JpaSnowflakeIdentifierGenerator();
        }

        @ConditionalOnClass(org.hibernate.id.IdentifierGenerator.class)
        @Bean
        public SnowflakeHibernatePropertiesCustomizer snowflakeHibernatePropertiesCustomizer() {
            return new SnowflakeHibernatePropertiesCustomizer();
        }
    }
}
