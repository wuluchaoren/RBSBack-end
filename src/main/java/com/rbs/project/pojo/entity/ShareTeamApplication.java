package com.rbs.project.pojo.entity;

/**
 * @Author: WinstonDeng
 * @Description: OOAD_Course_ManageSystem
 * @Date: Created in 15:16 2018/12/15
 * @Modified by:
 */
public class ShareTeamApplication {
    private long id;
    /**
     * 主课程id
     */
    private long mainCourseId;
    /**
     * 从课程id
     */
    private long subCourseId;
    /**
     * 从课程老师id
     */
    private long subCourseTeacherId;
    /**
     * 请求状态，同意1、不同意0、未处理null
     */
    private Integer status;

    //关系
    /**
     * 一个主课程
     */
    private Course mainCourse;
    /**
     * 主课程老师
     */
    private Teacher mainCourseTeacher;
    /**
     * 一个从课程
     */
    private Course subCourse;
    /**
     * 一个从课程老师
     */
    private Teacher subCourseTeacher;

    public final static Integer STATUS_UNHANDLE=null;
    public final static int STATUS_ACCEPT = 1;
    public final static int STATUS_REJECT=0;

    //==================================================getter AND setter==================================================//

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getMainCourseId() {
        return mainCourseId;
    }

    public void setMainCourseId(long mainCourseId) {
        this.mainCourseId = mainCourseId;
    }

    public long getSubCourseId() {
        return subCourseId;
    }

    public void setSubCourseId(long subCourseId) {
        this.subCourseId = subCourseId;
    }

    public long getSubCourseTeacherId() {
        return subCourseTeacherId;
    }

    public void setSubCourseTeacherId(long subCourseTeacherId) {
        this.subCourseTeacherId = subCourseTeacherId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Course getMainCourse() {
        return mainCourse;
    }

    public void setMainCourse(Course mainCourse) {
        this.mainCourse = mainCourse;
    }

    public Course getSubCourse() {
        return subCourse;
    }

    public void setSubCourse(Course subCourse) {
        this.subCourse = subCourse;
    }

    public Teacher getSubCourseTeacher() {
        return subCourseTeacher;
    }

    public void setSubCourseTeacher(Teacher subCourseTeacher) {
        this.subCourseTeacher = subCourseTeacher;
    }

    public Teacher getMainCourseTeacher() {
        return mainCourseTeacher;
    }

    public void setMainCourseTeacher(Teacher mainCourseTeacher) {
        this.mainCourseTeacher = mainCourseTeacher;
    }
//===================== toString =========================

}
