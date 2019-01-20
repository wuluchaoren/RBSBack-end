package com.rbs.project.secruity;

import com.rbs.project.pojo.entity.Admin;
import com.rbs.project.pojo.entity.Student;
import com.rbs.project.pojo.entity.Teacher;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;


/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 12:52 2018/12/10
 */
@Component("RBAC")
public class RbacAuthorityDecision {

    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {

        Object userInfo = authentication.getPrincipal();

        boolean hasPermission = false;

        //获取资源
        Set<String> urls = new HashSet();
        Set<UrlAuthorization> urlAuthorizations = new HashSet<>();
        urlAuthorizations.add(new UrlAuthorization("/**/**").addAllMethod());
        urlAuthorizations.add(new UrlAuthorization("/user/**").addAllMethod());

        if (userInfo instanceof Student) {
            System.out.println("我是学生" + ((Student) userInfo).getStudentName());
            urlAuthorizations.add(new UrlAuthorization("/course/**").addGetMethod());
            urlAuthorizations.add(new UrlAuthorization("/seminar/**").addGetMethod().addPostMethod());
            urlAuthorizations.add(new UrlAuthorization("/team/**").addAllMethod());
            urlAuthorizations.add(new UrlAuthorization("/attendance/**").addAllMethod());
            urlAuthorizations.add(new UrlAuthorization("/seminarscore/**").addGetMethod());
            urlAuthorizations.add(new UrlAuthorization("/roundscore/**").addGetMethod());
            urlAuthorizations.add(new UrlAuthorization("/request/**").addAllMethod());

        } else if (userInfo instanceof Teacher) {
            System.out.println("我是老师" + ((Teacher) userInfo).getTeacherName());
            urlAuthorizations.add(new UrlAuthorization("/course/**").addAllMethod());
            urlAuthorizations.add(new UrlAuthorization("/seminar/**").addAllMethod());
            urlAuthorizations.add(new UrlAuthorization("/class/**").addAllMethod());
            urlAuthorizations.add(new UrlAuthorization("/team/**").addAllMethod());
            urlAuthorizations.add(new UrlAuthorization("/attendance/**").addAllMethod());
            urlAuthorizations.add(new UrlAuthorization("/seminarscore/**").addAllMethod());
            urlAuthorizations.add(new UrlAuthorization("/roundscore/**").addAllMethod());
            urlAuthorizations.add(new UrlAuthorization("/request/**").addAllMethod());


        } else if (userInfo instanceof Admin) {
            System.out.println("我是管理员" + ((Admin) userInfo).getUsername());
            urlAuthorizations.add(new UrlAuthorization("/**/**").addAllMethod());
        }

        //当前接口和权限接口进行匹配
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        for (UrlAuthorization urlAuthorization : urlAuthorizations) {
            //判断接口url是否相等 并判断接口url的请求方法
            if (antPathMatcher.match(urlAuthorization.getUrl(), request.getRequestURI())
                    && urlAuthorization.getMethod().contains(request.getMethod())) {
                hasPermission = true;
            }
        }

        return hasPermission;
    }
}
