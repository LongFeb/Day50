package cn.nyse.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 1.引入依赖
 * 2.配置数据源
 * 3.加载数据源
 * 4.创建sqlsessionfactorybean
 */
@Configuration
@MapperScan("cn.nyse.mapper")
@Import(SpringServiceConfig.class)
public class SpringMybatisConfig {
    @Bean
    public DruidDataSource getDruidDateSource(){
        DruidDataSource dataSource = new DruidDataSource();
        ClassLoader classLoader = SpringMybatisConfig.class.getClassLoader();
        InputStream is = classLoader.getResourceAsStream("db.properties");
        Properties properties = new Properties();
        try {
            properties.load(is);
            dataSource.configFromPropety(properties);
            return dataSource;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Bean
    public SqlSessionFactoryBean getSqlSessionFactoryBean(DruidDataSource dataSource){
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        tk.mybatis.mapper.session.Configuration configuration = new tk.mybatis.mapper.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(true);
        factoryBean.setConfiguration(configuration);
        factoryBean.setDataSource(dataSource);
        return factoryBean;
    }

}
