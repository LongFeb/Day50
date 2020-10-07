package cn.nyse.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截器开发流程：
 * 1.编写拦截器类，实现HandlerInterceptor类
 * 2.声明拦截器为spring容器管理，注册拦截器
 * 3.拦截、放行规则
 *
 *
 * HandlerInterceptor:拦截器
 *
 * 未登录用户进行拦截，跳转到notlogin
 *
 * */
public class LoginInterceptor implements HandlerInterceptor {
    //方法执行前处理
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object loginUser = request.getSession().getAttribute("loginUser");
        if(loginUser!=null){
            return true;
        }
        response.sendRedirect(request.getContextPath()+"/notlogin.html");
        return false;
    }

    //方法执行后，返回核心控制器前处理
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("方法执行完成。。。。");
    }

    //视图执行，返回页面前处理
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("视图执行完成。。。");
    }
}
