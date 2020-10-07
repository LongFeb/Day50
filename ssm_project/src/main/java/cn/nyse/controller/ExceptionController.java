package cn.nyse.controller;

import cn.nyse.entity.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice //全局异常处理
public class ExceptionController {
    @ExceptionHandler
    @ResponseBody
    public Result doException(Exception e){
        return new Result(false,"处理失败",e.getMessage());
    }
}
