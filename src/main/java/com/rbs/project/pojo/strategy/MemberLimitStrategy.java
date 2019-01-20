package com.rbs.project.pojo.strategy;

/**
 * @Author: WinstonDeng
 * @Description: member_limit_strategy 表记录的是对一个team中总人数的限制
 * @Date: Created in 15:29 2018/12/15
 * @Modified by:
 */
public class MemberLimitStrategy {
    private long id;
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
}
