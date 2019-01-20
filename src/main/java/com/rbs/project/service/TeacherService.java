package com.rbs.project.service;

import com.rbs.project.dao.TeacherDao;
import com.rbs.project.exception.MyException;
import com.rbs.project.pojo.entity.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 16:51 2018/12/16
 */
@Service
public class TeacherService {
    @Autowired
    private TeacherDao teacherDao;

    /**
     * Description: 获取所有老师信息
     *
     * @Author: 17Wang
     * @Time: 16:52 2018/12/16
     */
    public List<Teacher> listAllTeachers() {
        return teacherDao.listAllTeachers();
    }

    /**
     * Description:通过姓名或者学号查询一个老师信息
     *
     * @Author: 17Wang
     * @Time: 23:49 2018/12/17
     */
    public Teacher findOneTeacher(String identity) throws MyException {
        Teacher teacher;
        try {
            teacher = teacherDao.getTeacherByAccount(identity);
        } catch (MyException e) {
            try {
                teacher = teacherDao.getTeacherByTeacherName(identity);
            } catch (MyException e1) {
                throw new MyException("通过姓名或者学号查询一个教师信息出错！", MyException.NOT_FOUND_ERROR);
            }
        }
        return teacher;
    }

    /**
     * Description:管理员修改某一老师的信息
     *
     * @Author: 17Wang
     * @Time: 23:23 2018/12/16
     */
    /**
     * Description:管理员修改某一老师的信息
     *
     * @Author: 17Wang
     * @Time: 23:53 2018/12/17
     */
    @Transactional(rollbackFor = Exception.class)
    public Teacher resetTeacherInfo(Teacher teacher) throws MyException {
        Teacher temp = teacherDao.getTeacherById(teacher.getId());

        //修改老师账号
        if (!temp.getUsername().equals(teacher.getUsername())) {
            temp.setAccount(teacher.getUsername());
            teacherDao.updateAccountByTeacher(temp);
        }

        //修改老师名字
        if (!temp.getTeacherName().equals(teacher.getTeacherName())) {
            temp.setTeacherName(teacher.getTeacherName());
            teacherDao.updateTeacherNameByTeacher(temp);
        }
        //修改老师邮箱
        if (!temp.getEmail().equals(teacher.getEmail())) {
            temp.setEmail(teacher.getEmail());
            teacherDao.updateEmailByTeacher(temp);
        }

        return temp;
    }

    /**
     * Description: 修改老师密码
     *
     * @Author: 17Wang
     * @Time: 23:56 2018/12/17
     */
    @Transactional(rollbackFor = Exception.class)
    public Teacher resetTeacherPassword(Teacher teacher) throws MyException {
        Teacher temp = teacherDao.getTeacherById(teacher.getId());

        //修改老师密码
        if (!temp.getPassword().equals(teacher.getPassword())) {
            temp.setPassword(teacher.getPassword());
            teacherDao.updatePasswordByTeacher(temp);
        }

        return temp;
    }

    /**
     * Description:删除老师
     *
     * @Author: 17Wang
     * @Time: 23:56 2018/12/17
     */
    public boolean deleteTeacher(long teacherId) throws MyException {
        return teacherDao.deleteTeacherByTeacherId(teacherId);
    }

    /**
     * Description: 创建老师
     * @Author: 17Wang
     * @Time: 15:42 2018/12/18
    */
    public boolean createTeacher(Teacher teacher) throws MyException {
        return teacherDao.addTeacher(teacher);
    }
}
