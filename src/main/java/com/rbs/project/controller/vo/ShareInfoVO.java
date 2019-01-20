package com.rbs.project.controller.vo;

/**
 * @Author: WinstonDeng
 * @Description: OOAD_Course_ManageSystem
 * @Date: Created in 17:49 2018/12/23
 * @Modified by:
 */
public class ShareInfoVO {
    private Long shareId;
    private Long mainCourseId;
    private String mainCourseName;
    private String mainTeacherName;
    private Long subCourseId;
    private String subCourseName;
    private String subTeacherName;
    private String shareType;
    private String Info;

    public Long getShareId() {
        return shareId;
    }

    public void setShareId(Long shareId) {
        this.shareId = shareId;
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

    public Long getSubCourseId() {
        return subCourseId;
    }

    public void setSubCourseId(Long subCourseId) {
        this.subCourseId = subCourseId;
    }

    public String getSubCourseName() {
        return subCourseName;
    }

    public void setSubCourseName(String subCourseName) {
        this.subCourseName = subCourseName;
    }

    public String getShareType() {
        return shareType;
    }

    public void setShareType(String shareType) {
        this.shareType = shareType;
    }

    public String getInfo() {
        return Info;
    }

    public void setInfo(String info) {
        Info = info;
    }

    public String getMainTeacherName() {
        return mainTeacherName;
    }

    public void setMainTeacherName(String mainTeacherName) {
        this.mainTeacherName = mainTeacherName;
    }

    public String getSubTeacherName() {
        return subTeacherName;
    }

    public void setSubTeacherName(String subTeacherName) {
        this.subTeacherName = subTeacherName;
    }

    @Override
    public String toString() {
        return "ShareInfoVO{" +
                "shareId=" + shareId +
                ", mainCourseId=" + mainCourseId +
                ", mainCourseName='" + mainCourseName + '\'' +
                ", mainTeacherName='" + mainTeacherName + '\'' +
                ", subCourseId=" + subCourseId +
                ", subCourseName='" + subCourseName + '\'' +
                ", subTeacherName='" + subTeacherName + '\'' +
                ", shareType='" + shareType + '\'' +
                ", Info='" + Info + '\'' +
                '}';
    }
}
