package com.rbs.project.controller.vo;

import com.rbs.project.pojo.entity.Course;
import com.rbs.project.pojo.entity.Team;
import com.rbs.project.pojo.entity.TeamValidApplication;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 15:54 2018/12/23
 */
public class TeamValidApplicationVO {
    private Long id;
    private CourseInfoVO courseInfoVO;
    private TeamBaseInfoVO teamBaseInfoVO;
    private String reason;

    public TeamValidApplicationVO(TeamValidApplication teamValidApplication){
        id=teamValidApplication.getId();
        reason=teamValidApplication.getReason();
    }
    public TeamValidApplicationVO setCourse(Course course){
        courseInfoVO=new CourseInfoVO(course);
        return this;
    }
    public TeamValidApplicationVO setTeam(Team team){
        teamBaseInfoVO=new TeamBaseInfoVO(team);
        return this;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CourseInfoVO getCourseInfoVO() {
        return courseInfoVO;
    }

    public void setCourseInfoVO(CourseInfoVO courseInfoVO) {
        this.courseInfoVO = courseInfoVO;
    }

    public TeamBaseInfoVO getTeamBaseInfoVO() {
        return teamBaseInfoVO;
    }

    public void setTeamBaseInfoVO(TeamBaseInfoVO teamBaseInfoVO) {
        this.teamBaseInfoVO = teamBaseInfoVO;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "TeamValidApplicationVO{" +
                "id=" + id +
                ", courseInfoVO=" + courseInfoVO +
                ", teamBaseInfoVO=" + teamBaseInfoVO +
                ", reason='" + reason + '\'' +
                '}';
    }
}
