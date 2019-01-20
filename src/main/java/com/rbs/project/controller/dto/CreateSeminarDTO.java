package com.rbs.project.controller.dto;

import java.sql.Timestamp;

/**
 * @Author: WinstonDeng
 * @Description: 新建讨论课DTO
 * @Date: Created in 13:26 2018/12/18
 * @Modified by:
 */
public class CreateSeminarDTO {
    /**
     * 讨论课名
     */
    private String name;
    /**
     * 讨论课介绍
     */
    private String intro;
    /**
     * 是否可见
     */
    private Integer visible;
    /**
     * 讨论课开始报名时间
     */
    private String enrollStartTime;
    /**
     * 讨论课结束报名时间
     */
    private String enrollEndTime;
    /**
     * 报名最大组数
     */
    private Integer maxTeam;
    /**
     * 轮次id
     */
    private Long roundId;
    /**
     * 课程id
     */
    private Long courseId;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
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

    public Integer getVisible() {
        return visible;
    }

    public void setVisible(Integer visible) {
        this.visible = visible;
    }

    public Integer getMaxTeam() {
        return maxTeam;
    }

    public void setMaxTeam(Integer maxTeam) {
        this.maxTeam = maxTeam;
    }

    public Long getRoundId() {
        return roundId;
    }

    public void setRoundId(Long roundId) {
        this.roundId = roundId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    @Override
    public String toString() {
        return "CreateSeminarDTO{" +
                "name='" + name + '\'' +
                ", intro='" + intro + '\'' +
                ", visible=" + visible +
                ", enrollStartTime='" + enrollStartTime + '\'' +
                ", enrollEndTime='" + enrollEndTime + '\'' +
                ", maxTeam=" + maxTeam +
                ", roundId=" + roundId +
                ", courseId=" + courseId +
                '}';
    }
}
