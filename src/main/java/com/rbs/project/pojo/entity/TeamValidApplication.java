package com.rbs.project.pojo.entity;


/**
 * @Author: WinstonDeng
 * @Description: OOAD_Course_ManageSystem
 * @Date: Created in 14:37 2018/12/15
 * @Modified by:
 */
public class TeamValidApplication {
    //基本信息

    private long id;
    /**
     * 队伍id
     */
    private long teamId;
    /**
     * 教师id
     */
    private long teacherId;
    /**
     * 申请原因
     */
    private String reason;
    /**
     * 请求状态，同意1、不同意0、未处理null
     */
    private Integer status;
    /**
     * 同意
     */
    public static final Integer STATUS_AGREE = 1;
    /**
     * 不同意
     */
    public static final Integer STATUS_DISAGREE = 0;
    /**
     * 未处理
     */
    public static final Integer STATUS_UNDO = null;

    //关系
    /**
     * 一个队伍
     */
    private Team team;
    /**
     * 一个接收请求的老师
     */
    private Teacher teacher;

    //==================================================getter AND setter==================================================//


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTeamId() {
        return teamId;
    }

    public void setTeamId(long teamId) {
        this.teamId = teamId;
    }

    public long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(long teacherId) {
        this.teacherId = teacherId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
//======================== toString =====================

}
