package com.rbs.project.controller.vo;

import com.rbs.project.pojo.entity.Seminar;
import com.rbs.project.pojo.entity.SeminarScore;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 5:49 2018/12/29
 */
public class BigSeminarVO {
    private Long seminarId;
    private Long classId;
    private String seminarName;
    private Double seminarPresentationScore;
    private Double seminarQuestionScore;
    private Double seminarReportScore;

    public BigSeminarVO(Seminar seminar, SeminarScore seminarScore) {
        this.seminarId = seminar.getId();
        this.seminarName = seminar.getName();
        if(seminarScore!=null){
            this.seminarPresentationScore = seminarScore.getPresentationScore();
            this.seminarQuestionScore = seminarScore.getQuestionScore();
            this.seminarReportScore = seminarScore.getReportScore();
        }
    }

    public Long getSeminarId() {
        return seminarId;
    }

    public void setSeminarId(Long seminarId) {
        this.seminarId = seminarId;
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public String getSeminarName() {
        return seminarName;
    }

    public void setSeminarName(String seminarName) {
        this.seminarName = seminarName;
    }

    public Double getSeminarPresentationScore() {
        return seminarPresentationScore;
    }

    public void setSeminarPresentationScore(Double seminarPresentationScore) {
        this.seminarPresentationScore = seminarPresentationScore;
    }

    public Double getSeminarQuestionScore() {
        return seminarQuestionScore;
    }

    public void setSeminarQuestionScore(Double seminarQuestionScore) {
        this.seminarQuestionScore = seminarQuestionScore;
    }

    public Double getSeminarReportScore() {
        return seminarReportScore;
    }

    public void setSeminarReportScore(Double seminarReportScore) {
        this.seminarReportScore = seminarReportScore;
    }

    @Override
    public String toString() {
        return "BigSeminarVO{" +
                "seminarId=" + seminarId +
                ", classId=" + classId +
                ", seminarName='" + seminarName + '\'' +
                ", seminarPresentationScore=" + seminarPresentationScore +
                ", seminarQuestionScore=" + seminarQuestionScore +
                ", seminarReportScore=" + seminarReportScore +
                '}';
    }
}
