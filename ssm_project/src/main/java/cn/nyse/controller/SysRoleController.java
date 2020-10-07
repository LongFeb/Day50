package cn.nyse.controller;

import cn.nyse.entity.Result;
import cn.nyse.entity.SysUser;
import cn.nyse.service.SysOfficeService;
import cn.nyse.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.Map;

@RestController
@RequestMapping("/manager/role")
public class SysRoleController {

    @Autowired
    SysRoleService service;

    @RequestMapping("")
    public ModelAndView index(){
        return new ModelAndView("/role/role");
    }

    @RequestMapping("toUser")
    public ModelAndView toUser(){
        return new ModelAndView("/role/role-user");
    }

    @RequestMapping("toUpdate")
    public ModelAndView toUpdate(){
        return new ModelAndView("/role/role-save");
    }

    @RequestMapping("selectPage/{pageNum}/{pageSize}")
    public Result selectPage(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize, @RequestParam Map<String,Object> params){
        return new Result(true,"查询成功",service.selectPage(pageNum,pageSize,params));
    }

    @RequestMapping("deleteBatch")
    public Result deleteBatch(long rid,long[] ids){
        return new Result(true,"移除人员成功",service.deleteBatch(rid,ids));
    }

    @RequestMapping("insertBatch")
    public Result insertBatch(long rid,Long[] cids){
        return new Result(true,"添加人员成功",service.insertBatch(rid, Arrays.asList(cids)));
    }

    @RequestMapping("selectOne")
    public Result selectOne(long rid){
        return new Result(true,"查询成功",service.selectByPrimaryKey(rid));
    }

    @RequestMapping("update")
    public Result update(@SessionAttribute("loginUser")SysUser sysUser,@RequestBody Map<String,Object> params){
        return new Result(true,"修改成功",service.updateByPrimaryKeySelective(sysUser.getName(),params));
    }
}
