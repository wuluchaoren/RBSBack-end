package com.rbs.project.secruity;


import com.rbs.project.secruity.jwt.JwtAuthenticationTokenFilter;
import com.rbs.project.secruity.jwt.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 23:42 2018/12/9
 */
@Configuration
@EnableWebSecurity
@ComponentScan
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MySecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    private MyAuthenticationEntryPoint myAuthenticationEntryPoint;

    @Autowired
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;

    @Autowired
    private MyAuthenticationFailureHandler myAuthenticationFailureHandler;

    @Autowired
    private MyLogoutSuccessHandler myLogoutSuccessHandler;

    @Autowired
    private MyAccessDeniedHandler myAccessDeniedHandler;

    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Autowired
    private MyAuthenticationProvider myAuthenticationProvider;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(myAuthenticationProvider);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //super.configure(web);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                //禁用session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()

                .httpBasic().authenticationEntryPoint(myAuthenticationEntryPoint)

                .and()
                .authorizeRequests()
                .antMatchers("/rbs-websocket/**").permitAll()
                .antMatchers("/app/**").permitAll()
                .antMatchers("/topic/**").permitAll()

                .anyRequest()
                .access("@RBAC.hasPermission(request,authentication)")

                .and()
                //开启表单登录
                .formLogin()

                //自定义登录url，可删除，默认为login
                .loginProcessingUrl("/user/login").usernameParameter("account").passwordParameter("password")
                // 登录成功
                .successHandler(myAuthenticationSuccessHandler)
                // 登录失败
                .failureHandler(myAuthenticationFailureHandler)
                .permitAll()

                .and()
                .logout()
                .logoutSuccessHandler(myLogoutSuccessHandler)
                .permitAll();

        // 记住我
        http.rememberMe().rememberMeParameter("remember-me")
                .userDetailsService(jwtUserDetailsService).tokenValiditySeconds(7 * 24 * 60 * 60);

        // 无权访问 JSON 格式的数据
        http.exceptionHandling().accessDeniedHandler(myAccessDeniedHandler);
        // JWT Filter
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        //禁用缓存
        http.headers().cacheControl();

//        http.authorizeRequests().antMatchers("/**/**").permitAll()
//                .anyRequest().permitAll()
//                .and().csrf().disable();
    }
}
