package com.rbs.project.controller.vo;

import com.rbs.project.pojo.entity.Team;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 17:07 2018/12/21
 */
public class TeamBaseInfoVO {
    private Long id;
    private String teamSerials;
    private String teamName;
    private Integer teamStatus;

    public TeamBaseInfoVO(Team team) {
        id = team.getId();
        teamSerials = team.getcClass().getSerial() + "-" + String.valueOf(team.getSerial());
        teamName = team.getName();
        teamStatus = team.getStatus();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTeamSerials() {
        return teamSerials;
    }

    public void setTeamSerials(String teamSerials) {
        teamSerials = teamSerials;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        teamName = teamName;
    }

    public Integer getTeamStatus() {
        return teamStatus;
    }

    public void setTeamStatus(Integer teamStatus) {
        this.teamStatus = teamStatus;
    }

    @Override
    public String toString() {
        return "TeamBaseInfoVO{" +
                "id=" + id +
                ", teamSerials='" + teamSerials + '\'' +
                ", teamName='" + teamName + '\'' +
                ", teamStatus=" + teamStatus +
                '}';
    }
}
