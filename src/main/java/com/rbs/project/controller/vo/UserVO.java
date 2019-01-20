package com.rbs.project.controller.vo;

import com.rbs.project.pojo.entity.Student;
import com.rbs.project.pojo.entity.Teacher;
import com.rbs.project.pojo.entity.User;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 16:29 2018/12/16
 */
public class UserVO {
    private Long id;
    private String account;
    private String name;
    private String email;
    private Long teamId;

    public UserVO() {

    }

    public UserVO(User user) {
        id = user.getId();
        account = user.getUsername();
        email = user.getEmail();

        if (user instanceof Student) {
            name = ((Student) user).getStudentName();
            teamId = ((Student) user).getTeamId();
        } else if (user instanceof Teacher) {
            name = ((Teacher) user).getTeacherName();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    @Override
    public String toString() {
        return "UserVO{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
