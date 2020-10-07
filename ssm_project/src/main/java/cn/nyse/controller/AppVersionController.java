package cn.nyse.controller;

import cn.nyse.entity.AppVersion;
import cn.nyse.entity.Result;
import cn.nyse.service.AppVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

@RestController
@RequestMapping("/manager/app")
public class AppVersionController {

    @Autowired
    AppVersionService service;

    @RequestMapping("index")
    public ModelAndView index(){
        return new ModelAndView("/app/index");
    }

    @RequestMapping("select")
    public Result select(){
        AppVersion app=new AppVersion();
        app.setDelFlag("0");
        return new Result(true,"查询成功",service.select(app));
    }

    @RequestMapping("selectPage/{pageNum}/{pageSize}")
    public Result selectPage(@PathVariable("pageNum") int pageNum,@PathVariable("pageSize") int pageSize){
        return new Result(true,"查询成功",service.selectPage(pageNum,pageSize));
    }

    @RequestMapping("toUpdate")
    public ModelAndView toUpdate(){
        return new ModelAndView("/app/update");
    }

    @RequestMapping("selectOne/{id}")
    public Result selectOne(@PathVariable("id") long id){
        return new Result(true,"查询成功",service.selectByPrimaryKey(id));
    }

    @RequestMapping(path = "doUpdate",method = RequestMethod.PUT)
    public Result doUpdate(@RequestBody AppVersion appVersion){
        appVersion.setUpdateDate(new Date());
        return new Result(true,"更新成功",service.updateByPrimaryKeySelective(appVersion));
    }

    @RequestMapping("insert")
    public Result insert(@RequestBody AppVersion appVersion){
        appVersion.setDelFlag("0");
        Date date = new Date();
        appVersion.setCreateDate(date);
        appVersion.setUpdateDate(date);
        return new Result(true,"插入成功",service.insertSelective(appVersion));
    }

    @RequestMapping("toDetail")
    public ModelAndView toDetail(){
        return new ModelAndView("/app/detail");
    }
}
