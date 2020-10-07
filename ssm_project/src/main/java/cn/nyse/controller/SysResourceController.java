package cn.nyse.controller;


import cn.nyse.entity.Result;
import cn.nyse.entity.SysResource;
import cn.nyse.service.SysResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;




@RestController
@RequestMapping("/manager/menu")
public class SysResourceController {

    @Autowired
    SysResourceService service;

    @RequestMapping("select")
    public Result select(){
        SysResource resource = new SysResource();
        resource.setDelFlag("0");
        resource.setType("0");
        return new Result(true,"查询成功",service.select(resource));
    }


    @RequestMapping("selectByRid")
    public Result selectByRid(long rid){
        return new Result(true,"查询成功",service.selectByRid(rid));
    }


}
