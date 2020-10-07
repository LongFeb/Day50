package cn.nyse.controller;

import cn.nyse.entity.Result;
import cn.nyse.entity.SysArea;
import cn.nyse.service.SysAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/manager/area")
public class SysAreaController {

    @Autowired
    SysAreaService service;

    @RequestMapping("")
    public ModelAndView index(){
        return new ModelAndView("/area/area");
    }

    @RequestMapping("toUpdate")
    public ModelAndView toUpdate(){
        return new ModelAndView("/area/update");
    }

    @RequestMapping("toModules")  //跳转到图标选择页
    public ModelAndView toModules(){
        return new ModelAndView("/modules/font-awesome");
    }

    @RequestMapping("toSelect")
    public ModelAndView toSelect(){
        return new ModelAndView("/area/select");
    }

    @RequestMapping("toDetail")
    public ModelAndView toDetail(){
        return new ModelAndView("/area/detail");
    }

    @RequestMapping("selectOne/{id}")
    public Result selectOne(@PathVariable("id") long id){
        return new Result(true,"查询成功",service.selectByAid(id));
    }

    @RequestMapping(path = "doUpdate",method = RequestMethod.PUT)
    public Result doUpdate(@RequestBody SysArea sysArea){
        sysArea.setUpdateDate(new Date());
        return new Result(true,"更新成功",service.updateByPrimaryKeySelective(sysArea));
    }

    @RequestMapping("select")
    public Result select(){
        SysArea area=new SysArea();
        area.setDelFlag("0");
        return new Result(true,"查询成功",service.select(area));
    }

    @RequestMapping("selectParent/{id}")
    public Result selectParent(@PathVariable("id") long id){
        return new Result(true,"查询成功",service.selectParent(id));
    }

    @RequestMapping("selectPage/{pageNum}/{pageSize}")
    public Result selectPage(@PathVariable("pageNum") int pageNum,@PathVariable("pageSize") int pageSize,@RequestParam Map<String,Object> params){
        return new Result(true,"查询成功",service.selectPage(pageNum,pageSize,params));
    }

    @RequestMapping("upload")
    public Result upload(MultipartFile file) throws IOException {
        service.upload(file.getInputStream());
        return new Result(true,"上传成功",null);
    }
    //1.设置响应头，告诉浏览器以附件的方式下载到客户端(content-disposition: attachment;filename='xxx')
    //2.读取数据，写入到excel
    //3.获取response的输出流，将excel放入输出
    @RequestMapping("download")
    public void download(HttpServletResponse response) throws IOException{
        String fileName=new String("区域.xlsx".getBytes(),"iso-8859-1");
        response.setHeader("content-disposition","attachment;filename="+fileName);
        service.download(response.getOutputStream());
    }


}
