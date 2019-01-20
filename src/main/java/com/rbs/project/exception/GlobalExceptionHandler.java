package com.rbs.project.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 11:48 2018/12/16
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = MyException.class)
    @ResponseBody
    public ErrorInfo<String> jsonErrorHandler(HttpServletRequest req, HttpServletResponse response, MyException e){
        ErrorInfo<String> r = new ErrorInfo<>();
        e.printStackTrace();
        r.setMessage(e.getMessage());
        r.setCode(e.getStateCode());
        r.setData("None Data");
        r.setUrl(req.getRequestURL().toString());
        response.setStatus(r.getCode());
        return r;
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ErrorInfo<String> unKnownErrorHandler(HttpServletRequest req, HttpServletResponse response, Exception e) {
        ErrorInfo<String> r = new ErrorInfo<>();
        r.setMessage(e.getMessage());
        r.setCode(500);
        e.printStackTrace();
        r.setData("不为人知的内部错误");
        r.setUrl(req.getRequestURL().toString());
        response.setStatus(r.getCode());
        return r;
    }

}
