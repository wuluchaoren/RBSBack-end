package com.rbs.project.pojo.entity;

import java.sql.Timestamp;
import java.util.List;

/**
 * @Author:17Wang
 * @Date:22:20 2018/12/4
 * @Description:
 */
public class Seminar {

    private long id;
    /**
     * 属于哪个round
     */
    private long roundId;
    /**
     * 属于哪个course（目前只存主课程）
     */
    private long courseId;
    /**
     * 讨论课主题
     */
    private String name;
    /**
     * 讨论课介绍
     */
    private String intro;
    /**
     * 报名讨论课最多组数
     */
    private Integer maxTeam;
    /**
     * 是否可见
     */
    private Integer visible;
    /**
     * 讨论课序号
     */
    private Integer serial;
    /**
     * 报名开始时间
     */
    private Timestamp enrollStartTime;
    /**
     * 报名结束时间
     */
    private Timestamp enrollEndTime;

    //关系
    /**
     * 一个轮次
     */
    private Round round;
    /**
     * 一个课程(目前只存主课程)
     */
    private Course course;
    /**
     * 多个班级讨论课
     */
    private List<CClassSeminar> cClassSeminars;

    //==================================================getter AND setter==================================================//

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getRoundId() {
        return roundId;
    }

    public void setRoundId(long roundId) {
        this.roundId = roundId;
    }

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
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

    public Integer getMaxTeam() {
        return maxTeam;
    }

    public void setMaxTeam(Integer maxTeam) {
        this.maxTeam = maxTeam;
    }

    public Integer getVisible() {
        return visible;
    }

    public void setVisible(Integer visible) {
        this.visible = visible;
    }

    public Integer getSerial() {
        return serial;
    }

    public void setSerial(Integer serial) {
        this.serial = serial;
    }

    public Timestamp getEnrollStartTime() {
        return enrollStartTime;
    }

    public void setEnrollStartTime(Timestamp enrollStartTime) {
        this.enrollStartTime = enrollStartTime;
    }

    public Timestamp getEnrollEndTime() {
        return enrollEndTime;
    }

    public void setEnrollEndTime(Timestamp enrollEndTime) {
        this.enrollEndTime = enrollEndTime;
    }

    public Round getRound() {
        return round;
    }

    public void setRound(Round round) {
        this.round = round;
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
//================ toString =======================

    @Override
    public String toString() {
        return "Seminar{" +
                "id=" + id +
                ", roundId=" + roundId +
                ", courseId=" + courseId +
                ", name='" + name + '\'' +
                ", intro='" + intro + '\'' +
                ", maxTeam=" + maxTeam +
                ", visible=" + visible +
                ", serial=" + serial +
                ", enrollStartTime=" + enrollStartTime +
                ", enrollEndTime=" + enrollEndTime +
                ", round=" + round +
                ", course=" + course +
                ", cClassSeminars=" + cClassSeminars +
                '}';
    }

//================ 构造方法 除了id courseId roundId全复制============

    public Seminar(){

    }

    public Seminar(Seminar seminar){
        name=seminar.getName();
        intro=seminar.getIntro();
        maxTeam=seminar.getMaxTeam();
        visible=seminar.getVisible();
        serial=seminar.getSerial();
        enrollStartTime=seminar.getEnrollStartTime();
        enrollEndTime=seminar.getEnrollEndTime();
    }

}
