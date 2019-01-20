package com.rbs.project.secruity;

import com.alibaba.fastjson.JSON;
import com.rbs.project.pojo.RespInfo;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 12:20 2018/12/10
 */
@Component
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        RespInfo respInfo = new RespInfo();

        respInfo.setStatus(400);
        respInfo.setMsg("Need Authorities");

        httpServletResponse.setStatus(400);
        httpServletResponse.getWriter().write(JSON.toJSONString(respInfo));
    }
}
