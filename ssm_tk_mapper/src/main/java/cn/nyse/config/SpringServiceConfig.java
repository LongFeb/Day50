package cn.nyse.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 *
 1.包扫描-扫描service
 2.配置事务整合
 a.配置spring提供的事务管理器(aop切面类)
 b.开启事务注解支持
 c.需要管理事务的类或者方法上添加事务注解
 */
@Configuration
@ComponentScan("cn.nyse.service")
@EnableTransactionManagement
public class SpringServiceConfig {

    @Bean
    public DataSourceTransactionManager  getTransactionManager(DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }
}
