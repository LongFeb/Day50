package cn.nyse.controller;

import cn.nyse.entity.Statute;
import cn.nyse.entity.Result;
import cn.nyse.service.StatuteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

@RestController
@RequestMapping("/manager/statute")
public class StatuteController {

    @Autowired
     StatuteService  statuteService;

    @RequestMapping("index")
    public ModelAndView index(){
        return new ModelAndView("/statute/index");
    }

    @RequestMapping("toUpdate")
    public ModelAndView toUpdate(){
        return new ModelAndView("/statute/update");
    }


    @RequestMapping("selectPage/{pageNum}/{pageSize}")
    public Result selectPage(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize, Integer type){
        return new Result(true,"查询成功", statuteService.selectPage(pageNum,pageSize,type));
    }

    @RequestMapping("selectOne/{id}")
    public Result selectOne(@PathVariable("id") long id){
        return new Result(true,"查询成功",statuteService.selectByPrimaryKey(id));
    }

    @RequestMapping(path = "doUpdate",method = RequestMethod.PUT)
    public Result doUpdate(@RequestBody Statute statute){
        statute.setUpdateDate(new Date());
        return new Result(true,"更新成功",statuteService.updateByPrimaryKeySelective(statute));
    }

    @RequestMapping("insert")
    public Result insert(@RequestBody Statute statute){
        statute.setDelFlag("0");
        Date date = new Date();
        statute.setCreateDate(date);
        statute.setUpdateDate(date);
        return new Result(true,"插入成功",statuteService.insertSelective(statute));
    }




}
