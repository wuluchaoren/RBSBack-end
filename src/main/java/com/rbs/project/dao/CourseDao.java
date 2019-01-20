package com.rbs.project.dao;

import com.rbs.project.exception.MyException;
import com.rbs.project.mapper.*;
import com.rbs.project.mapper.strategy.*;
import com.rbs.project.pojo.entity.Course;
import com.rbs.project.pojo.strategy.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 19:44 2018/12/18
 */
@Repository
public class CourseDao {
    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private CClassMapper cClassMapper;

    @Autowired
    private SeminarMapper seminarMapper;

    @Autowired
    private ShareSeminarApplicationMapper shareSeminarApplicationMapper;

    @Autowired
    private ShareTeamApplicationMapper shareTeamApplicationMapper;

    @Autowired
    private TeamStrategyMapper teamStrategyMapper;

    @Autowired
    private TeamAndStrategyMapper teamAndStrategyMapper;

    @Autowired
    private TeamOrStrategyMapper teamOrStrategyMapper;

    @Autowired
    private MemberLimitStrategyMapper memberLimitStrategyMapper;

    @Autowired
    private CourseMemberLimitStrategyMapper courseMemberLimitStrategyMapper;

    @Autowired
    private ConflictCourseStrategyMapper conflictCourseStrategyMapper;

    @Autowired
    private TeacherMapper teacherMapper;

    /**
     * 组队人数限制策略
     */
    public static final int HAS_STRATEGY = 0;
    public static final int HAS_CCLASS = 2;
    public static final int HAS_SEMINAR = 3;
    public static final int HAS_TEACHER = 4;

    private void hasSomethingFun(Course course, int... hasSomething) {
        for (int i : hasSomething) {
            if (i == HAS_STRATEGY) {
                List<TeamStrategy> teamStrategies = teamStrategyMapper.findByCourseId(course.getId());
                for (TeamStrategy teamStrategy : teamStrategies) {
                    hasStrategy(teamStrategy.getStrategyName(), teamStrategy.getStrategyId(), course);
                }
            }
            if (i == HAS_CCLASS) {
                course.setcClasses(cClassMapper.findByCourseId(course.getId()));
            }
            if (i == HAS_SEMINAR) {
                course.setSeminars(seminarMapper.findByCourseId(course.getId()));
            }
            if (i == HAS_TEACHER) {
                course.setTeacher(teacherMapper.findById(course.getTeacherId()));
            }
        }
    }

    private void hasStrategy(String strategyName, long strategyId, Course course) {
        switch (strategyName) {
            case "TeamAndStrategy":
                myTeamAndStrategy(strategyId, course);
                break;
            case "TeamOrStrategy":
                myTeamOrStrategy(strategyId, course);
                break;
            case "ConflictCourseStrategy":
                myConflictCourseStrategy(strategyId, course);
                break;
            case "MemberLimitStrategy":
                myMemberLimitStrategy(strategyId, course);
                break;
            case "CourseMemberLimitStrategy":
                myCourseMemberLimitStrategy(strategyId, course);
                break;
            default:
                ;
        }
    }

    private void myTeamAndStrategy(long strategyId, Course course) {
        List<TeamAndStrategy> teamAndStrategies = teamAndStrategyMapper.findById(strategyId);
        for (TeamAndStrategy teamAndStrategy : teamAndStrategies) {
            hasStrategy(teamAndStrategy.getStrategyName(), teamAndStrategy.getStrategyId(), course);
        }
    }

    private void myTeamOrStrategy(long strategyId, Course course) {
        List<TeamOrStrategy> teamOrStrategies = teamOrStrategyMapper.findById(strategyId);
        for (TeamOrStrategy teamOrStrategy : teamOrStrategies) {
            hasStrategy(teamOrStrategy.getStrategyName(), teamOrStrategy.getStrategyId(), course);
        }
    }

    private void myConflictCourseStrategy(long strategyId, Course course) {
        List<ConflictCourseStrategy> conflictCourseStrategies = conflictCourseStrategyMapper.findById(strategyId);
        List<Course> courses = new ArrayList<>();
        for (ConflictCourseStrategy conflictCourseStrategy : conflictCourseStrategies) {
            try {
                courses.add(getCourseById(conflictCourseStrategy.getCourseId(), HAS_TEACHER));
            } catch (MyException e) {
                e.printStackTrace();
            }
        }
        if (course.getConflictCourses() == null) {
            course.setConflictCourses(new ArrayList<>());
        }
        course.getConflictCourses().add(courses);
    }

    private void myMemberLimitStrategy(long strategyId, Course course) {
        MemberLimitStrategy memberLimitStrategy = memberLimitStrategyMapper.findById(strategyId);
        course.setMemberLimitStrategy(memberLimitStrategy);
    }

    private void myCourseMemberLimitStrategy(long strategyId, Course course) {
        CourseMemberLimitStrategy courseMemberLimitStrategy = courseMemberLimitStrategyMapper.findById(strategyId);
        if (course.getCourseMemberLimitStrategies() == null) {
            course.setCourseMemberLimitStrategies(new ArrayList<>());
        }
        course.getCourseMemberLimitStrategies().add(courseMemberLimitStrategy);
    }

    /**
     * Description:
     *
     * @Author: 17Wang
     * @Time: 3:21 2018/12/29
     */
    public boolean judgeCourseMemberLimitIsAndStyle(long courseId) {
        for (TeamStrategy teamStrategy : teamStrategyMapper.findByCourseId(courseId)) {
            if (teamStrategy.getStrategyName() == "CourseMemberLimitStrategy") {
                return true;
            }
        }
        return false;
    }

    /**
     * Description: 通过id锁定课程
     *
     * @Author: 17Wang
     * @Time: 19:55 2018/12/18
     */
    public Course getCourseById(long courseId, int... hasSomething) throws MyException {
        Course course = courseMapper.findById(courseId);
        if (course == null) {
            throw new MyException("查找课程失败！不存在该课程", MyException.NOT_FOUND_ERROR);
        }
        hasSomethingFun(course, hasSomething);
        return course;
    }

    public List<Course> listAllCourses(int... hasSomething) {
        List<Course> courses = courseMapper.listAllCourse();
        for (Course course : courses) {
            hasSomethingFun(course, hasSomething);
        }
        return courses;
    }

    /**
     * Description: 创建课程＋创建课程策略
     * 1、添加回滚
     *
     * @Author: 17Wang
     * @Time: 19:50 2018/12/18
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean addCourse(Course course, Integer flag) throws Exception {
        //添加课程
        if (!courseMapper.insertCourse(course)) {
            throw new MyException("创建课程失败！数据库处理错误", MyException.ERROR);
        }
        //TODO 添加策略
        long teamStrategySerial = 1;
        //冲突表策略
        long conflictCourseStrategyId = conflictCourseStrategyMapper.findMaxId();

        for (List<Course> courses : course.getConflictCourses()) {
            //表下一个 、策略组id
            conflictCourseStrategyId++;
            //将同一组策略写入 冲突策略表中
            for (Course temp : courses) {
                conflictCourseStrategyMapper.insertOneLine(conflictCourseStrategyId, temp.getId());
            }
            //将冲突策略写入最上层策略表中
            TeamStrategy teamStrategy = new TeamStrategy();
            teamStrategy.setCourseId(course.getId());
            teamStrategy.setStrategySerial(teamStrategySerial++);
            teamStrategy.setStrategyName("ConflictCourseStrategy");
            teamStrategy.setStrategyId(conflictCourseStrategyId);
            teamStrategyMapper.insertStrategy(teamStrategy);
        }

        //人数限制策略
        MemberLimitStrategy memberLimitStrategy = course.getMemberLimitStrategy();
        if (memberLimitStrategy != null) {
            memberLimitStrategyMapper.insertStrategy(memberLimitStrategy);
            //将人数限制策略写入最上层策略表中
            TeamStrategy t = new TeamStrategy();
            t.setCourseId(course.getId());
            t.setStrategySerial(teamStrategySerial++);
            t.setStrategyName("MemberLimitStrategy");
            t.setStrategyId(memberLimitStrategy.getId());
            teamStrategyMapper.insertStrategy(t);
        }


        //选修课程人数限制策略（两种  与 或）
        //与
        if (flag == 1) {
            for (CourseMemberLimitStrategy courseMemberLimitStrategy : course.getCourseMemberLimitStrategies()) {
                courseMemberLimitStrategyMapper.insertStrategy(courseMemberLimitStrategy);
                //写入最上层策略表中
                TeamStrategy teamStrategy = new TeamStrategy();
                teamStrategy.setCourseId(course.getId());
                teamStrategy.setStrategySerial(teamStrategySerial++);
                teamStrategy.setStrategyName("CourseMemberLimitStrategy");
                teamStrategy.setStrategyId(courseMemberLimitStrategy.getId());
                teamStrategyMapper.insertStrategy(teamStrategy);
            }
        } else if (flag == 0) {
            long maxId = teamOrStrategyMapper.findMaxId() + 1;
            for (CourseMemberLimitStrategy courseMemberLimitStrategy : course.getCourseMemberLimitStrategies()) {
                courseMemberLimitStrategyMapper.insertStrategy(courseMemberLimitStrategy);
                //写入或表
                TeamOrStrategy teamOrStrategy = new TeamOrStrategy();
                teamOrStrategy.setId(maxId);
                teamOrStrategy.setStrategyName("CourseMemberLimitStrategy");
                teamOrStrategy.setStrategyId(courseMemberLimitStrategy.getId());
                teamOrStrategyMapper.insertStrategy(teamOrStrategy);
            }
            //把或表写入最上层策略表中
            TeamStrategy teamStrategy = new TeamStrategy();
            teamStrategy.setCourseId(course.getId());
            teamStrategy.setStrategySerial(teamStrategySerial++);
            teamStrategy.setStrategyName("TeamOrStrategy");
            teamStrategy.setStrategyId(maxId);
            teamStrategyMapper.insertStrategy(teamStrategy);
        }


        return true;
    }

    /**
     * Description: 删除课程
     * 1、添加回滚操作
     *
     * @Author: 17Wang
     * @Time: 10:37 2018/12/19
     */
    @Transactional(rollbackFor = MyException.class)
    public boolean deleteCourseById(long courseId) throws Exception {
        //查询是否存在
        getCourseById(courseId);

        //删除冲突课程策略
        //删除冲突课程策略 已测试
        try {
            conflictCourseStrategyMapper.deleteByCourseId(courseId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException("删除冲突课程策略失败", MyException.ERROR);
        }

        //删除策略表
        if (!courseMemberLimitStrategyMapper.deleteByCourseId(courseId)) {
            throw new MyException("删除课程组队策略失败！数据库处理错误", MyException.ERROR);
        }

        //

        //删除课程
        courseMapper.deleteById(courseId);

        //TODO 删除share_seminar_application 待测试


        shareSeminarApplicationMapper.deleteByCourseId(courseId);

        //TODO 删除share_team_application 待测试
        shareTeamApplicationMapper.deleteByCourseId(courseId);


        return true;
    }

    /**
     * Description: 更新从课程team_main_course_id字段
     *
     * @Author: WinstonDeng
     * @Date: 10:21 2018/12/27
     */
    public boolean updateTeamMainCourseId(long subCourseId, long mainCourseId) throws MyException {
        Course course = courseMapper.findById(subCourseId);
        if (course == null) {
            throw new MyException("更新从课程team_main_course_id错误！未找到该课程", MyException.NOT_FOUND_ERROR);
        }
        course.setTeamMainCourseId(mainCourseId);
        if (!courseMapper.updateTeamMainCourseId(course)) {
            throw new MyException("更新从课程team_main_course_id错误！数据库处理错误", MyException.ERROR);
        }
        return true;
    }

    /**
     * Description: 通过id修改讨论课共享主课程字段
     *
     * @Author: WinstonDeng
     * @Date: 21:15 2018/12/27
     */
    public boolean updateSeminarMainCourseId(long subCourseId, long mainCourseId) throws MyException {
        Course course = courseMapper.findById(subCourseId);
        if (course == null) {
            throw new MyException("更新从课程seminar_main_course_id错误！未找到该课程", MyException.NOT_FOUND_ERROR);
        }
        course.setSeminarMainCourseId(mainCourseId);
        if (!courseMapper.updateSeminarMainCourseId(course)) {
            throw new MyException("更新从课程seminar_main_course_id错误！数据库处理错误", MyException.ERROR);
        }
        return true;
    }

    /**
     * Description: 通过老师id查看当前已有课程
     *
     * @Author: WinstonDeng
     * @Date: 15:41 2018/12/28
     */
    public List<Course> listAllCoursesByTeacherId(long teacherId, int... hasSomething) {
        List<Course> courses = courseMapper.findByTeacherId(teacherId);
        for (Course course
                : courses) {
            hasSomethingFun(course, hasSomething);
        }
        return courses;
    }

    /**
     * Description: 找到讨论课共享主课程为courseId的从课程列表
     * @Author: WinstonDeng
     * @Date: 0:49 2018/12/29
     */
    public List<Course> listAllCoursesBySeminarMainCourseId(long courseId) {
        return courseMapper.findBySeminarMainCourseId(courseId);
    }

    /**
     * Description: 找到队伍共享主课程为courseId的从课程列表
     * @Author: WinstonDeng
     * @Date: 2:14 2018/12/29
     */
    public List<Course> listAllCoursesByTeamMainCourseId(long courseId) {
        return courseMapper.findByTeamMainCourseId(courseId);
    }
}
