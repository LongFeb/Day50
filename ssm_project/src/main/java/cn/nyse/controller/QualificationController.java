package cn.nyse.controller;

import cn.nyse.entity.Qualification;
import cn.nyse.entity.QualificationCondition;
import cn.nyse.entity.Result;
import cn.nyse.service.QualificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

@RestController
@RequestMapping("/manager/qualification")
public class QualificationController {

    @Autowired
    QualificationService qualificationService;

    @RequestMapping("index")
    public ModelAndView index(){
        return new ModelAndView("/qualification/index");
    }


    @RequestMapping("selectPage/{pageNum}/{pageSize}")
    public Result selectPage(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize, QualificationCondition condition){
        return new Result(true,"查询成功",qualificationService.selectPage(pageNum,pageSize,condition));
    }

    @RequestMapping("toUpdate")
    public ModelAndView toUpdate(){
        return new ModelAndView("/qualification/update");
    }

    @RequestMapping("selectOne/{id}")
    public Result selectOne(@PathVariable("id") long id){
        return new Result(true,"查询成功",qualificationService.selectByPrimaryKey(id));
    }

    @RequestMapping(path = "doUpdate",method = RequestMethod.PUT)
    public Result doUpdate(@RequestBody Qualification qualification){
        qualification.setUpdateDate(new Date());
        return new Result(true,"更新成功",qualificationService.updateByPrimaryKeySelective(qualification));
    }

}
