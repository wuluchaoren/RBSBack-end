package com.rbs.project.controller.vo;

import com.rbs.project.pojo.entity.RoundScore;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 5:49 2018/12/29
 */
public class BigRoundScoreVO {
    private Double roundPresentationScore;
    private Double roundQuestionScore;
    private Double roundReportScore;
    private Double roundTotalScore;

    public BigRoundScoreVO(RoundScore roundScore) {
        this.roundPresentationScore = roundScore.getPresentationScore();
        this.roundQuestionScore = roundScore.getQuestionScore();
        this.roundReportScore = roundScore.getReportScore();
        this.roundTotalScore = roundScore.getTotalScore();
    }

    public Double getRoundPresentationScore() {
        return roundPresentationScore;
    }

    public void setRoundPresentationScore(Double roundPresentationScore) {
        this.roundPresentationScore = roundPresentationScore;
    }

    public Double getRoundQuestionScore() {
        return roundQuestionScore;
    }

    public void setRoundQuestionScore(Double roundQuestionScore) {
        this.roundQuestionScore = roundQuestionScore;
    }

    public Double getRoundReportScore() {
        return roundReportScore;
    }

    public void setRoundReportScore(Double roundReportScore) {
        this.roundReportScore = roundReportScore;
    }

    public Double getRoundTotalScore() {
        return roundTotalScore;
    }

    public void setRoundTotalScore(Double roundTotalScore) {
        this.roundTotalScore = roundTotalScore;
    }

    @Override
    public String toString() {
        return "BigRoundScoreVO{" +
                "roundPresentationScore=" + roundPresentationScore +
                ", roundQuestionScore=" + roundQuestionScore +
                ", roundReportScore=" + roundReportScore +
                ", roundTotalScore=" + roundTotalScore +
                '}';
    }
}
