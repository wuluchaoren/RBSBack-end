package com.rbs.project.pojo.entity;

import com.rbs.project.pojo.relationship.CClassRound;

import java.util.List;

/**
 * @Author:17Wang
 * @Date:22:20 2018/12/4
 * @Description: 一个课程和一个班级共同决定一个轮次
 */
public class Round {

    private long id;
    /**
     * 所属课程id
     */
    private long courseId;
    /**
     * 第几轮次
     */
    private Integer serial;
    /**
     * 展示成绩计算办法
     */
    private Integer presentationScoreMethod;
    /**
     * 报告成绩计算办法
     */
    private Integer reportScoreMethod;
    /**
     * 提问成绩计算办法
     */
    private Integer questionScoreMethod;
    /**
     * 班级
     */
    private long cClassId;
    /**
     * 某班级，某轮次队伍报名次数限制
     */
    private Integer enrollNumber;
    //关系
    /**
     * 一个课程
     */
    private Course course;
    /**
     * 一个班级
     */
    private CClass cClass;
    /**
     * 多个讨论课
     */
    private List<Seminar> seminars;
    /**
     * 多个小组的轮次成绩
     */
    private List<RoundScore> roundScores;
    /**
     * 多个班级轮次
     */
    private List<CClassRound> cClassRounds;

    public final static int SCORE_AVERAGE=0;
    public final static int SCORE_MAX=1;

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

    public Integer getSerial() {
        return serial;
    }

    public void setSerial(Integer serial) {
        this.serial = serial;
    }

    public Integer getPresentationScoreMethod() {
        return presentationScoreMethod;
    }

    public void setPresentationScoreMethod(Integer presentationScoreMethod) {
        this.presentationScoreMethod = presentationScoreMethod;
    }

    public Integer getReportScoreMethod() {
        return reportScoreMethod;
    }

    public void setReportScoreMethod(Integer reportScoreMethod) {
        this.reportScoreMethod = reportScoreMethod;
    }

    public Integer getQuestionScoreMethod() {
        return questionScoreMethod;
    }

    public void setQuestionScoreMethod(Integer questionScoreMethod) {
        this.questionScoreMethod = questionScoreMethod;
    }

    public long getcClassId() {
        return cClassId;
    }

    public void setcClassId(long cClassId) {
        this.cClassId = cClassId;
    }

    public Integer getEnrollNumber() {
        return enrollNumber;
    }

    public void setEnrollNumber(Integer enrollNumber) {
        this.enrollNumber = enrollNumber;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public CClass getcClass() {
        return cClass;
    }

    public void setcClass(CClass cClass) {
        this.cClass = cClass;
    }

    public List<Seminar> getSeminars() {
        return seminars;
    }

    public void setSeminars(List<Seminar> seminars) {
        this.seminars = seminars;
    }

    public List<RoundScore> getRoundScores() {
        return roundScores;
    }

    public void setRoundScores(List<RoundScore> roundScores) {
        this.roundScores = roundScores;
    }

    public List<CClassRound> getcClassRounds() {
        return cClassRounds;
    }

    public void setcClassRounds(List<CClassRound> cClassRounds) {
        this.cClassRounds = cClassRounds;
    }

//============= toString =========================================

//============= 构造方法 除了id courseId全部复制============================

    public Round(){
        
    }
    public Round(Round round){
        serial=round.getSerial();
        presentationScoreMethod=round.getPresentationScoreMethod();
        reportScoreMethod=round.getReportScoreMethod();
        questionScoreMethod=round.getQuestionScoreMethod();
    }
}
