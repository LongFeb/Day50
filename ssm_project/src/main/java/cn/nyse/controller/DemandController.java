package cn.nyse.controller;

import cn.nyse.entity.Demand;
import cn.nyse.entity.Result;
import cn.nyse.service.DemandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

@RestController
@RequestMapping("/manager/demand")
public class DemandController {
    @Autowired
    DemandService demandService;

    @RequestMapping("index")
    public ModelAndView index(){
        return new ModelAndView("/demand/index");
    }


    @RequestMapping("selectPage/{pageNum}/{pageSize}")
    public Result selectPage(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize){
        return new Result(true,"查询成功",demandService.selectPage(pageNum,pageSize));
    }

    @RequestMapping("toUpdate")
    public ModelAndView toUpdate(){
        return new ModelAndView("/demand/update");
    }

    @RequestMapping("selectOne/{id}")
    public Result selectOne(@PathVariable("id") long id){
        return new Result(true,"查询成功",demandService.selectByPrimaryKey(id));
    }

    @RequestMapping(path = "doUpdate",method = RequestMethod.PUT)
    public Result doUpdate(@RequestBody Demand demand){
        demand.setUpdateDate(new Date());
        return new Result(true,"更新成功",demandService.updateByPrimaryKeySelective(demand));
    }

    @RequestMapping("toDetail")
    public ModelAndView toDetail(){
        return new ModelAndView("/demand/detail");
    }
}
