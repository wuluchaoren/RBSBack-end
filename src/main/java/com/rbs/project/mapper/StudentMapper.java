package com.rbs.project.mapper;

import com.rbs.project.pojo.entity.Student;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 15:28 2018/12/15
 */
@Repository
public interface StudentMapper {
    /**
     * 通过id锁定一个学生
     *
     * @param id
     * @return
     */
    Student findById(long id);

    /**
     * 通过在学号锁定一个学生
     *
     * @param account
     * @return
     */
    Student findByAccount(String account);

    /**
     * 通过学生名查找一个学生
     *
     * @param studentName
     * @return
     */
    Student findByStudentName(String studentName);

    /**
     * 返回所有学生信息
     *
     * @return 学生名单
     */
    List<Student> listAll();

    /**
     * 获取team下面的所有学生
     *
     * @param teamId
     * @return
     */
    List<Student> findByTeamId(long teamId);

    /**
     * 获取一个课程下没有组队的所有学生的ID
     * @param courseId
     * @return
     */
    List<Student> findByCourseIdHasTeam(@Param("courseId") long courseId);

    /**
     * 获取一个课程下的所有学生
     * @param courseId
     * @return
     */
    List<Student> findByCourseId(long courseId);

    /**
     * 获取一个队伍里，属于courseId下的学生
     * @param courseId
     * @param teamId
     * @return
     */
    List<Student> findByCourseIdAndTeamId(@Param("courseId") long courseId,@Param("teamId") long teamId);

    /**
     * 通过id，同时修改密码和邮箱
     *
     * @param student
     * @return
     */
    boolean updatePasswordAndEmailAndActiveById(Student student);

    /**
     * 通过id修改密码
     *
     * @param student
     * @return
     */
    boolean updatePasswordById(Student student);

    /**
     * 通过id修改邮箱
     *
     * @param student
     * @return
     */
    boolean updateEmailById(Student student);

    /**
     * 通过id修改学生名
     *
     * @param student
     * @return
     */
    boolean updateStudentNameById(Student student);

    /**
     * 新增学生
     *
     * @param student
     * @return
     */
    long insertStudent(Student student);


    /**
     * 通过id修改学号
     *
     * @param student
     * @return
     */
    boolean updateAccountById(Student student);

    /**
     * 删除学生
     *
     * @param id
     * @return
     */
    boolean deleteStudentById(long id);
}
