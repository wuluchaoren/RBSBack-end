package com.rbs.project.secruity;

import com.alibaba.fastjson.JSON;
import com.rbs.project.pojo.RespInfo;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 12:22 2018/12/10
 */
@Component
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        RespInfo respInfo=new RespInfo();


        respInfo.setStatus(401);
        respInfo.setMsg("Login Failure!");

        httpServletResponse.setStatus(401);
        httpServletResponse.getWriter().write(JSON.toJSONString(respInfo));
    }
}
