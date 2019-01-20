package com.rbs.project.secruity;

import com.alibaba.fastjson.JSON;
import com.rbs.project.pojo.RespInfo;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 12:17 2018/12/10
 */
@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        RespInfo respInfo=new RespInfo();

        respInfo.setStatus(300);
        respInfo.setMsg("Need Authorities");

        httpServletResponse.setStatus(300);
        httpServletResponse.getWriter().write(JSON.toJSONString(respInfo));
    }
}
