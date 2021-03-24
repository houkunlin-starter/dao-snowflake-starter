package com.houkunlin.snowflake.starter.jpa;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * SnowflakeHibernate 配置
 *
 * @author HouKunLin
 */
@Configuration
@ComponentScan(basePackageClasses = SnowflakeHibernateConfiguration.class)
public class SnowflakeHibernateConfiguration {
}
