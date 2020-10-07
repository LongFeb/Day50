package cn.nyse.interceptor;

import cn.nyse.entity.SysResource;
import cn.nyse.entity.SysUser;
import cn.nyse.service.SysResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 资源拦截
 * 1.获取所有需要授权的资源，如果当前请求需要授权资源，则进行授权判断，
 * 如果不需要授权，则是公共资源或按钮资源
 *2.进一步授权判断：
 * a.获取用户已授权限的资源，与当前请求进行比较，如果是已授权则放行
 *b.如果是未授权资源则拦截
 *
 * */

public class ResourceInterceptor implements HandlerInterceptor {
    //当前拦截器组件是spring容器管理的  可以注入容器中已存在的bean
    @Autowired
    SysResourceService resourceService;

    public ResourceInterceptor() {
    }

    public ResourceInterceptor(SysResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        List <SysResource> resources = resourceService.selectResources();
        boolean need=false;
        // /应用名/xxxx  ->xxxx
        String url = request.getRequestURI().replace(request.getContextPath() + "/", "");
        for (SysResource resource : resources) {
            if(resource.getUrl().equals(url)){
                need=true;
                break;
            }
        }

        if(need){
            SysUser loginUser = (SysUser) request.getSession().getAttribute("loginUser");
            List <SysResource> resources1 = loginUser.getResources();
            for (SysResource sysResource : resources1) {
                if(sysResource.getUrl().equals(url)){
                    return true;
                }
            }
        }else {
            return true;
        }

        response.sendRedirect(request.getContextPath()+"/notauth.html");
        return false;
    }
}
