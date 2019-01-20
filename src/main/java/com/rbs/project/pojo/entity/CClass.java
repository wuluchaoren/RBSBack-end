package com.rbs.project.pojo.entity;

import java.util.List;

/**
 * @Author:17Wang
 * @Date:22:20 2018/12/4
 * @Description: 对应表klass
 */
public class CClass {
    //基本信息

    private long id;
    /**
     * 属于哪个课程
     */
    private long courseId;
    /**
     * 年级
     */
    private Integer grade;
    /**
     * 班级序号
     */
    private Integer serial;
    /**
     * 上课地点
     */
    private String place;
    /**
     * 上课时间
     */
    private String time;

    //关系
    /**
     * 一个课程
     */
    private Course course;
    /**
     * 多个班级讨论课
     */
    private List<CClassSeminar> cClassSeminars;
    /**
     * 多个队伍
     */
    private List<Team> teams;
    /**
     * 多个班级学生
     */
    private List<Student> students;
    /**
     * 多个轮次
     */
    private List<Round> rounds;

    //==================================================getter AND setter==================================================//

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Integer getSerial() {
        return serial;
    }

    public void setSerial(Integer serial) {
        this.serial = serial;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public List<CClassSeminar> getcClassSeminars() {
        return cClassSeminars;
    }

    public void setcClassSeminars(List<CClassSeminar> cClassSeminars) {
        this.cClassSeminars = cClassSeminars;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public List<Round> getRounds() {
        return rounds;
    }

    public void setRounds(List<Round> rounds) {
        this.rounds = rounds;
    }

    //================ toString ============================

    @Override
    public String toString() {
        return "CClass{" +
                "id=" + id +
                ", courseId=" + courseId +
                ", grade=" + grade +
                ", serial=" + serial +
                ", place='" + place + '\'' +
                ", time='" + time + '\'' +
                ", course=" + course +
                ", cClassSeminars=" + cClassSeminars +
                ", teams=" + teams +
                ", students=" + students +
                ", rounds=" + rounds +
                '}';
    }
}
