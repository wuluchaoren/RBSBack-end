package com.rbs.project.controller.vo;

import com.rbs.project.pojo.entity.ShareSeminarApplication;
import com.rbs.project.pojo.entity.ShareTeamApplication;

/**
 * @Author: WinstonDeng
 * @Description: OOAD_Course_ManageSystem
 * @Date: Created in 16:08 2018/12/28
 * @Modified by:
 */
public class RequestInfoVO {
    private Long requestId;
    private Long mainCourseId;
    private String mainCourseName;
    private Long mainTeacherId;
    private String mainTeacherName;
    private String shareType;

    public RequestInfoVO(ShareTeamApplication shareTeamApplication){
        requestId=shareTeamApplication.getId();
        mainCourseId=shareTeamApplication.getMainCourseId();
        mainCourseName=shareTeamApplication.getMainCourse().getName();
        mainTeacherId=shareTeamApplication.getMainCourseTeacher().getId();
        mainTeacherName=shareTeamApplication.getMainCourseTeacher().getTeacherName();
        shareType="共享组队";
    }
    public RequestInfoVO(ShareSeminarApplication shareSeminarApplication){
        requestId=shareSeminarApplication.getId();
        mainCourseId=shareSeminarApplication.getMainCourseId();
        mainCourseName=shareSeminarApplication.getMainCourse().getName();
        mainTeacherId=shareSeminarApplication.getMainCourseTeacher().getId();
        mainTeacherName=shareSeminarApplication.getMainCourseTeacher().getTeacherName();
        shareType="共享讨论课";
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public Long getMainCourseId() {
        return mainCourseId;
    }

    public void setMainCourseId(Long mainCourseId) {
        this.mainCourseId = mainCourseId;
    }

    public String getMainCourseName() {
        return mainCourseName;
    }

    public void setMainCourseName(String mainCourseName) {
        this.mainCourseName = mainCourseName;
    }

    public Long getMainTeacherId() {
        return mainTeacherId;
    }

    public void setMainTeacherId(Long mainTeacherId) {
        this.mainTeacherId = mainTeacherId;
    }

    public String getMainTeacherName() {
        return mainTeacherName;
    }

    public void setMainTeacherName(String mainTeacherName) {
        this.mainTeacherName = mainTeacherName;
    }

    public String getShareType() {
        return shareType;
    }

    public void setShareType(String shareType) {
        this.shareType = shareType;
    }

    @Override
    public String toString() {
        return "RequestInfoVO{" +
                "requestId=" + requestId +
                ", mainCourseId=" + mainCourseId +
                ", mainCourseName='" + mainCourseName + '\'' +
                ", mainTeacherId=" + mainTeacherId +
                ", mainTeacherName='" + mainTeacherName + '\'' +
                ", shareType='" + shareType + '\'' +
                '}';
    }
}
