package cn.nyse.controller;

import cn.nyse.entity.Result;
import cn.nyse.entity.SysOffice;
import cn.nyse.service.SysLogService;
import cn.nyse.service.SysOfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@RestController
@RequestMapping("/manager/syslog")
public class SysLogController {

    @Autowired
    SysLogService service;

    @RequestMapping("")
    public ModelAndView index(){
        return new ModelAndView("/log/log");
    }

    @RequestMapping("toDetail")
    public ModelAndView toDetail(){
        return new ModelAndView("/log/log-detail");
    }


    @RequestMapping("selectPage/{pageNum}/{pageSize}")
    public Result selectPage(@PathVariable("pageNum") int pageNum,@PathVariable("pageSize") int pageSize){
        return new Result(true,"查询成功",service.selectPage(pageNum,pageSize));
    }

    @RequestMapping("selectOne/{id}")
    public Result selectOne(@PathVariable("id") long id){
        return new Result(true,"查询成功",service.selectByPrimaryKey(id));
    }


}
