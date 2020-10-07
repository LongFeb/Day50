package cn.nyse.config;

import cn.nyse.interceptor.LoginInterceptor;
import cn.nyse.interceptor.ResourceInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.*;
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
        return new InternalResourceViewResolver("/WEB-INF/html", ".html");
    }

    /**
     * springmvc文件上传解析器  专门解析commons的技术实现文件上传功能，转成MultipartFile对象
     * @return
     */
    @Bean("multipartResolver")
    public CommonsMultipartResolver getMultipartResolver(){
        return new CommonsMultipartResolver();
    }



    @Autowired
    ResourceInterceptor resourceInterceptor;
    /**
     * 拦截器先后处理：先注册先拦截  或者设置order
     * */
    //声明注册拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LoginInterceptor loginInterceptor = new LoginInterceptor();
        InterceptorRegistration registration = registry.addInterceptor(loginInterceptor);
        registration.addPathPatterns("/**");//拦截
        registration.excludePathPatterns(new String[]{"/common/**"});//放行

        //ResourceInterceptor resourceInterceptor = new ResourceInterceptor();
        InterceptorRegistration registration2 = registry.addInterceptor(resourceInterceptor);
        registration2.addPathPatterns("/manager/**");
    }
}
