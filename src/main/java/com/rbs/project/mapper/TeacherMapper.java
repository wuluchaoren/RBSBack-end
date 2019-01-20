package com.rbs.project.mapper;

import com.rbs.project.pojo.entity.Teacher;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 16:37 2018/12/15
 */
@Repository
public interface TeacherMapper {
    /**
     * 通过id锁定一个老师
     *
     * @param id
     * @return
     */
    Teacher findById(long id);

    /**
     * 通过用户名锁定一个老师
     *
     * @param account
     * @return
     */
    Teacher findByAccount(String account);

    /**
     * 通过老师名查找一个老师
     *
     * @param teacherName
     * @return
     */
    Teacher findByTeacherName(String teacherName);

    /**
     * 返回所有教师信息
     *
     * @return 教师名单
     */
    List<Teacher> listAll();

    /**
     * 通过id，同时修改密码和邮箱
     *
     * @param teacher
     * @return
     */
    boolean updatePasswordAndEmailAndActiveById(Teacher teacher);

    /**
     * 通过id修改密码
     *
     * @param teacher
     * @return
     */
    boolean updatePasswordById(Teacher teacher);

    /**
     * 通过id修改邮箱
     *
     * @param teacher
     * @return
     */
    boolean updateEmailById(Teacher teacher);

    /**
     * 通过id修改老师名
     *
     * @param teacher
     * @return
     */
    boolean updateTeacherNameById(Teacher teacher);

    /**
     * 新增老师
     *
     * @param teacher
     * @return
     */
    boolean insertTeacher(Teacher teacher);


    /**
     * 通过id修改学号
     *
     * @param teacher
     * @return
     */
    boolean updateAccountById(Teacher teacher);

    /**
     * 删除老师
     *
     * @param id
     * @return
     */
    boolean deleteTeacherById(long id);
}
