package com.rbs.project.pojo.relationship;

/**
 * @Author: WinstonDeng
 * @Description: OOAD_Course_ManageSystem
 * @Date: Created in 16:08 2018/12/19
 * @Modified by:
 */
public class CClassStudent {
    /**
     * 班级id
     */
    private Long cClassId;
    /**
     * 学生id
     */
    private Long studentId;
    /**
     * 课程id
     */
    private Long courseId;
    /**
     * 队伍id
     */
    private Long teamId;

    public Long getcClassId() {
        return cClassId;
    }

    public void setcClassId(Long cClassId) {
        this.cClassId = cClassId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }
}
