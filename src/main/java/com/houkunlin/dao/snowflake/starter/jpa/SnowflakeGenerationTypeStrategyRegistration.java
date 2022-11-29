package com.houkunlin.dao.snowflake.starter.jpa;

import jakarta.persistence.GenerationType;
import org.hibernate.id.factory.spi.GenerationTypeStrategy;
import org.hibernate.id.factory.spi.GenerationTypeStrategyRegistration;
import org.hibernate.service.ServiceRegistry;

import java.util.function.BiConsumer;

public class SnowflakeGenerationTypeStrategyRegistration implements GenerationTypeStrategyRegistration {
    @Override
    public void registerStrategies(final BiConsumer<GenerationType, GenerationTypeStrategy> registry, final ServiceRegistry serviceRegistry) {
        // registry.accept(GenerationType.UUID, new GenerationTypeStrategy() {
        //     @Override
        //     public IdentifierGenerator createIdentifierGenerator(final GenerationType generationType, final String generatorName, final JavaType<?> javaType, final Properties config, final GeneratorDefinitionResolver definitionResolver, final ServiceRegistry serviceRegistry) {
        //         return null;
        //     }
        // });
    }
}
