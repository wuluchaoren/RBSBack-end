package com.rbs.project.secruity.jwt;

import com.rbs.project.dao.UserDao;
import com.rbs.project.pojo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 10:21 2018/12/10
 */
@Service
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    private UserDao userDao;

    @Override
    public User loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userDao.getUserByUsername(s);

        if (user == null) {
            throw new UsernameNotFoundException("Username not found");
        }

        return user;
    }
}
