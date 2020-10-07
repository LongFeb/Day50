package cn.nyse.aspect;

import cn.nyse.entity.SysLog;
import cn.nyse.entity.SysUser;
import cn.nyse.service.SysLogService;
import cn.nyse.utils.IPUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 日志切面类
 * 正常日志、异常日志
 * */
@Aspect
@Component
public class LogAspect {

    @Autowired
    private SysLogService sysLogService;

    @Autowired
    private HttpServletRequest request;

    @Pointcut("within((cn.nyse.service.impl.*impl))")
    public void pointcut(){}

    //joinPoint  连接点
    @AfterReturning(pointcut = "pointcut()")
    public void afterReturning(JoinPoint joinPoint){
        String className = joinPoint.getTarget().getClass().getSimpleName();
        if(!"SysLogServiceImpl".equals(className)){
            insertLog(joinPoint,null);
        }

    }

    @AfterThrowing(pointcut = "pointcut()",throwing = "e")
    public void throwing(JoinPoint joinPoint,Exception e){
        insertLog(joinPoint,e);
    }

    private void insertLog(JoinPoint joinPoint, Exception e) {
        SysLog sysLog = new SysLog();

        if(request!=null){
            SysUser loginUser= (SysUser) request.getSession().getAttribute("loginUser");
            sysLog.setCreateBy(loginUser.getName()!=null?loginUser.getName():"");
            sysLog.setRemoteAddr(IPUtils.getClientAddress(request));
            sysLog.setRequestUri(request.getRequestURI());
            sysLog.setUserAgent(request.getHeader("User-Agent"));
            sysLog.setMethod(request.getMethod());
        }
        sysLog.setType(e==null?"1":"2");
        sysLog.setException(e==null?"无":e.toString());

        //设置方法参数
        Object[] args = joinPoint.getArgs();
        if(args!=null&&args.length>0){
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < args.length; i++) {
                sb.append("[参数").append(i+1).append(",类型：").append(args[i].getClass().getSimpleName()).append(",值：").append(args[i]).append("]");
            }
        }


        sysLog.setCreateDate(new Date());

        sysLogService.insertSelective(sysLog);

    }


}
