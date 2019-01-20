package com.rbs.project.controller.vo;

import com.rbs.project.pojo.entity.RoundScore;
import com.rbs.project.pojo.entity.SeminarScore;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 16:27 2018/12/22
 */
public class ScoreVO {
    private TeamBaseInfoVO teamBaseInfoVO;
    private Double totalScore;
    private Double reportScore;
    private Double questionScore;
    private Double presentationScore;

    public ScoreVO revertSeminarScore(SeminarScore seminarScore) {
        teamBaseInfoVO = new TeamBaseInfoVO(seminarScore.getTeam());
        totalScore = seminarScore.getTotalScore();
        reportScore = seminarScore.getReportScore();
        questionScore = seminarScore.getQuestionScore();
        presentationScore = seminarScore.getPresentationScore();
        return this;
    }

    public ScoreVO revertSeminarScore(RoundScore roundScore) {
        teamBaseInfoVO = new TeamBaseInfoVO(roundScore.getTeam());
        totalScore = roundScore.getTotalScore();
        reportScore = roundScore.getReportScore();
        questionScore = roundScore.getQuestionScore();
        presentationScore = roundScore.getPresentationScore();
        return this;
    }

    public TeamBaseInfoVO getTeamBaseInfoVO() {
        return teamBaseInfoVO;
    }

    public void setTeamBaseInfoVO(TeamBaseInfoVO teamBaseInfoVO) {
        this.teamBaseInfoVO = teamBaseInfoVO;
    }

    public Double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Double totalScore) {
        this.totalScore = totalScore;
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
        return "ScoreVO{" +
                "teamBaseInfoVO=" + teamBaseInfoVO +
                ", totalScore=" + totalScore +
                ", reportScore=" + reportScore +
                ", questionScore=" + questionScore +
                ", presentationScore=" + presentationScore +
                '}';
    }
}
