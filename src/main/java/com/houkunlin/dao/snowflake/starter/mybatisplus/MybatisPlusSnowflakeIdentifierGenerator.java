package com.houkunlin.dao.snowflake.starter.mybatisplus;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.littlenb.snowflake.sequence.IdGenerator;

/**
 * @author HouKunLin
 */
public class MybatisPlusSnowflakeIdentifierGenerator implements IdentifierGenerator {
    private final IdGenerator idGenerator;

    public MybatisPlusSnowflakeIdentifierGenerator(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    @Override
    public Number nextId(Object entity) {
        return idGenerator.nextId();
    }
}
