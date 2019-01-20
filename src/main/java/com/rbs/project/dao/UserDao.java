package com.rbs.project.dao;

import com.rbs.project.exception.MyException;
import com.rbs.project.mapper.AdminMapper;
import com.rbs.project.mapper.CourseMapper;
import com.rbs.project.mapper.StudentMapper;
import com.rbs.project.mapper.TeacherMapper;
import com.rbs.project.pojo.entity.Student;
import com.rbs.project.pojo.entity.Teacher;
import com.rbs.project.pojo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 16:26 2018/12/15
 */
@Repository
public class UserDao {
    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private AdminMapper adminMapper;

    /**
     * 是否加入课程关系
     */
    public static final int HAS_COURSES = 0;

    private User hasSomethingFun(User user, int... hasSomething) {
        for (int i : hasSomething) {
            if (i == HAS_COURSES) {
                if (user instanceof Student) {
                    user.setCourses(courseMapper.findByStudentId(user.getId()));
                }
                if (user instanceof Teacher) {
                    user.setCourses(courseMapper.findByTeacherId(user.getId()));
                }
            }
        }

        return user;
    }

    /**
     * Description: 通过account返回用户信息，先后判断学生和老师
     *
     * @Author: 17Wang
     * @Time: 16:34 2018/12/15
     */
    public User getUserByUsername(String username, int... hasSomething) {
        User user = studentMapper.findByAccount(username);
        if (user == null) {
            user = teacherMapper.findByAccount(username);
        }
        if (user == null) {
            user = adminMapper.findByAccount(username);
        }
        hasSomethingFun(user, hasSomething);
        return user;
    }


}
