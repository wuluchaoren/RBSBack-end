package com.rbs.project.controller.dto;

import com.rbs.project.controller.vo.ScoreVO;

import java.util.List;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 4:10 2018/12/29
 */
public class ScoreDTO {
    private Long teamId;
    private Double reportScore;
    private Double questionScore;
    private Double presentationScore;

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
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

    public Double getPresentationScore() {
        return presentationScore;
    }

    public void setPresentationScore(Double presentationScore) {
        this.presentationScore = presentationScore;
    }

    @Override
    public String toString() {
        return "ScoreDTO{" +
                "teamId=" + teamId +
                ", reportScore=" + reportScore +
                ", questionScore=" + questionScore +
                ", presentationScore=" + presentationScore +
                '}';
    }
}
