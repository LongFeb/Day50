package cn.nyse.controller;

import cn.nyse.entity.AppVersion;
import cn.nyse.entity.SysOffice;
import cn.nyse.entity.Result;
import cn.nyse.service.SysOfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/manager/office")
public class SysOfficeController {

    @Autowired
    SysOfficeService service;

    @RequestMapping("")
    public ModelAndView index(){
        return new ModelAndView("/office/office");
    }

    @RequestMapping("toDetail")
    public ModelAndView toDetail(){
        return new ModelAndView("/office/detail");
    }

    @RequestMapping("toUpdate")
    public ModelAndView toUpdate(){
        return new ModelAndView("/office/save");
    }

    @RequestMapping("selectPage/{pageNum}/{pageSize}")
    public Result selectPage(@PathVariable("pageNum") int pageNum,@PathVariable("pageSize") int pageSize,@RequestParam Map<String,Object> params){
        return new Result(true,"查询成功",service.selectPage(pageNum,pageSize,params));
    }

    @RequestMapping("select")
    public Result select(){
        SysOffice sysOffice=new SysOffice();
        sysOffice.setDelFlag("0");
        return new Result(true,"查询成功",service.select(sysOffice));
    }

    @RequestMapping("selectOne/{id}")
    public Result selectOne(@PathVariable("id") long oid){
        return new Result(true,"查询成功",service.selectByOid(oid));
    }

    @RequestMapping("selectByRid")
    public Result selectByRid(long rid){
        return new Result(true,"查询成功",service.selectByRid(rid));
    }

    @RequestMapping(path = "doUpdate",method = RequestMethod.PUT)
    public Result doUpdate(@RequestBody SysOffice sysOffice){
        sysOffice.setUpdateDate(new Date());
        return new Result(true,"更新成功",service.updateByPrimaryKeySelective(sysOffice));
    }
}
