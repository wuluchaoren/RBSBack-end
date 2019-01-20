package com.rbs.project.mapper;

import com.rbs.project.pojo.entity.Course;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: WinstonDeng
 * @Description: OOAD_Course_ManageSystem
 * @Date: Created in 14:09 2018/12/16
 * @Modified by:
 */
@Mapper
@Repository
public interface CourseMapper {
    //=========================查找=======================

    /**
     * 通过id找课程
     *
     * @param id
     * @return
     */
    Course findById(long id);

    /**
     * 通过老师查找课程列表
     *
     * @param teacherId
     * @return
     */
    List<Course> findByTeacherId(long teacherId);

    /**
     * 通过学生id查找班级，再通过班级查找课程
     *
     * @param studentId
     * @return
     */
    List<Course> findByStudentId(long studentId);

    /**
     * 通过班级获取课程
     *
     * @param cClassId
     * @return
     */
    Course findByCClassId(long cClassId);

    /**
     * 获取所有的课程
     *
     * @return
     */
    List<Course> listAllCourse();

    /**
     * 获取与当前课程冲突的所有课程
     *
     * @param nowCourseId
     * @return
     */
    List<Course> findAllConflictCourseByNowCourseId(long nowCourseId);
    //=========================新增=======================

    /**
     * 新增课程
     * @param course
     * @return
     * @throws Exception
     */
    boolean insertCourse(Course course) throws Exception;
    //=========================删除=======================

    /**
     * 通过id删除课程
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteById(long id) throws Exception;


    //=========================修改=======================

    /**
     * 修改 team_main_course_id
     *
     * @param course
     * @return
     */
    boolean updateTeamMainCourseId(Course course);

    /**
     * 修改 seminar_main_course_id
     *
     * @param course
     * @return
     */
    boolean updateSeminarMainCourseId(Course course);

    /**
     * 找到讨论课主课程为seminarMainCourseId的从课程列表
     * @param seminarMainCourseId
     * @return
     */
    List<Course> findBySeminarMainCourseId(long seminarMainCourseId);

    /**
     * 找到队伍主课程为teamMainCourseId的从课程列表
     * @param teamMainCourseId
     * @return
     */
    List<Course> findByTeamMainCourseId(long teamMainCourseId);
}
