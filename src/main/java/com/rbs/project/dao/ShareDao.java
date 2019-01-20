package com.rbs.project.dao;

import com.rbs.project.exception.MyException;
import com.rbs.project.mapper.*;
import com.rbs.project.mapper.strategy.ConflictCourseStrategyMapper;
import com.rbs.project.pojo.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: WinstonDeng
 * @Description: OOAD_Course_ManageSystem
 * @Date: Created in 16:21 2018/12/23
 * @Modified by:
 */

@Repository
public class ShareDao {

    @Autowired
    private ShareSeminarApplicationMapper shareSeminarApplicationMapper;

    @Autowired
    private ShareTeamApplicationMapper shareTeamApplicationMapper;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private CClassMapper cClassMapper;

    @Autowired
    private TeamMapper teamMapper;

    @Autowired
    private CClassTeamMapper cClassTeamMapper;

    @Autowired
    private SeminarMapper seminarMapper;

    @Autowired
    private RoundMapper roundMapper;

    @Autowired
    private CClassSeminarMapper cClassSeminarMapper;

    @Autowired
    private RoundScoreMapper roundScoreMapper;

    @Autowired
    private SeminarScoreMapper seminarScoreMapper;

    @Autowired
    private AttendanceMapper attendanceMapper;

    public final static int HAS_MAIN_COURSE = 0;
    public final static int HAS_MAIN_COURSE_TEACHER = 1;
    public final static int HAS_SUB_COURSE = 2;
    public final static int HAS_SUB_COURSE_TEACHER = 3;

    private void hasSomethingFun(ShareSeminarApplication shareSeminarApplication, int... hasSomething) {
        for (int i : hasSomething) {
            if (i == HAS_MAIN_COURSE) {
                Course mainCourse = courseMapper.findById(shareSeminarApplication.getMainCourseId());
                shareSeminarApplication.setMainCourse(mainCourse);
            }
            if (i == HAS_MAIN_COURSE_TEACHER) {
                Course mainCourse = courseMapper.findById(shareSeminarApplication.getMainCourseId());
                shareSeminarApplication.setMainCourseTeacher(teacherMapper.findById(mainCourse.getTeacherId()));
            }
            if (i == HAS_SUB_COURSE) {
                Course subCourse = courseMapper.findById(shareSeminarApplication.getSubCourseId());
                shareSeminarApplication.setSubCourse(subCourse);
            }
            if (i == HAS_SUB_COURSE_TEACHER) {
                shareSeminarApplication.setSubCourseTeacher(teacherMapper.findById(shareSeminarApplication.getSubCourseTeacherId()));
            }
        }
    }

    /**
     * 警告：此处重载了，但不是个好做法，根本原因在于两种请求字段完全重复，应该用多态来实现，但是没有这样做
     */
    private void hasSomethingFun(ShareTeamApplication shareTeamApplication, int... hasSomething) {
        for (int i : hasSomething) {
            if (i == HAS_MAIN_COURSE) {
                Course mainCourse = courseMapper.findById(shareTeamApplication.getMainCourseId());
                shareTeamApplication.setMainCourse(mainCourse);
            }
            if (i == HAS_MAIN_COURSE_TEACHER) {
                Course mainCourse = courseMapper.findById(shareTeamApplication.getMainCourseId());
                shareTeamApplication.setMainCourseTeacher(teacherMapper.findById(mainCourse.getTeacherId()));
            }
            if (i == HAS_SUB_COURSE) {
                Course subCourse = courseMapper.findById(shareTeamApplication.getSubCourseId());
                shareTeamApplication.setSubCourse(subCourse);
            }
            if (i == HAS_SUB_COURSE_TEACHER) {
                shareTeamApplication.setSubCourseTeacher(teacherMapper.findById(shareTeamApplication.getSubCourseTeacherId()));
            }
        }
    }


    /**
     * Description: 通过id查找共享讨论课申请信息
     *
     * @Author: WinstonDeng
     * @Date: 16:29 2018/12/23
     */
    public ShareSeminarApplication getShareSeminarApplicationById(long id, int... hasSomething) throws MyException {
        ShareSeminarApplication shareSeminarApplication = shareSeminarApplicationMapper.findById(id);
        if (shareSeminarApplication == null) {
            throw new MyException("获取共享讨论课请求错误！该记录不存在", MyException.NOT_FOUND_ERROR);
        }
        hasSomethingFun(shareSeminarApplication, hasSomething);
        return shareSeminarApplication;
    }

    /**
     * Description: 通过主课程id查找共享讨论课信息列表
     *
     * @Author: WinstonDeng
     * @Date: 16:42 2018/12/23
     */
    public List<ShareSeminarApplication> listAllShareSeminarApplicationsByMainCourseId(long mainCourseId, int... hasSomething) throws Exception {
        List<ShareSeminarApplication> shareSeminarApplications = shareSeminarApplicationMapper.findByMainCourseId(mainCourseId);
        for (ShareSeminarApplication shareSeminarApplication : shareSeminarApplications) {
            hasSomethingFun(shareSeminarApplication, hasSomething);
        }
        return shareSeminarApplications;
    }

    /**
     * Description: 通过从课程id查找共享讨论课信息列表
     *
     * @Author: WinstonDeng
     * @Date: 16:55 2018/12/23
     */
    public List<ShareSeminarApplication> listAllShareSeminarApplicationsBySubCourseId(long subCourseId, int... hasSomething) throws Exception {
        List<ShareSeminarApplication> shareSeminarApplications = shareSeminarApplicationMapper.findBySubCourseId(subCourseId);
        for (ShareSeminarApplication shareSeminarApplication : shareSeminarApplications) {
            hasSomethingFun(shareSeminarApplication, hasSomething);
        }
        return shareSeminarApplications;
    }

    /**
     * Description: 通过id查找共享组队申请信息
     *
     * @Author: WinstonDeng
     * @Date: 16:29 2018/12/23
     */
    public ShareTeamApplication getShareTeamApplicationById(long id, int... hasSomething) throws MyException {
        ShareTeamApplication shareTeamApplication = shareTeamApplicationMapper.findById(id);
        if (shareTeamApplication == null) {
            throw new MyException("获取共享分组请求错误！该记录不存在", MyException.NOT_FOUND_ERROR);
        }
        hasSomethingFun(shareTeamApplication, hasSomething);
        return shareTeamApplication;
    }

    /**
     * Description: 通过主课程id查找共享分组信息列表
     *
     * @Author: WinstonDeng
     * @Date: 16:42 2018/12/23
     */
    public List<ShareTeamApplication> listAllShareTeamApplicationsByMainCourseId(long mainCourseId, int... hasSomething) throws Exception {
        List<ShareTeamApplication> shareTeamApplications = shareTeamApplicationMapper.findByMainCourseId(mainCourseId);
        for (ShareTeamApplication shareTeamApplication : shareTeamApplications) {
            hasSomethingFun(shareTeamApplication, hasSomething);
        }
        return shareTeamApplications;
    }

    /**
     * Description: 通过从课程id查找共享分组信息列表
     *
     * @Author: WinstonDeng
     * @Date: 16:55 2018/12/23
     */
    public List<ShareTeamApplication> listAllShareTeamApplicationsBySubCourseId(long subCourseId, int... hasSomething) throws Exception {
        List<ShareTeamApplication> shareTeamApplications = shareTeamApplicationMapper.findBySubCourseId(subCourseId);
        for (ShareTeamApplication shareTeamApplication : shareTeamApplications) {
            hasSomethingFun(shareTeamApplication, hasSomething);
        }
        return shareTeamApplications;
    }

    /**
     * Description: 通过id更新组队共享请求状态
     *
     * @Author: WinstonDeng
     * @Date: 0:20 2018/12/25
     */
    public boolean updateShareTeamApplicationStatus(long requestId, Integer status) throws MyException {
        ShareTeamApplication shareTeamApplication = shareTeamApplicationMapper.findById(requestId);
        if (shareTeamApplication == null) {
            throw new MyException("修改组队共享请求状态错误！未找到该条记录", MyException.NOT_FOUND_ERROR);
        }
        if (!shareTeamApplicationMapper.updateStatusById(requestId, status)) {
            throw new MyException("修改组队共享请求状态错误！数据库处理错误", MyException.ERROR);
        }
        return true;
    }


    /**
     * Description: 发起队伍共享请求
     *
     * @Author: WinstonDeng
     * @Date: 10:45 2018/12/27
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean addTeamShareApplication(long courseId, long subCourseId) throws MyException {
        //1. 检查是否为冲突课程，冲突课程直接错误
        if (courseMapper.findAllConflictCourseByNowCourseId(courseId).contains(subCourseId)) {
            throw new MyException("发起组队共享申请错误！两课程是冲突课程", MyException.ERROR);
        }
        //2. 从课程不能发起共享
        if (courseMapper.findById(courseId).getTeamMainCourseId() != 0 ) {
            throw new MyException("发起组队共享申请错误！从课程不能发起共享", MyException.ERROR);
        }
        //   从课程不能接收共享
        if( courseMapper.findById(subCourseId).getTeamMainCourseId() != 0){
            throw new MyException("发起组队共享申请错误！从课程不能接收共享", MyException.ERROR);
        }
        //3. 若无，新建队伍共享请求
        Course subCourse = courseMapper.findById(subCourseId);
        if (subCourse == null) {
            throw new MyException("发起队伍共享请求错误！未找到从课程", MyException.NOT_FOUND_ERROR);
        }
        ShareTeamApplication shareTeamApplication = new ShareTeamApplication();
        shareTeamApplication.setMainCourseId(courseId);
        shareTeamApplication.setSubCourseId(subCourse.getId());
        shareTeamApplication.setSubCourseTeacherId(subCourse.getTeacherId());
        shareTeamApplication.setStatus(ShareTeamApplication.STATUS_UNHANDLE);
        if (!shareTeamApplicationMapper.addShareTeamApplication(shareTeamApplication)) {
            throw new MyException("发起队伍共享请求错误！数据库处理错误", MyException.ERROR);
        }
        return true;
    }

    /**
     * Description: 取消队伍共享
     *
     * @Author: WinstonDeng
     * @Date: 11:16 2018/12/27
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean removeTeamShare(long requestId) throws MyException {
        ShareTeamApplication shareTeamApplication = shareTeamApplicationMapper.findById(requestId);
        if (shareTeamApplication == null) {
            throw new MyException("取消队伍共享错误！未找到该共享记录", MyException.NOT_FOUND_ERROR);
        }
        //1. 删除从课程的共享小组及相关活动
        //获取从课程共享队伍后的所有班级
        //主课程队伍
        List<Team> teams = teamMapper.findByCourseId(shareTeamApplication.getMainCourseId());
        //从课程班级
        List<CClass> cClasses = cClassMapper.findByCourseId(shareTeamApplication.getSubCourseId());
        //如果用从课程班级找到了主课程队伍，即是共享队伍的记录，把这个班级记录下来
        for (CClass cClass
                : cClasses) {
            for (Team team
                    : teams) {
                if (teamMapper.findByCClassId(cClass.getId()).contains(team)) {
                    //删除从课程的klass_team中的从课程班级
                    cClassTeamMapper.deleteByCClassIdAndTeamId(cClass.getId(), team.getId());
                    //TODO 删除以从课程班级队伍为单位的所有活动 已完成
                    List<CClassSeminar> cClassSeminars=cClassSeminarMapper.findByCClassId(cClass.getId());
                    for(CClassSeminar cClassSeminar:cClassSeminars){
                        //TODO 删除seminar_score 已完成
                        seminarScoreMapper.deleteByCClassSeminarId(cClassSeminar.getId());
                        //TODO 删除attendance 已完成
                        attendanceMapper.deleteByCClassSeminarId(cClassSeminar.getId());
                    }
                }
            }
        }
        //TODO 删除round_score 已完成
        List<Round> rounds=roundMapper.findByCourseId(shareTeamApplication.getSubCourseId());
        for (Round round
                :rounds){
            roundScoreMapper.deleteByRoundId(round.getId());
        }

        //2. 删除共享记录
        Course course = courseMapper.findById(shareTeamApplication.getSubCourseId());
        course.setTeamMainCourseId(0);
        if (!courseMapper.updateTeamMainCourseId(course)) {
            throw new MyException("取消队伍共享错误！从课程取消映射数据库处理错误", MyException.ERROR);
        }
        shareTeamApplicationMapper.deleteTeamShareApplication(requestId);
        return true;
    }

    /**
     * Description: 修改讨论课共享申请状态
     *
     * @Author: WinstonDeng
     * @Date: 21:23 2018/12/27
     */
    public boolean updateShareSeminarApplicationStatus(long requestId, Integer status) throws MyException {
        ShareSeminarApplication shareSeminarApplication = shareSeminarApplicationMapper.findById(requestId);
        if (shareSeminarApplication == null) {
            throw new MyException("修改讨论课共享请求状态错误！未找到该条记录", MyException.NOT_FOUND_ERROR);
        }
        if (!shareSeminarApplicationMapper.updateStatusById(requestId, status)) {
            throw new MyException("修改讨论课共享请求状态错误！数据库处理错误", MyException.ERROR);
        }
        return true;
    }

    /**
     * Description: 发起讨论课共享请求
     *
     * @Author: WinstonDeng
     * @Date: 23:10 2018/12/27
     */
    public boolean addSeminarShareApplication(long courseId, long subCourseId) throws MyException {
        //1. 从课程不能发起共享
        if (courseMapper.findById(courseId).getSeminarMainCourseId() != 0 ) {
            throw new MyException("发起讨论课共享申请错误！从课程不能发起共享", MyException.ERROR);
        }
        //   从课程不能接受共享
        if(courseMapper.findById(subCourseId).getSeminarMainCourseId() != 0){
            throw new MyException("发起讨论课共享申请错误！从课程不能接受共享", MyException.ERROR);
        }
        //2. 若无，新建讨论课共享请求
        Course subCourse = courseMapper.findById(subCourseId);
        if (subCourse == null) {
            throw new MyException("发起讨论课共享请求错误！未找到从课程", MyException.NOT_FOUND_ERROR);
        }
        ShareSeminarApplication shareSeminarApplication = new ShareSeminarApplication();
        shareSeminarApplication.setMainCourseId(courseId);
        shareSeminarApplication.setSubCourseId(subCourse.getId());
        shareSeminarApplication.setSubCourseTeacherId(subCourse.getTeacherId());
        shareSeminarApplication.setStatus(ShareSeminarApplication.STATUS_UNHANDLE);
        if (!shareSeminarApplicationMapper.addShareSeminarApplication(shareSeminarApplication)) {
            throw new MyException("发起讨论课共享请求错误！数据库处理错误", MyException.ERROR);
        }
        return true;
    }

    /**
     * Description: 取消讨论课共享
     *
     * @Author: WinstonDeng
     * @Date: 23:22 2018/12/27
     */
    public boolean removeSeminarShare(long requestId) throws Exception{
        ShareSeminarApplication shareSeminarApplication=shareSeminarApplicationMapper.findById(requestId);
        long subCourseId=shareSeminarApplication.getSubCourseId();
        List<Round> rounds = roundMapper.findByCourseId(subCourseId);
        for (Round round
                : rounds) {
            //  1. 删除round
            roundMapper.deleteById(round.getId());
            //  2. 删除round_score
            roundScoreMapper.deleteByRoundId(round.getId());
        }
        //2. 删除共享记录
        Course course = courseMapper.findById(subCourseId);
        course.setSeminarMainCourseId(0);
        if (!courseMapper.updateSeminarMainCourseId(course)) {
            throw new MyException("取消讨论课共享错误！从课程取消映射数据库处理错误", MyException.ERROR);
        }
        shareSeminarApplicationMapper.deleteSeminarShareApplication(requestId);
        return true;
    }

    /**
     * Description: 通过从课程老师id获取所有讨论课共享申请
     * @Author: WinstonDeng
     * @Date: 17:08 2018/12/28
     */
    public List<ShareSeminarApplication> listAllShareSeminarApplicationsByTeacherId(long subTeacherId, int ...hasSomething) throws Exception {
        List<ShareSeminarApplication> shareSeminarApplications=shareSeminarApplicationMapper.findBySubTeacherId(subTeacherId);
        for(ShareSeminarApplication shareSeminarApplication:shareSeminarApplications){
            hasSomethingFun(shareSeminarApplication,hasSomething);
        }
        return shareSeminarApplications;
    }

    /**
     * Description: 通过从课程老师id获取所有组队共享申请
     * @Author: WinstonDeng
     * @Date: 17:19 2018/12/28
     */
    public List<ShareTeamApplication> listAllShareTeamApplicationsByTeacherId(long subTeacherId, int ...hasSomething) throws Exception{
        List<ShareTeamApplication> shareTeamApplications=shareTeamApplicationMapper.findBySubTeacherId(subTeacherId);
        for(ShareTeamApplication shareTeamApplication:shareTeamApplications){
            hasSomethingFun(shareTeamApplication,hasSomething);
        }
        return shareTeamApplications;
    }
}
