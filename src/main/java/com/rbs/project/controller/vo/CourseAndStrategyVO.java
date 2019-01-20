package com.rbs.project.controller.vo;

import com.rbs.project.pojo.entity.Course;
import com.rbs.project.pojo.strategy.CourseMemberLimitStrategy;
import com.rbs.project.pojo.strategy.MemberLimitStrategy;
import com.rbs.project.utils.JsonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 23:15 2018/12/18
 */
public class CourseAndStrategyVO {
    private Long id;
    private String name;
    private String intro;
    private Integer presentationPercentage;
    private Integer questionPercentage;
    private Integer reportPercentage;
    private String teamStartTime;
    private String teamEndTime;
    private Long teamMainCourseId;
    private Long seminarMainCourseId;
    private Long teacherId;
    private String teacherName;

    private Boolean courseMemberLimitStrategyStyle;
    private List<CourseMemberLimitStrategyVO> courseMemberLimitStrategyVOS;
    private MemberLimitStrategy memberLimitStrategy;
    private List<List<CourseInfoVO>> conflictCourses;
    private Boolean ShareTeam;
    private Boolean ShareSeminar;

    public CourseAndStrategyVO() {

    }

    public CourseAndStrategyVO(Course course) {
        id = course.getId();
        name = course.getName();
        intro = course.getIntro();
        teamMainCourseId = course.getTeamMainCourseId();
        seminarMainCourseId = course.getSeminarMainCourseId();
        presentationPercentage = course.getPresentationPercentage();
        questionPercentage = course.getQuestionPercentage();
        reportPercentage = course.getReportPercentage();
        teamStartTime = JsonUtils.TimestampToString(course.getTeamStartTime());
        teamEndTime = JsonUtils.TimestampToString(course.getTeamEndTime());
        teacherId = course.getTeacherId();
        teacherName = course.getTeacher().getTeacherName();

        memberLimitStrategy = course.getMemberLimitStrategy();
        List<List<CourseInfoVO>> lists = new ArrayList<>();
        if (course.getConflictCourses() != null) {
            for (List<Course> courses : course.getConflictCourses()) {
                List<CourseInfoVO> courseInfoVOS = new ArrayList<>();
                for (Course c : courses) {
                    courseInfoVOS.add(new CourseInfoVO(c));
                }
                lists.add(courseInfoVOS);
            }

        }
        conflictCourses = lists;

        if (course.getTeamMainCourseId() == 0) {
            ShareTeam = false;
        }
        if (course.getSeminarMainCourseId() == 0) {
            ShareSeminar = false;
        }
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public Long getTeamMainCourseId() {
        return teamMainCourseId;
    }

    public void setTeamMainCourseId(Long teamMainCourseId) {
        this.teamMainCourseId = teamMainCourseId;
    }

    public Long getSeminarMainCourseId() {
        return seminarMainCourseId;
    }

    public void setSeminarMainCourseId(Long seminarMainCourseId) {
        this.seminarMainCourseId = seminarMainCourseId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public Integer getPresentationPercentage() {
        return presentationPercentage;
    }

    public void setPresentationPercentage(Integer presentationPercentage) {
        this.presentationPercentage = presentationPercentage;
    }

    public Integer getQuestionPercentage() {
        return questionPercentage;
    }

    public void setQuestionPercentage(Integer questionPercentage) {
        this.questionPercentage = questionPercentage;
    }

    public Integer getReportPercentage() {
        return reportPercentage;
    }

    public void setReportPercentage(Integer reportPercentage) {
        this.reportPercentage = reportPercentage;
    }

    public String getTeamStartTime() {
        return teamStartTime;
    }

    public void setTeamStartTime(String teamStartTime) {
        this.teamStartTime = teamStartTime;
    }

    public String getTeamEndTime() {
        return teamEndTime;
    }

    public void setTeamEndTime(String teamEndTime) {
        this.teamEndTime = teamEndTime;
    }

    public Boolean isShareTeam() {
        return ShareTeam;
    }

    public void setShareTeam(Boolean shareTeam) {
        ShareTeam = shareTeam;
    }

    public Boolean isShareSeminar() {
        return ShareSeminar;
    }

    public void setShareSeminar(Boolean shareSeminar) {
        ShareSeminar = shareSeminar;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getShareTeam() {
        return ShareTeam;
    }

    public Boolean getShareSeminar() {
        return ShareSeminar;
    }

    public MemberLimitStrategy getMemberLimitStrategy() {
        return memberLimitStrategy;
    }

    public void setMemberLimitStrategy(MemberLimitStrategy memberLimitStrategy) {
        this.memberLimitStrategy = memberLimitStrategy;
    }

    public List<List<CourseInfoVO>> getConflictCourses() {
        return conflictCourses;
    }

    public void setConflictCourses(List<List<CourseInfoVO>> conflictCourses) {
        this.conflictCourses = conflictCourses;
    }

    public List<CourseMemberLimitStrategyVO> getCourseMemberLimitStrategyVOS() {
        return courseMemberLimitStrategyVOS;
    }

    public CourseAndStrategyVO setCourseMemberLimitStrategyVOS(List<CourseMemberLimitStrategyVO> courseMemberLimitStrategyVOS) {
        this.courseMemberLimitStrategyVOS = courseMemberLimitStrategyVOS;
        return this;
    }

    public boolean isCourseMemberLimitStrategyStyle() {
        return courseMemberLimitStrategyStyle;
    }

    public CourseAndStrategyVO setCourseMemberLimitStrategyStyle(boolean courseMemberLimitStrategyStyle) {
        this.courseMemberLimitStrategyStyle = courseMemberLimitStrategyStyle;
        return this;
    }

    @Override
    public String toString() {
        return "CourseAndStrategyVO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", intro='" + intro + '\'' +
                ", presentationPercentage=" + presentationPercentage +
                ", questionPercentage=" + questionPercentage +
                ", reportPercentage=" + reportPercentage +
                ", teamStartTime='" + teamStartTime + '\'' +
                ", teamEndTime='" + teamEndTime + '\'' +
                ", teamMainCourseId=" + teamMainCourseId +
                ", seminarMainCourseId=" + seminarMainCourseId +
                ", teacherId=" + teacherId +
                ", teacherName='" + teacherName + '\'' +
                ", courseMemberLimitStrategyStyle=" + courseMemberLimitStrategyStyle +
                ", courseMemberLimitStrategyVOS=" + courseMemberLimitStrategyVOS +
                ", memberLimitStrategy=" + memberLimitStrategy +
                ", conflictCourses=" + conflictCourses +
                ", ShareTeam=" + ShareTeam +
                ", ShareSeminar=" + ShareSeminar +
                '}';
    }
}