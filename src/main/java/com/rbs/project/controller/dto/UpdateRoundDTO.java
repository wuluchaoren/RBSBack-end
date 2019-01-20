package com.rbs.project.controller.dto;

/**
 * @Author: WinstonDeng
 * @Description: OOAD_Course_ManageSystem
 * @Date: Created in 0:00 2018/12/26
 * @Modified by:
 */
public class UpdateRoundDTO {
    private Integer presentationScoreMethod;
    private Integer reportScoreMethod;
    private Integer questionScoreMethod;

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

    @Override
    public String toString() {
        return "UpdateRoundDTO{" +
                "presentationScoreMethod=" + presentationScoreMethod +
                ", reportScoreMethod=" + reportScoreMethod +
                ", questionScoreMethod=" + questionScoreMethod +
                '}';
    }
}
