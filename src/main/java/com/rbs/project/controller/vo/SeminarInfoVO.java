package com.rbs.project.controller.vo;

import com.rbs.project.pojo.entity.Seminar;

/**
 * @Author: WinstonDeng
 * @Description: OOAD_Course_ManageSystem
 * @Date: Created in 22:01 2018/12/21
 * @Modified by:
 */
public class SeminarInfoVO {
    private Long seminarId;
    private String seminarName;
    private Integer seminarSerial;
    private Integer visible;

    public SeminarInfoVO(){

    }

    public SeminarInfoVO(Seminar seminar){
        seminarId=seminar.getId();
        seminarName=seminar.getName();
        visible=seminar.getVisible();
        seminarSerial=seminar.getSerial();
    }

    public Long getSeminarId() {
        return seminarId;
    }

    public void setSeminarId(Long seminarId) {
        this.seminarId = seminarId;
    }

    public String getSeminarName() {
        return seminarName;
    }

    public void setSeminarName(String seminarName) {
        this.seminarName = seminarName;
    }

    public Integer getVisible() {
        return visible;
    }

    public void setVisible(Integer visible) {
        this.visible = visible;
    }

    public Integer getSeminarSerial() {
        return seminarSerial;
    }

    public void setSeminarSerial(Integer seminarSerial) {
        this.seminarSerial = seminarSerial;
    }

    @Override
    public String toString() {
        return "SeminarInfoVO{" +
                "seminarId=" + seminarId +
                ", seminarName='" + seminarName + '\'' +
                ", seminarSerial=" + seminarSerial +
                ", visible=" + visible +
                '}';
    }
}
