package com.houkunlin.snowflake.starter.mybatisplus;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.littlenb.snowflake.sequence.IdGenerator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Component;

/**
 * @author HouKunLin
 */
@ConditionalOnClass(IdentifierGenerator.class)
@Component
public class SnowflakeIdentifierGenerator implements IdentifierGenerator {
    private final IdGenerator idGenerator;

    public SnowflakeIdentifierGenerator(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    @Override
    public Number nextId(Object entity) {
        return idGenerator.nextId();
    }
}
