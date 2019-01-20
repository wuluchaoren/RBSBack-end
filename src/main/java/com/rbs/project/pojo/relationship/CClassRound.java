package com.rbs.project.pojo.relationship;

/**
 * @Author: WinstonDeng
 * @Description: OOAD_Course_ManageSystem
 * @Date: Created in 15:16 2018/12/20
 * @Modified by:
 */
public class CClassRound {
    /**
     * 班级id
     */
    private long cClassId;
    /**
     * 轮次id
     */
    private long roundId;
    /**
     * 报名次数
     */
    private Integer enrollNumber;

    public final static int DEFAULT_ENROLL_NUM=1;

    public long getcClassId() {
        return cClassId;
    }

    public void setcClassId(long cClassId) {
        this.cClassId = cClassId;
    }

    public long getRoundId() {
        return roundId;
    }

    public void setRoundId(long roundId) {
        this.roundId = roundId;
    }

    public Integer getEnrollNumber() {
        return enrollNumber;
    }

    public void setEnrollNumber(Integer enrollNumber) {
        this.enrollNumber = enrollNumber;
    }
}
