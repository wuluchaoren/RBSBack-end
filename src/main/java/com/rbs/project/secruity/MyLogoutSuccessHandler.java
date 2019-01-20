package com.rbs.project.secruity;

import com.alibaba.fastjson.JSON;
import com.rbs.project.pojo.RespInfo;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 12:26 2018/12/10
 */
@Component
public class MyLogoutSuccessHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        RespInfo respInfo=new RespInfo();

        respInfo.setStatus(100);
        respInfo.setMsg("Logout Success!");

        httpServletResponse.setStatus(100);
        httpServletResponse.getWriter().write(JSON.toJSONString(respInfo));
    }
}
