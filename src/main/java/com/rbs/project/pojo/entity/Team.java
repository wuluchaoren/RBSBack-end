package com.rbs.project.pojo.entity;

import java.util.List;

/**
 * @Author:17Wang
 * @Date:22:20 2018/12/4
 * @Description:
 */
public class Team {
    //基本信息

    private long id;
    /**
     * 属于哪个班
     */
    private long cClassId;
    /**
     * 属于哪个课程(目前只存主课程)
     */
    private long courseId;
    /**
     * 组长id
     */
    private long leaderId;
    /**
     * 组号
     */
    private Integer serial;
    /**
     * 组名
     */
    private String name;
    /**
     * 小组状态 不合法0、合法1、审核中2
     */
    private Integer status;

    public static final int STATUS_ERROR = 0;
    public static final int STATUS_OK = 1;
    public static final int STATUS_IN_REVIEW = 2;

    //关系
    /**
     * 一个班级
     */
    private CClass cClass;
    /**
     * 一个课程(目前只存主课程)
     */
    private Course course;
    /**
     * 一个组长
     */
    private Student leader;
    /**
     * 多个提问
     */
    private List<Question> questions;
    /**
     * 多个展示
     */
    private List<Attendance> attendances;
    /**
     * 多个班级下的小组成员（包括组长）
     */
    private List<Student> students;
    /**
     * 多个讨论课成绩
     */
    private List<SeminarScore> seminarScores;
    /**
     * 多个组队申请
     */
    private List<TeamValidApplication> teamValidApplications;

    //==================================================getter AND setter==================================================//

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getcClassId() {
        return cClassId;
    }

    public void setcClassId(long cClassId) {
        this.cClassId = cClassId;
    }

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    public long getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(long leaderId) {
        this.leaderId = leaderId;
    }

    public Integer getSerial() {
        return serial;
    }

    public void setSerial(Integer serial) {
        this.serial = serial;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public CClass getcClass() {
        return cClass;
    }

    public void setcClass(CClass cClass) {
        this.cClass = cClass;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Student getLeader() {
        return leader;
    }

    public void setLeader(Student leader) {
        this.leader = leader;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public List<Attendance> getAttendances() {
        return attendances;
    }

    public void setAttendances(List<Attendance> attendances) {
        this.attendances = attendances;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public List<SeminarScore> getSeminarScores() {
        return seminarScores;
    }

    public void setSeminarScores(List<SeminarScore> seminarScores) {
        this.seminarScores = seminarScores;
    }

    public List<TeamValidApplication> getTeamValidApplications() {
        return teamValidApplications;
    }

    public void setTeamValidApplications(List<TeamValidApplication> teamValidApplications) {
        this.teamValidApplications = teamValidApplications;
    }

    //================= toString =================

    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", cClassId=" + cClassId +
                ", courseId=" + courseId +
                ", leaderId=" + leaderId +
                ", serial=" + serial +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", cClass=" + cClass +
                ", course=" + course +
                ", leader=" + leader +
                ", questions=" + questions +
                ", attendances=" + attendances +
                ", students=" + students +
                ", seminarScores=" + seminarScores +
                ", teamValidApplications=" + teamValidApplications +
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
        return ((Team) obj).getId() == this.id ? true : false;
    }
}
