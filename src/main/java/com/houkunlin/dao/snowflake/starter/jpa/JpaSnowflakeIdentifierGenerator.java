package com.houkunlin.dao.snowflake.starter.jpa;

import com.littlenb.snowflake.sequence.IdGenerator;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerationException;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.BasicType;
import org.hibernate.type.Type;
import org.hibernate.type.descriptor.java.StringJavaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Properties;

/**
 * Hibernate 雪花算法主键策略
 *
 * @author HouKunLin
 */
@ConditionalOnClass(IdentifierGenerator.class)
@Component
public class JpaSnowflakeIdentifierGenerator implements IdentifierGenerator, ApplicationContextAware, InitializingBean {
    public static final String CLASS_NAME = JpaSnowflakeIdentifierGenerator.class.getSimpleName();
    private static final Logger logger = LoggerFactory.getLogger(JpaSnowflakeIdentifierGenerator.class);
    /**
     * 由于 Hibernate 的主键策略是多实例的，每一个 Entity 对应一个 IdentifierGenerator 实例，即使同一个策略，也会因为不同的 Entity 而创建实例。
     * 因此这里必须提供一个静态的属性，可供多个主键策略实例共享同一个 IdGenerator 雪花算法实例
     */
    private static ApplicationContext applicationContext;
    private static IdGenerator idGenerator;
    /**
     * 每个 Entity 都会实例化一个 IdentifierGenerator 对象，因此这个 entityName 只对应一个指定 Entity 名称，而不会与其他的重复。
     */
    private String entityName;
    /**
     * Entity 的主键字段类型
     */
    private BasicType<?> identifierType;

    @Override
    public void configure(Type type, Properties params, ServiceRegistry serviceRegistry) throws MappingException {
        if (type instanceof BasicType) {
            this.identifierType = (BasicType<?>) type;
        } else {
            throw new IdentifierGenerationException("identifier property type error");
        }
        entityName = params.getProperty(IdentifierGenerator.ENTITY_NAME);
        if (entityName == null) {
            throw new MappingException("no entity name");
        }
        if (logger.isDebugEnabled()) {
            logger.debug("{} configure {} id property type {}", CLASS_NAME, entityName, type);
        }
    }

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        long nextId = idGenerator.nextId();
        if (logger.isDebugEnabled()) {
            logger.debug("{} generate {} : {}", CLASS_NAME, entityName, nextId);
            // IdentifierGeneratorHelper.getIntegralDataTypeHolder(null).initialize(0).makeValue()
        }
        if (identifierType instanceof StringJavaType) {
            // 这里只考虑一个特殊的 String 类型的主键，其他的暂时一律返回 Long 值
            return "" + nextId;
        }
        return nextId;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        assert applicationContext != null;
        idGenerator = applicationContext.getBean(IdGenerator.class);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        JpaSnowflakeIdentifierGenerator.applicationContext = applicationContext;
    }
}
