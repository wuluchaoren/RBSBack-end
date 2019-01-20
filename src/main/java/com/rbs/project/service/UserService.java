package com.rbs.project.service;

import com.rbs.project.dao.StudentDao;
import com.rbs.project.dao.TeacherDao;
import com.rbs.project.dao.UserDao;
import com.rbs.project.exception.MyException;
import com.rbs.project.pojo.entity.Student;
import com.rbs.project.pojo.entity.Teacher;
import com.rbs.project.pojo.entity.User;
import com.rbs.project.utils.EmailUtils;
import com.rbs.project.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 12:04 2018/12/16
 */
@Service
public class UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private StudentDao studentDao;
    @Autowired
    private TeacherDao teacherDao;

    /**
     * Description: 激活用户，包括学生和老师
     *
     * @Author: 17Wang
     * @Time: 13:09 2018/12/16
     */
    public boolean userActivation(User user) throws MyException {
        User nowUser = UserUtils.getNowUser();
        if (user.getPassword().equals(nowUser.getPassword())) {
            throw new MyException("激活密码不能和初始密码一样", MyException.ERROR);
        }
        //判断json格式，密码不能为空
        if (user.getPassword() == null) {
            throw new MyException("激活失败！密码不能为空", MyException.ERROR);
        }
        user.setActive(true);
        user.setId(nowUser.getId());
        if (user instanceof Student) {
            studentDao.updatePasswordAndEmailAndActiveByStudent((Student) user);
        } else if (user instanceof Teacher) {
            user.setEmail(nowUser.getEmail());
            teacherDao.updatePasswordAndEmailAndActiveByTeacher((Teacher) user);
        }

        return true;
    }

    /**
     * Description: 找回密码
     *
     * @Author: 17Wang
     * @Time: 14:33 2018/12/16
     */
    public boolean getPassword(String account) throws MyException {
        User user = userDao.getUserByUsername(account);
        if (user == null) {
            throw new MyException("找回密码失败！不存在该用户", MyException.NOT_FOUND_ERROR);
        }
        if (user.getEmail() == null || !EmailUtils.checkEmailFormat(user.getEmail())) {
            throw new MyException("找回密码失败！邮件名错误", MyException.ERROR);
        }
        boolean isSend = EmailUtils.sendEmail("找回密码", new String[]{user.getEmail()}, new String[]{"ooadmail2_3@126.com"}, "<p>你的密码是：" + user.getPassword() + "</p>", null);
        if (!isSend) {
            throw new MyException("找回密码失败！发送邮件错误", MyException.ERROR);
        }
        return isSend;
    }

    /**
     * Description: 修改密码
     *
     * @Author: 17Wang
     * @Time: 15:15 2018/12/16
     */
    public boolean resetPassword(String password) throws MyException {
        //获取当前用户信息
        User user;
        try {
            user = UserUtils.getNowUser();
        } catch (MyException e) {
            throw new MyException("修改密码失败！当前无用户 " + e.getMessage(), MyException.NOT_FOUND_ERROR);
        }


        //放入需要设置的密码
        user.setPassword(password);
        if (user instanceof Student) {
            studentDao.updatePasswordByStudent((Student) user);
        } else if (user instanceof Teacher) {
            teacherDao.updatePasswordByTeacher((Teacher) user);
        } else {
            throw new MyException("修改密码失败！未知错误", MyException.ERROR);
        }
        return true;
    }

    /**
     * Description: 修改邮箱
     *
     * @Author: 17Wang
     * @Time: 16:02 2018/12/16
     */
    public boolean resetEmail(String email) throws MyException {
        //获取当前用户信息
        User user;
        try {
            user = UserUtils.getNowUser();
        } catch (MyException e) {
            throw new MyException("修改邮箱失败！当前无用户 " + e.getMessage(), MyException.NOT_FOUND_ERROR);
        }

        //放入需要设置的邮箱
        if (email != null) {
            user.setEmail(email);
        } else {
            user.setEmail(" ");
        }

        if (user instanceof Student) {
            studentDao.updateEmailByStudent((Student) user);
        } else if (user instanceof Teacher) {
            teacherDao.updateEmailByTeacher((Teacher) user);
        } else {
            throw new MyException("修改邮箱失败！未知错误", MyException.ERROR);
        }
        return true;
    }
}
