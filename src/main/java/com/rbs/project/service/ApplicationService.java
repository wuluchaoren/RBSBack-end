package com.rbs.project.service;

import com.rbs.project.dao.*;
import com.rbs.project.exception.MyException;
import com.rbs.project.pojo.entity.*;
import com.rbs.project.pojo.relationship.CClassRound;
import com.rbs.project.pojo.relationship.CClassStudent;
import com.rbs.project.utils.LogicUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 13:43 2018/12/23
 */
@Service
public class ApplicationService {
    @Autowired
    private TeamApplicationDao teamApplicationDao;

    @Autowired
    private TeamDao teamDao;

    @Autowired
    private CourseDao courseDao;

    @Autowired
    private ShareDao shareDao;

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private SeminarDao seminarDao;

    @Autowired
    private RoundDao roundDao;

    @Autowired
    private CClassSeminarDao cClassSeminarDao;

    @Autowired
    private SeminarService seminarService;

    @Autowired
    private SeminarScoreDao seminarScoreDao;

    @Autowired
    private RoundScoreDao roundScoreDao;

    @Autowired
    private CClassDao cClassDao;

    /**
     * Description: 查看team的请求
     * 1、需要加上主从课程！
     *
     * @Author: 17Wang
     * @Time: 14:15 2018/12/23
     */
    public TeamValidApplication getTeamValidRequestByTeamId(long teamId) throws MyException {
        Team team = teamDao.getTeamById(teamId);
        long teacherId = courseDao.getCourseById(team.getCourseId()).getTeacherId();
        return teamApplicationDao.getTeamValidRequestByTeamIdAndTeacherId(teamId, teacherId);
    }

    /**
     * Description: 查看一个老师的所有team的请求
     *
     * @Author: 17Wang
     * @Time: 15:53 2018/12/23
     */
    public List<TeamValidApplication> listTeamApplicationByTeacherId(long teacherId) {
        return teamApplicationDao.getTeamValidRequestByTeacherId(teacherId, TeamApplicationDao.HAS_TEAM);
    }

    /**
     * Description: 新增一条请求
     *
     * @Author: 17Wang
     * @Time: 14:04 2018/12/23
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean addTeamValidApplication(long teamId, String reason) throws Exception {

        TeamValidApplication teamValidApplication = new TeamValidApplication();
        Team team = teamDao.getTeamById(teamId);

        //设置信息
        teamValidApplication.setTeamId(teamId);
        teamValidApplication.setTeacherId(courseDao.getCourseById(team.getCourseId()).getTeacherId());
        teamValidApplication.setReason(reason);
        Long id = teamApplicationDao.addTeamValidApplication(teamValidApplication);

        //小组状态
        if (team.getStatus() != Team.STATUS_IN_REVIEW) {
            teamDao.updateStatusByTeamId(Team.STATUS_IN_REVIEW,
                    teamApplicationDao.getTeamValidRequestById(id).getTeamId());
        }

        return true;
    }

    /**
     * Description: 修改请求的状态
     *
     * @Author: 17Wang
     * @Time: 14:50 2018/12/23
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean updateTeamValidApplicationStatus(long requestId, int status) throws Exception {
        //如果同意，小组状态改变
        if (status == TeamValidApplication.STATUS_AGREE) {
            teamDao.updateStatusByTeamId(Team.STATUS_OK,
                    teamApplicationDao.getTeamValidRequestById(requestId).getTeamId());
        }
        //如果拒绝，小组状态改变
        if (status == TeamValidApplication.STATUS_DISAGREE) {
            teamDao.updateStatusByTeamId(Team.STATUS_ERROR,
                    teamApplicationDao.getTeamValidRequestById(requestId).getTeamId());
        }
        return teamApplicationDao.updateTeamValidApplicationStatusById(requestId, status);
    }

    /**
     * Description: 修改组队共享请求的状态
     *
     * @Author: WinstonDeng
     * @Date: 20:59 2018/12/24
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean updateTeamShareApplicationStatus(long requestId, Integer status) throws Exception {
        //如果同意
        if (status == ShareTeamApplication.STATUS_ACCEPT) {
            //  若请求通过，发出申请的课程为主课程，接受申请的课程为从课程，主课程小组名单映射到从课程中
            //  例如，某小组主课程 A 有5 人，五人中选修从课程 B 的为其中的 3 人，则 B 中此小组为此 3 人组成的小组
            //  若接受共享分组请求，该课程原有分组将被删除，并且，失去发起共享分组、接受其他共享分组请求以及课程中组队的功能
            //1、从课程删掉所有的teamId（team表），调用teamndao的级联删除函数delete
            //
            //  1.删除从课程原有分组
            ShareTeamApplication shareTeamApplication = shareDao.getShareTeamApplicationById(requestId);
            List<Team> subCourseTeams = teamDao.listByCourseId(shareTeamApplication.getSubCourseId());
            for (Team team : subCourseTeams) {
                //删除分组,调用dao层级联删除函数
                teamDao.deleteTeamById(team.getId());
            }

            //  2.从课程小组调整
            //  要确认从课程队伍属于哪个班，要先查klass_student表里courseid和teamid对应学生的klass_id，再通过分班策略
            //  进行区分，建议做成类方法
            List<Team> mainCourseTeams = teamDao.listByCourseId(shareTeamApplication.getMainCourseId());
            for (Team team
                    : mainCourseTeams) {
                teamMapToSubCourse(team, shareTeamApplication.getSubCourseId());
            }
            //  3.建立主从课程映射
            //  从课程使用队伍学生时，要先确认自己是从课程
            //  从课程学生只看klass_student表的course_id klass_id，不看主课程里这两个字段
            //  更新从课程的team_main_course_id字段,建立映射
            courseDao.updateTeamMainCourseId(shareTeamApplication.getSubCourseId(), shareTeamApplication.getMainCourseId());
        }
        //如果拒绝，只更新请求表的字段
        return shareDao.updateShareTeamApplicationStatus(requestId, status);
    }

    /**
     * Description: 把主课程的队伍映射到从课程
     *
     * @Author: WinstonDeng
     * @Date: 1:35 2018/12/29
     */
    public void teamMapToSubCourse(Team team, long subCourseId) throws Exception {
        //主课程学生
        List<Student> mainCourseStudents = studentDao.listByTeamId(team.getId());
        //从课程学生
        List<CClassStudent> subCourseStudents = new ArrayList<>();
        for (Student student
                : mainCourseStudents) {
            //如果这个学生在从课程
            List<CClassStudent> temp = studentDao.getByIdAndCourseId(student.getId(), subCourseId);
            if (temp != null) {
                for (CClassStudent cClassStudent:temp){
                    subCourseStudents.add(cClassStudent);
                }
            }
        }
        //如果有从课程学生
        if (!subCourseStudents.isEmpty()) {
            //通过主课程学生确定从课程学生共享后队伍所在班级
            long cClassIdInSubCourseTeam = getCClassIdByStrategy(subCourseStudents);
            //建立klass_team表的新关系
            teamDao.addCClassTeam(team.getId(), cClassIdInSubCourseTeam);
            //建立从课程seminar_score
            List<CClassSeminar> cClassSeminars = cClassSeminarDao.listByCClassId(cClassIdInSubCourseTeam);
            for (CClassSeminar cClassSeminar : cClassSeminars) {
                seminarScoreDao.addSeminarScore(cClassSeminar.getId(), team.getId());
            }
            //建立从课程round_score
            List<Round> rounds = roundDao.listByCourseId(subCourseId);
            for (Round round : rounds) {
                RoundScore roundScore = new RoundScore();
                roundScore.setRoundId(round.getId());
                roundScore.setTeamId(team.getId());
                roundScoreDao.addRoundScore(roundScore);
            }
        }
    }

    /**
     * Description: 从课程分班策略
     *
     * @Author: WinstonDeng
     * @Date: 15:39 2018/12/25
     */
    public long getCClassIdByStrategy(List<CClassStudent> subCourseStudents) {
        //小组人数
        final int memberNum = subCourseStudents.size();
        //计数器
        Map<Long, Integer> countMap = new HashMap<>();

        //初始化  把第一个学生的的班级存进去
        countMap.put(subCourseStudents.get(0).getcClassId(), 1);
        //统计
        for (int i = 1; i < memberNum; i++) {
            Long key = subCourseStudents.get(i).getcClassId();
            //如果存在 对应计数器+1 ，否则新建
            if (countMap.containsKey(key)) {
                int cnt = countMap.get(key);
                countMap.put(key, cnt + 1);
            } else {
                countMap.put(key, 1);
            }
        }
        //从课程分班策略
        //获得人数最多的班级
        long cClassIdWithMaxMember = subCourseStudents.get(0).getcClassId();
        int maxMember = countMap.get(cClassIdWithMaxMember);
        for (Long key : countMap.keySet()) {
            if (countMap.get(key) > maxMember) {
                cClassIdWithMaxMember = key;
                maxMember = countMap.get(cClassIdWithMaxMember);
            }
        }
        //若从课程有多个班级，从课程小组所在班级由系统按少数服从多数原则
        if (cClassIdWithMaxMember > memberNum / 2) {
            return cClassIdWithMaxMember;
        } else {
            //若出现该小组在不同班级的人数相同的情况，则优先分配到总小组数少的班级
            List<Long> cClassIdQueue = new ArrayList<>();
            for (Map.Entry<Long, Integer> m : countMap.entrySet()) {
                if (m.getValue().equals(maxMember)) {
                    cClassIdQueue.add(m.getKey());
                }
            }
            int teamNum = teamDao.listByCClassId(cClassIdQueue.get(0)).size();
            long resultCClassId = cClassIdQueue.get(0);
            for (int i = 1; i < cClassIdQueue.size(); i++) {
                if (teamDao.listByCClassId(cClassIdQueue.get(i)).size() < teamNum) {
                    resultCClassId = cClassIdQueue.get(i);
                }
            }
            return resultCClassId;
        }


    }

    /**
     * Description: 修改讨论课共享请求状态
     * controller已经把控好输入，此处无需判断
     *
     * @Author: WinstonDeng
     * @Date: 19:07 2018/12/27
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean updateSeminarShareApplicationStatus(long requestId, Integer status) throws Exception {
        //如果同意，创建副本
        if (status == ShareSeminarApplication.STATUS_ACCEPT) {
            ShareSeminarApplication shareSeminarApplication = shareDao.getShareSeminarApplicationById(requestId);
            long mainCourseId = shareSeminarApplication.getMainCourseId();
            long subCourseId = shareSeminarApplication.getSubCourseId();
            //1.删除从课程原有讨论课 调用到dao层联的级联删除
            seminarDao.deleteSeminarByCourseId(subCourseId);
            //2.建立副本
            //  2.1 新建round副本
            //  获取所有主课程轮次
            List<Round> rounds = roundDao.listByCourseId(mainCourseId);
            for (Round round
                    : rounds) {
                //新建 轮次副本
                //除了Id courseId全复制
                Round tempRound = new Round(round);
                tempRound.setCourseId(subCourseId);
                roundDao.addRound(tempRound);
                //新增klass_round
                List<CClass> cClasses = cClassDao.listByCourseId(subCourseId);
                for (CClass cClass : cClasses) {
                    CClassRound cClassRound = new CClassRound();
                    cClassRound.setcClassId(cClass.getId());
                    cClassRound.setRoundId(tempRound.getId());
                    //默认1次
                    cClassRound.setEnrollNumber(CClassRound.DEFAULT_ENROLL_NUM);
                    cClassDao.addCClassRound(cClassRound);
                }
                //新增round_score
                List<Team> teams = teamDao.listByCourseId(subCourseId);
                for (Team team
                        : teams) {
                    RoundScore roundScore = new RoundScore();
                    roundScore.setRoundId(tempRound.getId());
                    roundScore.setTeamId(team.getId());
                    roundScoreDao.addRoundScore(roundScore);
                }
                //  2.2 新建seminar副本
                //获取所有主课程讨论课
                List<Seminar> seminars = seminarDao.listAllSeminarsByRoundId(round.getId());
                for (Seminar seminar
                        : seminars) {
                    //除了id courseId roundId全复制
                    Seminar tempSeminar = new Seminar(seminar);
                    tempSeminar.setCourseId(subCourseId);
                    tempSeminar.setRoundId(tempRound.getId());
                    //TODO 调用seminarService新建业务 级联操作 已完成
                    seminarService.addSemianr(tempSeminar, false);
                }
            }
            //3.创建主从关系，仅表示是从课程，不作为映射关系
            courseDao.updateSeminarMainCourseId(subCourseId, mainCourseId);
        }
        //如果不同意，直接修改
        return shareDao.updateShareSeminarApplicationStatus(requestId, status);
    }


    /**
     * Description: 发起组队共享申请
     *
     * @Author: WinstonDeng
     * @Date: 23:18 2018/12/26
     */
    public boolean addTeamShareRequest(long courseId, long subCourseId) throws MyException {
        return shareDao.addTeamShareApplication(courseId, subCourseId);
    }

    /**
     * Description: 取消队伍共享
     *
     * @Author: WinstonDeng
     * @Date: 23:24 2018/12/26
     */
    public boolean removeTeamShare(long requestId) throws MyException {
        return shareDao.removeTeamShare(requestId);
    }


    /**
     * Description: 发起讨论课共享申请
     *
     * @Author: WinstonDeng
     * @Date: 23:07 2018/12/27
     */
    public boolean addSeminarShareRequest(long courseId, long subCourseId) throws MyException {
        return shareDao.addSeminarShareApplication(courseId, subCourseId);
    }

    /**
     * Description: 取消讨论课共享
     *
     * @Author: WinstonDeng
     * @Date: 23:09 2018/12/27
     */
    public boolean removeSeminarShare(long requestId) throws Exception {
        //先删除讨论课及相关信息 调用seminarService的方法
        ShareSeminarApplication shareSeminarApplication = shareDao.getShareSeminarApplicationById(requestId);
        List<Seminar> seminars = seminarDao.findSeminarByCourseId(shareSeminarApplication.getSubCourseId());
        for (Seminar seminar : seminars) {
            seminarService.removeSeminarById(seminar.getId(), false);
        }
        //调用dao内部再删除轮次
        return shareDao.removeSeminarShare(requestId);
    }

    /**
     * Description: 获取所有待办讨论课共享申请
     *
     * @Author: WinstonDeng
     * @Date: 17:06 2018/12/28
     */
    public List<ShareSeminarApplication> listSeminarShareApplicationByTeacherId(long subTeacherId) throws Exception {
        List<ShareSeminarApplication> shareSeminarApplications = shareDao.listAllShareSeminarApplicationsByTeacherId(subTeacherId, ShareDao.HAS_MAIN_COURSE, ShareDao.HAS_MAIN_COURSE_TEACHER);
        List<ShareSeminarApplication> unHandleList = new ArrayList<>();
        for (ShareSeminarApplication shareSeminarApplication
                : shareSeminarApplications) {
            if (shareSeminarApplication.getStatus() == null) {
                unHandleList.add(shareSeminarApplication);
            }
        }
        return unHandleList;
    }

    /**
     * Description:
     *
     * @Author: WinstonDeng
     * @Date: 17:17 2018/12/28
     */
    public List<ShareTeamApplication> listTeamShareApplicationByTeacherId(long subTeacherId) throws Exception {
        List<ShareTeamApplication> shareTeamApplications = shareDao.listAllShareTeamApplicationsByTeacherId(subTeacherId, ShareDao.HAS_MAIN_COURSE, ShareDao.HAS_MAIN_COURSE_TEACHER);
        List<ShareTeamApplication> unHandleList = new ArrayList<>();
        for (ShareTeamApplication shareTeamApplication
                : shareTeamApplications) {
            if (shareTeamApplication.getStatus() == null) {
                unHandleList.add(shareTeamApplication);
            }
        }
        return unHandleList;
    }
}
