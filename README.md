# DAO 雪花算法 Starter

雪花算法依赖：
```
com.littlenb:snowflake
```



## 默认创建 Bean

对 `com.littlenb:snowflake` 的算法实例注入到 SpringBoot Bean 中

- `com.littlenb.snowflake.worker.WorkerIdAssigner` 提供数据中心标识，可在程序中手动覆盖
- `com.littlenb.snowflake.sequence.IdGenerator` 雪花算法实例，通过实例来获取唯一主键
- `com.littlenb.snowflake.support.IdGeneratorFactory` 雪花算法实例工厂，通过工厂传入数据中心标识来创建一个雪花算法实例



## 配置文件支持

配置文件：`com.houkunlin.dao.snowflake.starter.SnowflakeProperties`

| KEY | Default | Remark |
| ----------------------------- | --------------------- |---------------------------------------- |
| dao.snowflake.time-bits | 41 | 时间，增量毫秒 bit 长度 |
| dao.snowflake.worker-bits | 10| 机器，数据中心 bit 长度 |
| dao.snowflake.seq-bits | 12 | 序列，随机序列 bit 长度 |
| dao.snowflake.epoch-timestamp | 1577808000000L | 开始时间戳，默认 UTC 2020-01-01 00:00:00 |
| dao.snowflake.time-unit | TimeUnit.MILLISECONDS | 时间，时间单位，在时间单位内只有序列变化 |
| dao.snowflake.worker-id | 1L | 机器，数据中心ID |



## 对于 Jpa 如何使用

> 把 snowflake 雪花算法注入到 Hibernate Jpa 的全局主键策略中



用法：

```
@javax.persistence.Id
@javax.persistence.GeneratedValue(generator = "id")
@org.hibernate.annotations.GenericGenerator(name = "id", strategy = "snowflake")
private String id;
```
