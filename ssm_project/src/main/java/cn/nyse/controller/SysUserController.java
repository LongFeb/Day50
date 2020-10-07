package cn.nyse.controller;

import cn.nyse.entity.Result;
import cn.nyse.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/manager/sysuser")
public class SysUserController {
    @Autowired
    SysUserService service;

    @RequestMapping("")
    public ModelAndView index(){
        return new ModelAndView("/user/user");
    }

    @RequestMapping("selectByRid")
    public Result selectByRid(long rid){
        return new Result(true,"查询成功",service.selectByRid(rid));
    }

    @RequestMapping("selectNoRole")
    public Result selectNoRole(long rid,long oid){
        return new Result(true,"查询成功",service.selectNoRole(rid,oid));
    }



}
