package com.rbs.project.controller.vo;

import com.rbs.project.pojo.strategy.CourseMemberLimitStrategy;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 3:03 2018/12/29
 */
public class CourseMemberLimitStrategyVO {
    private Long id;
    private Long courseId;
    private String courseName;
    private Integer minMember;
    private Integer maxMember;
    public CourseMemberLimitStrategyVO(CourseMemberLimitStrategy courseMemberLimitStrategy){
        this.id=courseMemberLimitStrategy.getId();
        this.courseId=courseMemberLimitStrategy.getCourseId();
        this.minMember=courseMemberLimitStrategy.getMinMember();
        this.maxMember=courseMemberLimitStrategy.getMaxMember();
    }
    public CourseMemberLimitStrategyVO setCourseName(String courseName){
        this.courseName=courseName;
        return this;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public Integer getMinMember() {
        return minMember;
    }

    public void setMinMember(Integer minMember) {
        this.minMember = minMember;
    }

    public Integer getMaxMember() {
        return maxMember;
    }

    public void setMaxMember(Integer maxMember) {
        this.maxMember = maxMember;
    }

    @Override
    public String toString() {
        return "CourseMemberLimitStrategyVO{" +
                "id=" + id +
                ", courseId=" + courseId +
                ", courseName='" + courseName + '\'' +
                ", minMember=" + minMember +
                ", maxMember=" + maxMember +
                '}';
    }
}
