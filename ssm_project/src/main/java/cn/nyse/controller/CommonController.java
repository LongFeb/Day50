package cn.nyse.controller;

import cn.nyse.entity.Result;
import cn.nyse.entity.SysResource;
import cn.nyse.entity.SysUser;
import cn.nyse.service.SysResourceService;
import cn.nyse.service.SysUserService;
import cn.nyse.utils.EncryptUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("common")
public class CommonController {

    @Autowired
    SysUserService service;

    @Autowired
    SysResourceService resourceService;

    @RequestMapping("select")
    @ResponseBody
    public Result select(String username, String password, String code, HttpSession session){
        String checkCode=(String)session.getAttribute("checkCode");
        SysUser loginUser=null;
        //验证验证码
        if(checkCode!=null&&checkCode.equals(code)){
            SysUser sysUser = new SysUser();
            sysUser.setUsername(username);
            sysUser.setPassword(EncryptUtils.MD5_HEX(EncryptUtils.MD5_HEX(password)+username));
            loginUser = service.selectOne(sysUser);

            //验证密码
            if(loginUser!=null){
                loginUser.setPassword(null); //清除密码，避免传输
                List <SysResource> resources = resourceService.selectByUid(loginUser.getId());
                loginUser.setResources(resources);

                session.setAttribute("loginUser",loginUser);
                return new Result(true,"登录成功",loginUser);
            }else {
                return new Result(false,"账号或密码错误",null);
            }
        }

        return new Result(false,"验证码错误",null);
    }

    @RequestMapping("/sidebar")
    public  String sidebar(){
        return "/common/sidebar";
    }

    @RequestMapping("/navbar")
    public  String navbar(){
        return "/common/navbar";
    }

    @RequestMapping("/index")
    public  String index(){
        return "/index";
    }

    @RequestMapping("/logout")
    public  String logout(HttpSession session){
        session.invalidate();//清空
        return "redirect:/login.html";
    }
}
