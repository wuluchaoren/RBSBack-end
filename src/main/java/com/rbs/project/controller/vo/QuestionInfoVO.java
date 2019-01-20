package com.rbs.project.controller.vo;

import com.rbs.project.pojo.entity.Question;

/**
 * @Author: WinstonDeng
 * @Description: OOAD_Course_ManageSystem
 * @Date: Created in 16:13 2018/12/22
 * @Modified by:
 */
public class QuestionInfoVO {
    private Long questionId;
    private Long teamId;
    private String teamSerial;
    private Long attendanceId;
    private Boolean selected;
    private Double score;
    private Long studentId;
    private String studentName;

    public QuestionInfoVO(){

    }
    public QuestionInfoVO(Question question){
        questionId=question.getId();
        teamId=question.getTeamId();
        teamSerial=question.getcClassSeminar().getcClassId()+"-"+question.getTeam().getSerial();
        attendanceId=question.getAttendanceId();
        if(question.getSelected()==0){
            //未被选中
            selected=false;
        }else if(question.getSelected()==1){
            //选中
            selected=true;
        }
        score=question.getScore();
        studentId=question.getStudentId();
        studentName=question.getStudent().getStudentName();
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public String getTeamSerial() {
        return teamSerial;
    }

    public void setTeamSerial(String teamSerial) {
        this.teamSerial = teamSerial;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Long getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(Long attendanceId) {
        this.attendanceId = attendanceId;
    }

    @Override
    public String toString() {
        return "QuestionInfoVO{" +
                "questionId=" + questionId +
                ", teamId=" + teamId +
                ", teamSerial='" + teamSerial + '\'' +
                ", attendanceId=" + attendanceId +
                ", selected=" + selected +
                ", score=" + score +
                ", studentId=" + studentId +
                ", studentName='" + studentName + '\'' +
                '}';
    }
}
