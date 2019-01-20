package com.rbs.project.pojo.entity;

import java.math.BigDecimal;


/**
 * @Author:17Wang
 * @Date:22:20 2018/12/4
 * @Description:
 */
public class Question {
    //基本信息

    private long id;
    /**
     * 班级讨论课id
     */
    private long cClassSeminarId;
    /**
     * 问题所针对的发言id
     */
    private long attendanceId;
    /**
     * 属于哪个队伍
     */
    private long teamId;
    /**
     * 学生id
     */
    private long studentId;
    /**
     * 提问分数
     */
    private double score;
    /**
     * 是否被提问0没被提问，1被提问
     */
    private Integer selected;

    public static final int IS_SELECTED = 1;
    public static final int IS_NO_SELECTED = 0;

    //关系
    /**
     * 一个班级讨论课
     */
    private CClassSeminar cClassSeminar;
    /**
     * 一个被提问的展示
     */
    private Attendance attendance;
    /**
     * 一个发起提问的队伍
     */
    private Team team;
    /**
     * 一个发起提问的学生
     */
    private Student student;

    //==================================================getter AND setter==================================================//

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getcClassSeminarId() {
        return cClassSeminarId;
    }

    public void setcClassSeminarId(long cClassSeminarId) {
        this.cClassSeminarId = cClassSeminarId;
    }

    public long getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(long attendanceId) {
        this.attendanceId = attendanceId;
    }

    public long getTeamId() {
        return teamId;
    }

    public void setTeamId(long teamId) {
        this.teamId = teamId;
    }

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public Integer getSelected() {
        return selected;
    }

    public void setSelected(Integer selected) {
        this.selected = selected;
    }

    public CClassSeminar getcClassSeminar() {
        return cClassSeminar;
    }

    public void setcClassSeminar(CClassSeminar cClassSeminar) {
        this.cClassSeminar = cClassSeminar;
    }

    public Attendance getAttendance() {
        return attendance;
    }

    public void setAttendance(Attendance attendance) {
        this.attendance = attendance;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
//====================== toString =========================

}
