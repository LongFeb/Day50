package cn.nyse.web;

import com.alibaba.druid.support.http.WebStatFilter;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

/**
 * 自定义filter，设置初始化属性，可以开启web监控
 * */
@WebFilter(urlPatterns = "/*",initParams = {
        @WebInitParam(name="exclusions",value = "*.js,*.jpg,*.png,*.css,/druid/*"),//session拦截忽略规则
        @WebInitParam(name="sessionStatEnable",value = "true"),//开启session监控
        @WebInitParam(name="principalSessionName",value = "loginUser"),//设置session的user的key
        @WebInitParam(name="profileEnable",value = "true")//监控url的sql调用表
})
public class DruidViewFilter extends WebStatFilter {
}
