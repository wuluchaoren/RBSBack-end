package com.rbs.project.secruity;

import com.rbs.project.pojo.entity.User;
import com.rbs.project.secruity.jwt.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 13:47 2018/12/10
 */
@Component
public class MyAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username=(String) authentication.getPrincipal();
        String password=(String) authentication.getCredentials();

        User jwtUser=jwtUserDetailsService.loadUserByUsername(username);
        if(!jwtUser.getPassword().equals(password)){
            throw new BadCredentialsException("用户名密码不正确，请重新登陆！");
        }

        return new UsernamePasswordAuthenticationToken(username, password, jwtUser.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
