package com.rbs.project.pojo.entity;

import java.math.BigDecimal;

/**
 * @Author: WinstonDeng
 * @Description: OOAD_Course_ManageSystem
 * @Date: Created in 14:59 2018/12/15
 * @Modified by:
 */
public class SeminarScore {
    /**
     * 班级讨论课id
     */
    private long cClassSeminarId;
    /**
     * 队伍id
     */
    private long teamId;
    /**
     * 本次讨论课总分
     */
    private Double totalScore;
    /**
     * 本次讨论课展示分
     */
    private Double presentationScore;
    /**
     * 本次讨论课报告分
     */
    private Double reportScore;
    /**
     * 本次讨论课提问分
     */
    private Double questionScore;

    //关系
    /**
     * 一个班级讨论课
     */
    private CClassSeminar cClassSeminar;
    /**
     * 一个队伍
     */
    private Team team;

    //==================================================getter AND setter==================================================//

    public long getcClassSeminarId() {
        return cClassSeminarId;
    }

    public void setcClassSeminarId(long cClassSeminarId) {
        this.cClassSeminarId = cClassSeminarId;
    }

    public long getTeamId() {
        return teamId;
    }

    public void setTeamId(long teamId) {
        this.teamId = teamId;
    }

    public Double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Double totalScore) {
        this.totalScore = totalScore;
    }

    public Double getPresentationScore() {
        return presentationScore;
    }

    public void setPresentationScore(Double presentationScore) {
        this.presentationScore = presentationScore;
    }

    public Double getReportScore() {
        return reportScore;
    }

    public void setReportScore(Double reportScore) {
        this.reportScore = reportScore;
    }

    public Double getQuestionScore() {
        return questionScore;
    }

    public void setQuestionScore(Double questionScore) {
        this.questionScore = questionScore;
    }

    public CClassSeminar getcClassSeminar() {
        return cClassSeminar;
    }

    public void setcClassSeminar(CClassSeminar cClassSeminar) {
        this.cClassSeminar = cClassSeminar;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }


    //====================== toString ====================

    @Override
    public String toString() {
        return "SeminarScore{" +
                "cClassSeminarId=" + cClassSeminarId +
                ", teamId=" + teamId +
                ", totalScore=" + totalScore +
                ", presentationScore=" + presentationScore +
                ", reportScore=" + reportScore +
                ", questionScore=" + questionScore +
                ", cClassSeminar=" + cClassSeminar +
                ", team=" + team +
                '}';
    }
}
