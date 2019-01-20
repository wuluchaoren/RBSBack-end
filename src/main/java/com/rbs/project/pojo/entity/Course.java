package com.rbs.project.pojo.entity;

import com.rbs.project.pojo.strategy.CourseMemberLimitStrategy;
import com.rbs.project.pojo.strategy.MemberLimitStrategy;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

/**
 * @Author:17Wang
 * @Date:22:20 2018/12/4
 * @Description:
 */
public class Course {
    /**
     * 基本信息
     */
    private long id;
    /**
     * 属于哪个老师
     */
    private long teacherId;
    /**
     * 课程名
     */
    private String name;
    /**
     * 课程介绍
     */
    private String intro;
    /**
     * 展示分数百分比（这里放0~100整数，前端处理为百分数，下同）
     */
    private Integer presentationPercentage;
    /**
     * 提问分数百分比
     */
    private Integer questionPercentage;
    /**
     * 报告分数百分比
     */
    private Integer reportPercentage;
    /**
     * 组队开始时间
     */
    private Timestamp teamStartTime;
    /**
     * 组队结束时间
     */
    private Timestamp teamEndTime;
    /**
     * 队伍共享主课程
     */
    private long teamMainCourseId;
    /**
     * 讨论课共享主课程
     */
    private long seminarMainCourseId;

    //关系
    /**
     * 课程组队人数限制
     */
    private MemberLimitStrategy memberLimitStrategy;
    /**
     * 课程组队选修人数限制
     */
    private List<CourseMemberLimitStrategy> courseMemberLimitStrategies;
    /**
     * 冲突的课程
     */
    private List<List<Course>> conflictCourses;
    /**
     * 一个老师
     */
    private Teacher teacher;
    /**
     * 一个队伍共享主课程
     */
    private Course teamMainCourse;
    /**
     * 一个讨论课共享主课程
     */
    private Course seminarMainCourse;
    /**
     * 多个讨论课
     */
    private List<Seminar> seminars;
    /**
     * 多个轮次
     */
    private List<Round> rounds;
    /**
     * 多个班级
     */
    private List<CClass> cClasses;
    /**
     * 多个队伍
     */
    private List<Team> teams;
    /**
     * 多个班级学生
     */
    private List<Student> students;


    //==================================================getter AND setter==================================================//

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(long teacherId) {
        this.teacherId = teacherId;
    }

    public void setTeamMainCourseId(long teamMainCourseId) {
        this.teamMainCourseId = teamMainCourseId;
    }

    public void setSeminarMainCourseId(long seminarMainCourseId) {
        this.seminarMainCourseId = seminarMainCourseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public Integer getPresentationPercentage() {
        return presentationPercentage;
    }

    public void setPresentationPercentage(Integer presentationPercentage) {
        this.presentationPercentage = presentationPercentage;
    }

    public Integer getQuestionPercentage() {
        return questionPercentage;
    }

    public void setQuestionPercentage(Integer questionPercentage) {
        this.questionPercentage = questionPercentage;
    }

    public Integer getReportPercentage() {
        return reportPercentage;
    }

    public void setReportPercentage(Integer reportPercentage) {
        this.reportPercentage = reportPercentage;
    }

    public Timestamp getTeamStartTime() {
        return teamStartTime;
    }

    public void setTeamStartTime(Timestamp teamStartTime) {
        this.teamStartTime = teamStartTime;
    }

    public Timestamp getTeamEndTime() {
        return teamEndTime;
    }

    public void setTeamEndTime(Timestamp teamEndTime) {
        this.teamEndTime = teamEndTime;
    }

    public long getTeamMainCourseId() {
        return teamMainCourseId;
    }

    public long getSeminarMainCourseId() {
        return seminarMainCourseId;
    }

    public MemberLimitStrategy getMemberLimitStrategy() {
        return memberLimitStrategy;
    }

    public void setMemberLimitStrategy(MemberLimitStrategy memberLimitStrategy) {
        this.memberLimitStrategy = memberLimitStrategy;
    }

    public List<CourseMemberLimitStrategy> getCourseMemberLimitStrategies() {
        return courseMemberLimitStrategies;
    }

    public void setCourseMemberLimitStrategies(List<CourseMemberLimitStrategy> courseMemberLimitStrategies) {
        this.courseMemberLimitStrategies = courseMemberLimitStrategies;
    }

    public List<List<Course>> getConflictCourses() {
        return conflictCourses;
    }

    public void setConflictCourses(List<List<Course>> conflictCourses) {
        this.conflictCourses = conflictCourses;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Course getTeamMainCourse() {
        return teamMainCourse;
    }

    public void setTeamMainCourse(Course teamMainCourse) {
        this.teamMainCourse = teamMainCourse;
    }

    public Course getSeminarMainCourse() {
        return seminarMainCourse;
    }

    public void setSeminarMainCourse(Course seminarMainCourse) {
        this.seminarMainCourse = seminarMainCourse;
    }

    public List<Seminar> getSeminars() {
        return seminars;
    }

    public void setSeminars(List<Seminar> seminars) {
        this.seminars = seminars;
    }

    public List<Round> getRounds() {
        return rounds;
    }

    public void setRounds(List<Round> rounds) {
        this.rounds = rounds;
    }

    public List<CClass> getcClasses() {
        return cClasses;
    }

    public void setcClasses(List<CClass> cClasses) {
        this.cClasses = cClasses;
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

    //======================= toString=============================

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", teacherId=" + teacherId +
                ", name='" + name + '\'' +
                ", intro='" + intro + '\'' +
                ", presentationPercentage=" + presentationPercentage +
                ", questionPercentage=" + questionPercentage +
                ", reportPercentage=" + reportPercentage +
                ", teamStartTime=" + teamStartTime +
                ", teamEndTime=" + teamEndTime +
                ", teamMainCourseId=" + teamMainCourseId +
                ", seminarMainCourseId=" + seminarMainCourseId +
                ", memberLimitStrategy=" + memberLimitStrategy +
                ", courseMemberLimitStrategies=" + courseMemberLimitStrategies +
                ", conflictCourses=" + conflictCourses +
                ", teacher=" + teacher +
                ", teamMainCourse=" + teamMainCourse +
                ", seminarMainCourse=" + seminarMainCourse +
                ", seminars=" + seminars +
                ", rounds=" + rounds +
                ", cClasses=" + cClasses +
                ", teams=" + teams +
                ", students=" + students +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return ((Course) obj).getId() == this.id ? true : false;
    }
}
