package com.rbs.project.controller.vo;

import com.rbs.project.pojo.entity.Attendance;
import com.rbs.project.pojo.entity.SeminarScore;
import com.rbs.project.utils.JsonUtils;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 17:20 2018/12/21
 */
public class AttendanceVO implements Comparable<AttendanceVO> {
    private Long id;
    private Integer present;
    private String pptName;
    private String pptUrl;
    private String reportName;
    private String reportUrl;
    private String reportDDL;
    private TeamBaseInfoVO teamBaseInfoVO;
    private Integer teamOrder;

    private Double presentationScore;
    ;

    public AttendanceVO() {

    }

    public AttendanceVO(Attendance attendance) {
        id = attendance.getId();
        present = attendance.getPresent();
        pptName = attendance.getPptName();
        pptUrl = attendance.getPptUrl();
        reportName = attendance.getReportName();
        reportUrl = attendance.getReportUrl();
        if (attendance.getcClassSeminar() != null) {
            if(attendance.getcClassSeminar().getReportDDL()==null){
                reportDDL=null;
            }else {
                reportDDL = JsonUtils.TimestampToString(attendance.getcClassSeminar().getReportDDL());
            }
        }
        if (attendance.getTeam() != null) {
            teamBaseInfoVO = new TeamBaseInfoVO(attendance.getTeam());
        }

        teamOrder = attendance.getTeamOrder();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPresent() {
        return present;
    }

    public void setPresent(Integer present) {
        this.present = present;
    }

    public String getPptName() {
        return pptName;
    }

    public void setPptName(String pptName) {
        this.pptName = pptName;
    }

    public String getPptUrl() {
        return pptUrl;
    }

    public void setPptUrl(String pptUrl) {
        this.pptUrl = pptUrl;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getReportUrl() {
        return reportUrl;
    }

    public void setReportUrl(String reportUrl) {
        this.reportUrl = reportUrl;
    }

    public String getReportDDL() {
        return reportDDL;
    }

    public void setReportDDL(String reportDDL) {
        this.reportDDL = reportDDL;
    }

    public TeamBaseInfoVO getTeamBaseInfoVO() {
        return teamBaseInfoVO;
    }

    public void setTeamBaseInfoVO(TeamBaseInfoVO teamBaseInfoVO) {
        this.teamBaseInfoVO = teamBaseInfoVO;
    }

    public Integer getTeamOrder() {
        return teamOrder;
    }

    public void setTeamOrder(Integer teamOrder) {
        this.teamOrder = teamOrder;
    }

    public Double getPresentationScore() {
        return presentationScore;
    }

    public AttendanceVO setPresentationScore(Double presentationScore) {
        this.presentationScore = presentationScore;
        return this;
    }

    @Override
    public int compareTo(AttendanceVO o) {
        return this.getTeamOrder().compareTo(o.getTeamOrder());
    }

    @Override
    public String toString() {
        return "AttendanceVO{" +
                "id=" + id +
                ", present=" + present +
                ", pptName='" + pptName + '\'' +
                ", pptUrl='" + pptUrl + '\'' +
                ", reportName='" + reportName + '\'' +
                ", reportUrl='" + reportUrl + '\'' +
                ", reportDDL='" + reportDDL + '\'' +
                ", teamBaseInfoVO=" + teamBaseInfoVO +
                ", teamOrder=" + teamOrder +
                ", presentationScore=" + presentationScore +
                '}';
    }
}
