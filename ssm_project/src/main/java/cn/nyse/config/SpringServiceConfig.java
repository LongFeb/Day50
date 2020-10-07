package cn.nyse.config;

import cn.nyse.interceptor.ResourceInterceptor;
import com.alibaba.druid.support.spring.stat.DruidStatInterceptor;
import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.context.annotation.*;
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
@ComponentScan({"cn.nyse.service","cn.nyse.aspect"})
@EnableTransactionManagement
@EnableAspectJAutoProxy  //开启aop注解支持
@PropertySource(value = "classpath:system.properties",encoding = "utf-8")
@Import(SpringCacheConfig.class)
public class SpringServiceConfig {

    @Bean
    public DataSourceTransactionManager  getTransactionManager(DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public ResourceInterceptor getResourceInterceptor(){
        return new ResourceInterceptor();
    }

    @Bean(name="druidStatInterceptor")//设置druid 的 aop切面类
    public DruidStatInterceptor getDruidStatInterceptor(){
        DruidStatInterceptor druidStatInterceptor = new DruidStatInterceptor();

        return druidStatInterceptor;
    }


    @Bean//配置spring监控
    public BeanNameAutoProxyCreator getAutoProxyCreator(){
        BeanNameAutoProxyCreator beanNameAutoProxyCreator = new BeanNameAutoProxyCreator();
        beanNameAutoProxyCreator.setProxyTargetClass(true);
        beanNameAutoProxyCreator.setBeanNames(new String[]{"*Mapper","*ServiceImpl"});
        beanNameAutoProxyCreator.setInterceptorNames("druidStatInterceptor");
        return beanNameAutoProxyCreator;
    }

}
