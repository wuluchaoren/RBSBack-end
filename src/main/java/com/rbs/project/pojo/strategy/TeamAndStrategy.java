package com.rbs.project.pojo.strategy;

/**
 * @Author: WinstonDeng
 * @Description: “与”组队策略
 * @Date: Created in 15:18 2018/12/15
 * @Modified by:
 */
public class TeamAndStrategy {
    private long id;

    private String strategyName;

    private long strategyId;

    //==================================================getter AND setter==================================================//

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
