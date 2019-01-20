package com.rbs.project.pojo.strategy;

/**
 * @Author: WinstonDeng
 * @Description: OOAD_Course_ManageSystem
 * @Date: Created in 15:30 2018/12/15
 * @Modified by:
 */
public class TeamStrategy {

    private long courseId;

    private long strategySerial;

    private String strategyName;

    private long strategyId;
    //==================================================getter AND setter==================================================//


    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    public long getStrategySerial() {
        return strategySerial;
    }

    public void setStrategySerial(long strategySerial) {
        this.strategySerial = strategySerial;
    }

    public String getStrategyName() {
        return strategyName;
    }

    public void setStrategyName(String strategyName) {
        this.strategyName = strategyName;
    }

    public long getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(long strategyId) {
        this.strategyId = strategyId;
    }
}
