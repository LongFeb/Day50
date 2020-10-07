package cn.nyse.config;

import cn.nyse.utils.MapWrapperFactory;
import com.alibaba.druid.pool.DruidDataSource;
import com.github.pagehelper.PageInterceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import tk.mybatis.spring.annotation.MapperScan;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

/**
 * spring与mybatis整合配置：
 * mybatis-spring依赖
 * 1.配置数据源
 * 2.配置SqlSessionFactoryBean，设置数据源和框架可选参数等Configuration的配置
 * 3.MapperScannerConfigurate扫描Mapper接口创建代理子类
 */
@Configuration
//@MapperScan("cn.nyse.mapper")//扫描指定mapper接口包，自动getMapper
@MapperScan("cn.nyse.mapper")//扫描指定mapper接口包且需要实现继承通用的Mapper接口的，自动getMapper
@Import(SpringServiceConfig.class)   //引入其他配置类
public class SpringMybatisConfig {

    //使用DruidDataSource
    @Bean
    public DruidDataSource getDataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        InputStream is = SpringMybatisConfig.class.getClassLoader().getResourceAsStream("db.properties");
        Properties properties = new Properties();
        try {
            properties.load(is);//加载配置文件
            dataSource.configFromPropety(properties);//设置属性
            /**
             * druid性能监控：
             * 1.设置使用性能监控过滤器
             * 2.设置自定义Sevlet实现对druid性能监控页面的显示处理
             * */
            try {
                dataSource.setFilters("stat,wall");
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return dataSource;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("配置文件不存在，检查配置文件。。。。");
        }
        return null;
    }


    @Bean
    public SqlSessionFactoryBean getSqlSessionFactoryBean(DruidDataSource dataSource){
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        //设置驼峰命名转换
//        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        //替换为tkmapper提供的配置对象
        tk.mybatis.mapper.session.Configuration configuration = new tk.mybatis.mapper.session.Configuration();
        //tkmapper自带的生成语句会自动转换驼峰命名，自定义的mapper的sql查询处理才需要设置该转换
        configuration.setMapUnderscoreToCamelCase(true);
        //将值为空的key显示出来
        configuration.setCallSettersOnNulls(true);

        //map封装的对象进行驼峰转换
        configuration.setObjectWrapperFactory(new MapWrapperFactory());

        //设置使用配置对象
        factoryBean.setConfiguration(configuration);



        //创建分页拦截
        PageInterceptor pageInterceptor = new PageInterceptor();
        pageInterceptor.setProperties(new Properties());
        factoryBean.setPlugins(pageInterceptor);
        return factoryBean;
    }
}
