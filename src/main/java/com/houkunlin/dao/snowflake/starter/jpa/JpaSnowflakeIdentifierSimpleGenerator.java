package com.houkunlin.dao.snowflake.starter.jpa;

import com.littlenb.snowflake.sequence.IdGenerator;
import lombok.RequiredArgsConstructor;
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

import java.io.Serializable;
import java.util.Properties;

/**
 * Hibernate 雪花算法主键策略
 *
 * @author HouKunLin
 */
@RequiredArgsConstructor
public class JpaSnowflakeIdentifierSimpleGenerator implements IdentifierGenerator {
    public static final String CLASS_NAME = JpaSnowflakeIdentifierSimpleGenerator.class.getSimpleName();
    private static final Logger logger = LoggerFactory.getLogger(JpaSnowflakeIdentifierSimpleGenerator.class);
    private final IdGenerator idGenerator;
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
}
