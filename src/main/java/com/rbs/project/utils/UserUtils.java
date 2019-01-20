package com.rbs.project.utils;

import com.rbs.project.exception.MyException;
import com.rbs.project.pojo.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 15:13 2018/12/16
 */
public class UserUtils {

    public static User getNowUser() throws MyException {
        User nowUser=(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        //如果用户为空，抛出异常
        if (nowUser == null) {
            throw new MyException("错误！当前无用户", MyException.ERROR);
        }

        return nowUser;
    }
}
