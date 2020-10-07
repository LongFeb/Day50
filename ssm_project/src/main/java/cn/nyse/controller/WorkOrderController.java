package cn.nyse.controller;

import cn.nyse.entity.Result;
import cn.nyse.service. WorkOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@RestController
@RequestMapping("/manager/admin/work")
public class WorkOrderController {

    @Autowired
     WorkOrderService  workOrderService;

    @RequestMapping("")
    public ModelAndView index(){
        return new ModelAndView("/work/admin/index");
    }


    @RequestMapping("toDetail")
    public ModelAndView toDetail(){
        return new ModelAndView("/work/work-detail");
    }


    @RequestMapping("selectPage/{pageNum}/{pageSize}")
    public Result selectPage(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize, @RequestParam Map<String,Object> params){
        return new Result(true,"查询成功", workOrderService.selectPage(pageNum,pageSize,params));
    }

    @RequestMapping("selectDetail/{id}")
    public Result selectDetail(@PathVariable("id")long id){
        return new Result(true,"查询成功",workOrderService.selectDetail(id));
    }




}
