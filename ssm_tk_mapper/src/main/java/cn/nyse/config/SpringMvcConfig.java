package cn.nyse.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * 1.开启mvc注解支持 @EnableWebMvc
 * 2.扫描controller层  @ComponentScan(basePackages = "cn.nyse.controller")
 * 3.设置静态资源放行
 * 4.设置 视图解析规则
 */
@Configuration
@EnableWebMvc
@ComponentScan("cn.nyse.controller")
//WebMvcConfigurer 实现springmvc的配置  自动调用去设置相关配置
public class SpringMvcConfig implements WebMvcConfigurer {

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();//设置默认静态资源映射处理servlet  等同于   mvc:default-servlet-handler
    }
    //4.设置 视图解析规则
    @Bean
    public InternalResourceViewResolver getViewResolver(){
        return new InternalResourceViewResolver("/WEB-INF/", ".jsp");
    }
}
