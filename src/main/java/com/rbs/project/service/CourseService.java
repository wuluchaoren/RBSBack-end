package com.rbs.project.service;

import com.rbs.project.dao.*;
import com.rbs.project.exception.MyException;
import com.rbs.project.pojo.entity.*;
import com.rbs.project.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 16:13 2018/12/18
 */
@Service
public class CourseService {
    @Autowired
    private CourseDao courseDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private CClassDao cClassDao;

    /**
     * Description: 创建课程同时创建课程策略内容
     *
     * @Author: 17Wang
     * @Time: 19:45 2018/12/18
     */
    public boolean createCourse(Course course, Integer flag) throws Exception {
        //设置当前教师
        Teacher teacher = (Teacher) UserUtils.getNowUser();
        course.setTeacherId(teacher.getId());
        //增加六个策略表


        return courseDao.addCourse(course, flag);
    }

    /**
     * Description: 通过id获取当前课程
     *
     * @Author: 17Wang
     * @Time: 22:58 2018/12/18
     */
    public Course getCourseById(long courseId, int... hasSomething) throws MyException {
        if (hasSomething.length == 0) {
            return courseDao.getCourseById(courseId,
                    CourseDao.HAS_STRATEGY,
                    CourseDao.HAS_SEMINAR,
                    CourseDao.HAS_CCLASS,
                    CourseDao.HAS_TEACHER);
        }
        return courseDao.getCourseById(courseId, hasSomething);
    }

    /**
     * Description: 判断选修课程策略是否或 还是 与
     * @Author: 17Wang
     * @Time: 3:20 2018/12/29
    */
    public boolean judgeCourseMemberLimitIsAndStyle(long courseId){
        return courseDao.judgeCourseMemberLimitIsAndStyle(courseId);
    }

    /**
     * Description: 获取所有课程
     *
     * @Author: 17Wang
     * @Time: 17:41 2018/12/23
     */
    public List<Course> listAllCourses() {
        return courseDao.listAllCourses(CourseDao.HAS_TEACHER);
    }

    /**
     * Description: 获取当前用户的所有课程
     *
     * @Author: 17Wang
     * @Time: 22:17 2018/12/18
     */
    public List<Course> listMyCourses() throws MyException {
        //获取当前登录用户的courses
        User user = userDao.getUserByUsername(UserUtils.getNowUser().getUsername(), UserDao.HAS_COURSES);
        if (user instanceof Student) {
            for (Course course : user.getCourses()) {
                List<CClass> classes = new ArrayList<>();
                classes.add(cClassDao.getCClassByStudentIdAndCourseId(user.getId(), course.getId()));
                course.setcClasses(classes);
            }
        } else if (user instanceof Teacher) {
            List<Course> courses = courseDao.listAllCoursesByTeacherId(user.getId(), CourseDao.HAS_TEACHER);
            user.setCourses(courses);
        }
        return user.getCourses();
    }

    /**
     * Description: 删除课程，只要删掉courseId其他东西别人就拿不到了
     * 1、
     *
     * @Author: 17Wang
     * @Time: 10:34 2018/12/19
     */
    public boolean deleteCourseById(long courseId) throws Exception {
        /*
            1、删除课程
            2、删除课程冲突策略
            3、删除课程人数限制策略
            TODO 删除share_seminar_application 待测试
            TODO 删除share_team_application 待测试
         */
        return courseDao.deleteCourseById(courseId);
        /*
            TOD 删除班级klass和klass底下的级联删除
            TOD 删除klass_round
            TOD 删除klass_student
            TOD 删除klass_team
            TOD 删除
         */
        //cClassDao.deleteCClassByCourseId(courseId);
        /*
            TOD 删除Team
         */
        //teamDao.deleteTeamByCourseId(courseId);
        /*
            TOD 删除讨论课seminar
            TOD 删除klass_seminar
         */
        //seminarDao.deleteSeminarByCourseId(courseId);
        /*
            TOD 删除轮次round
            TOD 删除round_score
         */
        //roundDao.deleteRoundByCourseId(courseId);
    }
}
