package com.rbs.project.controller.vo;

import com.rbs.project.pojo.entity.Team;

import java.util.List;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 5:49 2018/12/29
 */
public class BigTeamVO {
    private TeamBaseInfoVO teamBaseInfoVO;

    private List<BigSeminarVO> bigSeminarVOS;

    private BigRoundScoreVO bigRoundScoreVO;

    public BigTeamVO(Team team) {
        teamBaseInfoVO = new TeamBaseInfoVO(team);
    }

    public TeamBaseInfoVO getTeamBaseInfoVO() {
        return teamBaseInfoVO;
    }

    public void setTeamBaseInfoVO(TeamBaseInfoVO teamBaseInfoVO) {
        this.teamBaseInfoVO = teamBaseInfoVO;
    }

    public List<BigSeminarVO> getBigSeminarVOS() {
        return bigSeminarVOS;
    }

    public void setBigSeminarVOS(List<BigSeminarVO> bigSeminarVOS) {
        this.bigSeminarVOS = bigSeminarVOS;
    }

    public BigRoundScoreVO getBigRoundScoreVO() {
        return bigRoundScoreVO;
    }

    public void setBigRoundScoreVO(BigRoundScoreVO bigRoundScoreVO) {
        this.bigRoundScoreVO = bigRoundScoreVO;
    }

    @Override
    public String toString() {
        return "BigTeamVO{" +
                "teamBaseInfoVO=" + teamBaseInfoVO +
                ", bigSeminarVOS=" + bigSeminarVOS +
                ", bigRoundScoreVO=" + bigRoundScoreVO +
                '}';
    }
}
