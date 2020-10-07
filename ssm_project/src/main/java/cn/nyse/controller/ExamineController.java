package cn.nyse.controller;

import cn.nyse.entity.Result;
import cn.nyse.service.ExamineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@RestController
@RequestMapping("/manager/examine")
public class ExamineController {

    @Autowired
    ExamineService examineService;

    @RequestMapping("index")
    public ModelAndView index(){
        return new ModelAndView("/examine/index");
    }


    @RequestMapping("selectPage/{pageNum}/{pageSize}")
    public Result selectPage(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize, @RequestParam Map<String,Object> params){
        return new Result(true,"查询成功",examineService.selectPage(pageNum,pageSize,params));
    }


}
