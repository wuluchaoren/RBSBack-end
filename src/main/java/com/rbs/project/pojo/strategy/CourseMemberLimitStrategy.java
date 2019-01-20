package com.rbs.project.pojo.strategy;

/**
 * Description: 课程人数策略
 * @Author: 17Wang
 * @Time: 15:03 2018/12/28
*/
public class CourseMemberLimitStrategy {

    private long id;
    /**
     * 课程id
     */
    private long courseId;
    /**
     * 最少人数
     */
    private Integer minMember;
    /**
     * 最多人数
     */
    private Integer maxMember;

    //==================================================getter AND setter==================================================//

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
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
        return "CourseMemberLimitStrategy{" +
                "id=" + id +
                ", courseId=" + courseId +
                ", minMember=" + minMember +
                ", maxMember=" + maxMember +
                '}';
    }
}
