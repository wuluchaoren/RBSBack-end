package com.rbs.project.dao;

import com.rbs.project.exception.MyException;
import com.rbs.project.mapper.TeacherMapper;
import com.rbs.project.pojo.entity.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 16:49 2018/12/16
 */
@Repository
public class TeacherDao {
    @Autowired
    private TeacherMapper teacherMapper;

    /**
     * Description: 通过id返回老师信息
     *
     * @Author: 17Wang
     * @Time: 12:53 2018/12/16
     */
    public Teacher getTeacherById(long id) throws MyException {
        Teacher teacher = teacherMapper.findById(id);
        if (teacher == null) {
            throw new MyException("查询失败！不存在该用户", MyException.NOT_FOUND_ERROR);
        }
        return teacher;
    }

    /**
     * Description: 获取所有老师信息
     *
     * @Author: 17Wang
     * @Time: 16:50 2018/12/16
     */
    public List<Teacher> listAllTeachers() {
        return teacherMapper.listAll();
    }

    /**
     * Description: 通过教工号查找一个老师
     *
     * @Author: 17Wang
     * @Time: 23:58 2018/12/17
     */
    public Teacher getTeacherByAccount(String account) throws MyException {
        Teacher teacher = teacherMapper.findByAccount(account);
        if (teacher == null) {
            throw new MyException("通过学号查找老师出错！", MyException.NOT_FOUND_ERROR);
        }
        return teacher;
    }

    /**
     * Description:通过老师名查找一个老师
     *
     * @Author: 17Wang
     * @Time: 23:58 2018/12/17
     */
    public Teacher getTeacherByTeacherName(String teacherName) throws MyException {
        Teacher teacher = teacherMapper.findByTeacherName(teacherName);
        if (teacher == null) {
            throw new MyException("通过老师名查找老师出错", MyException.NOT_FOUND_ERROR);
        }
        return teacher;
    }

    /**
     * Description:激活教师号时的更新
     *
     * @Author: 17Wang
     * @Time: 12:34 2018/12/16
     */
    public boolean updatePasswordAndEmailAndActiveByTeacher(Teacher teacher) throws MyException {
        //判断是否存在该id
        try {
            getTeacherById(teacher.getId());
        } catch (MyException e) {
            throw new MyException("激活老师失败！不存在该用户", MyException.NOT_FOUND_ERROR);
        }

        if (!teacherMapper.updatePasswordAndEmailAndActiveById(teacher)) {
            throw new MyException("激活老师失败！数据库处理错误", MyException.ERROR);
        }

        return true;
    }

    /**
     * Description: 老师修改密码
     *
     * @Author: 17Wang
     * @Time: 15:26 2018/12/16
     */
    public boolean updatePasswordByTeacher(Teacher teacher) throws MyException {
        try {
            getTeacherById(teacher.getId());
        } catch (MyException e) {
            throw new MyException("老师修改密码错误！不存在该用户", MyException.NOT_FOUND_ERROR);
        }
        if (!teacherMapper.updatePasswordById(teacher)) {
            throw new MyException("老师修改密码错误！数据库处理错误", MyException.ERROR);
        }
        return true;
    }

    /**
     * Description: 老师修改邮箱
     *
     * @Author: 17Wang
     * @Time: 16:05 2018/12/16
     */
    public boolean updateEmailByTeacher(Teacher teacher) throws MyException {
        if (teacher.getEmail() == null) {
            teacher.setEmail(" ");
        }

        try {
            getTeacherById(teacher.getId());
        } catch (MyException e) {
            throw new MyException("老师修改邮箱错误！不存在该用户", MyException.NOT_FOUND_ERROR);
        }
        if (!teacherMapper.updateEmailById(teacher)) {
            throw new MyException("老师修改邮箱错误！数据库处理错误", MyException.ERROR);
        }
        return true;
    }

    /**
     * Description: 老师修改名字
     *
     * @Author: 17Wang
     * @Time: 23:34 2018/12/16
     */
    public boolean updateTeacherNameByTeacher(Teacher teacher) throws MyException {
        try {
            getTeacherById(teacher.getId());
        } catch (MyException e) {
            throw new MyException("老师修改名字错误！不存在该用户", MyException.NOT_FOUND_ERROR);
        }

        if (!teacherMapper.updateTeacherNameById(teacher)) {
            throw new MyException("老师修改名字错误！数据库处理错误", MyException.ERROR);
        }
        return true;
    }

    /**
     * Description: 老师修改学号
     *
     * @Author: 17Wang
     * @Time: 23:47 2018/12/16
     */
    public boolean updateAccountByTeacher(Teacher teacher) throws MyException {
        try {
            getTeacherById(teacher.getId());
        } catch (MyException e) {
            throw new MyException("老师修改学号错误！不存在该用户", MyException.NOT_FOUND_ERROR);
        }

        if (!teacherMapper.updateAccountById(teacher)) {
            throw new MyException("老师修改学号错误！数据库处理错误", MyException.ERROR);
        }
        return true;
    }

    /**
     * Description: 通过id删除老师
     *
     * @Author: 17Wang
     * @Time: 15:43 2018/12/18
     */
    public boolean deleteTeacherByTeacherId(long teacherId) throws MyException {
        try {
            getTeacherById(teacherId);
        } catch (MyException e) {
            throw new MyException("删除老师错误！不存在该用户", MyException.NOT_FOUND_ERROR);
        }

        return teacherMapper.deleteTeacherById(teacherId);
    }

    /**
     * Description: 新增老师
     *
     * @Author: 17Wang
     * @Time: 15:43 2018/12/18
     */
    public boolean addTeacher(Teacher teacher) throws MyException {
        if (!teacherMapper.insertTeacher(teacher)) {
            throw new MyException("新增老师错误！数据库处理错误", MyException.ERROR);
        }
        return true;
    }
}
