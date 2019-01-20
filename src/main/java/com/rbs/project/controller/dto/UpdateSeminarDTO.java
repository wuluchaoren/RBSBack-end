package com.rbs.project.controller.dto;

import java.sql.Timestamp;

/**
 * @Author: WinstonDeng
 * @Description: OOAD_Course_ManageSystem
 * @Date: Created in 22:22 2018/12/18
 * @Modified by:
 */
public class UpdateSeminarDTO {

    /**
     * 讨论课名字
     */
    private String topic;
    /**
     * 介绍
     */
    private String intro;

    /**
     * 是否可见
     */
    private Integer visible;
    /**
     * 所属轮次
     */
    private Long roundId;
    /**
     * 报名开始时间
     */
    private String enrollStartTime;
    /**
     * 报名结束时间
     */
    private String enrollEndTime;
    /**
     * 报名组数
     */
    private Integer maxTeam;


    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }



    public Integer getVisible() {
        return visible;
    }

    public void setVisible(Integer visible) {
        this.visible = visible;
    }

    public Long getRoundId() {
        return roundId;
    }

    public void setRoundId(Long roundId) {
        this.roundId = roundId;
    }

    public String getEnrollStartTime() {
        return enrollStartTime;
    }

    public void setEnrollStartTime(String enrollStartTime) {
        this.enrollStartTime = enrollStartTime;
    }

    public String getEnrollEndTime() {
        return enrollEndTime;
    }

    public void setEnrollEndTime(String enrollEndTime) {
        this.enrollEndTime = enrollEndTime;
    }

    public Integer getMaxTeam() {
        return maxTeam;
    }

    public void setMaxTeam(Integer maxTeam) {
        this.maxTeam = maxTeam;
    }

    @Override
    public String toString() {
        return "UpdateSeminarDTO{" +
                "topic='" + topic + '\'' +
                ", intro='" + intro + '\'' +
                ", visible=" + visible +
                ", roundId=" + roundId +
                ", enrollStartTime='" + enrollStartTime + '\'' +
                ", enrollEndTime='" + enrollEndTime + '\'' +
                ", maxTeam=" + maxTeam +
                '}';
    }
}
