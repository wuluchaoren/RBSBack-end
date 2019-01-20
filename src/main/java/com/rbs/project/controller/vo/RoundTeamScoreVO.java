package com.rbs.project.controller.vo;

import com.rbs.project.pojo.entity.Team;

import java.util.List;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 4:59 2018/12/29
 */
public class RoundTeamScoreVO {
    /**
     * //轮次信息
     */
    private RoundInfoVO roundInfoVO;
    /**
     * //学生信息
     */
    private List<BigTeamVO> bigTeamVOS;

    public RoundInfoVO getRoundInfoVO() {
        return roundInfoVO;
    }

    public void setRoundInfoVO(RoundInfoVO roundInfoVO) {
        this.roundInfoVO = roundInfoVO;
    }

    public List<BigTeamVO> getBigTeamVOS() {
        return bigTeamVOS;
    }

    public void setBigTeamVOS(List<BigTeamVO> bigTeamVOS) {
        this.bigTeamVOS = bigTeamVOS;
    }

    @Override
    public String toString() {
        return "RoundTeamScoreVO{" +
                "roundInfoVO=" + roundInfoVO +
                ", bigTeamVOS=" + bigTeamVOS +
                '}';
    }
}

