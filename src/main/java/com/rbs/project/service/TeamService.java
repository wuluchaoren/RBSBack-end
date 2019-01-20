package com.rbs.project.service;

import com.rbs.project.dao.*;
import com.rbs.project.exception.MyException;
import com.rbs.project.pojo.entity.*;
import com.rbs.project.pojo.relationship.CClassStudent;
import com.rbs.project.utils.LogicUtils;
import com.rbs.project.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 11:13 2018/12/19
 */
@Service
public class TeamService {
    @Autowired
    private TeamDao teamDao;

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private CClassDao cClassDao;

    @Autowired
    private CClassSeminarDao cClassSeminarDao;

    @Autowired
    private RoundScoreDao roundScoreDao;

    @Autowired
    private RoundDao roundDao;

    @Autowired
    private SeminarScoreDao seminarScoreDao;

    @Autowired
    private CourseDao courseDao;

    @Autowired
    private ApplicationService applicationService;

    /**
     * Description: 新建一个Team
     *
     * @Author: 17Wang
     * @Time: 11:53 2018/12/19
     */
    @Transactional(rollbackFor = Exception.class)
    public Long createTeam(Team team) throws Exception {
        //如果出错，说明这个leader已经在这个课程下有小组
        Team myTeam;
        try {
            myTeam = teamDao.getTeamBycClassIdAndStudentId(team.getcClassId(), team.getLeaderId());
        } catch (Exception e) {
            myTeam = null;
        }
        if (myTeam != null) {
            throw new MyException("创建小组出错！这个人已经在这个课程下加入了一个小组", MyException.ERROR);
        }

        for (Student student : team.getStudents()) {
            //如果出错，说明有成员已经在这个课程下有了小组
            try {
                myTeam = teamDao.getTeamBycClassIdAndStudentId(team.getcClassId(), student.getId());
            } catch (Exception e) {
                myTeam = null;
            }
            if (myTeam != null) {
                throw new MyException("添加成员出错！学生" + student.getId() + "已有队伍", MyException.ERROR);
            }
        }
        //team Serial
        //已改连表，标识一下，做到这里了
        List<Team> teams = teamDao.listByCClassId(team.getcClassId());

        int teamSerial = 1;
        for (; ; teamSerial++) {
            boolean flag = false;
            for (Team existTeam : teams) {
                if (existTeam.getSerial() == teamSerial) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                break;
            }
        }
        team.setSerial(teamSerial);
        //默认小组状态为不合法
        team.setStatus(Team.STATUS_ERROR);

        //插入 team表
        //在这个方法同时插入到klass_team中
        teamDao.addTeam(team);

        //TODO 建立小组轮次关系round_score 已完成
        //获取这个小组属于的课程下的所有轮次
        RoundScore roundScore = new RoundScore();
        roundScore.setTeamId(team.getId());
        for (Round round : roundDao.listByCourseId(team.getCourseId())) {
            roundScore.setRoundId(round.getId());
            roundScoreDao.addRoundScore(roundScore);
        }


        //TODO 建立小组讨论课关系seminar_score 已完成
        List<CClassSeminar> cClassSeminars = cClassSeminarDao.listByCourseId(team.getCourseId());
        for (CClassSeminar cClassSeminar : cClassSeminars) {
            seminarScoreDao.addSeminarScore(cClassSeminar.getId(), team.getId());
        }


        //判断队伍是否合法
        if (teamDao.teamStrategy(team.getId())) {
            teamDao.updateStatusByTeamId(Team.STATUS_OK, team.getId());
        } else {
            teamDao.updateStatusByTeamId(Team.STATUS_ERROR, team.getId());
        }

        //新增team_student
        for (Student student : team.getStudents()) {
            teamDao.addTeamStudentByTeamIdAndStudentId(team.getId(), student.getId());
        }
        teamDao.addTeamStudentByTeamIdAndStudentId(team.getId(), team.getLeaderId());

        //如果有从课程，同步team
        //从课程
        List<Course> courses=courseDao.listAllCoursesByTeamMainCourseId(team.getCourseId());

        for(Course course:courses){
            applicationService.teamMapToSubCourse(team,course.getId());
        }
        //返回team的id
        return team.getId();
    }

    /**
     * Description: 通过teamId锁定一个team
     *
     * @Author: 17Wang
     * @Time: 22:00 2018/12/19
     */
    public Team getTeamById(long teamId, int... hasSomething) throws MyException {
        if (hasSomething.length == 0) {
            Team team = teamDao.getTeamById(teamId,
                    TeamDao.HAS_COURSE,
                    TeamDao.HAS_CCLASS,
                    TeamDao.HAS_LEADER,
                    TeamDao.HAS_MEMBERS);
            if (team.getCourse() == null) {
                team.setCourse(new Course());
            }
            if (team.getcClass() == null) {
                team.setcClass(new CClass());
            }
            if (team.getLeader() == null) {
                team.setLeader(new Student());
            }
            if (team.getStudents() == null) {
                team.setStudents(new ArrayList<>());
            }
            return team;
        }
        return teamDao.getTeamById(teamId, hasSomething);
    }

    /**
     * Description:获取课程下的所有小组（主从课程）
     *
     * @Author: 17Wang
     * @Time: 11:38 2018/12/23
     */
    public List<Team> listTeamByCourseId(long courseId) throws MyException {
        return teamDao.listByCourseId(courseId, TeamDao.HAS_CCLASS);
    }

    /**
     * Description: 查找我在这门课下的队伍
     * 搞定！ 通过courseid和我的id查找我在哪个班级下面，再通过班级号和学号查找队伍信息
     *
     * @Author: 17Wang
     * @Time: 11:48 2018/12/23
     */
    public Team getTeamByCourseIdAndStudentId(long courseId) throws MyException {
        Student nowStudent = (Student) UserUtils.getNowUser();
        CClass cClass = cClassDao.getCClassByStudentIdAndCourseId(nowStudent.getId(), courseId);

        Team team = teamDao.getTeamBycClassIdAndStudentId(cClass.getId(), nowStudent.getId(),
                TeamDao.HAS_COURSE,
                TeamDao.HAS_CCLASS,
                TeamDao.HAS_LEADER,
                TeamDao.HAS_MEMBERS);
        if (team.getCourse() == null) {
            team.setCourse(new Course());
        }
        if (team.getcClass() == null) {
            team.setcClass(new CClass());
        }
        if (team.getLeader() == null) {
            team.setLeader(new Student());
        }
        if (team.getStudents() == null) {
            team.setStudents(new ArrayList<>());
        }
        return team;
    }

    /**
     * Description: 添加成员，修改team_student就可以了
     *
     * @Author: 17Wang
     * @Time: 23:08 2018/12/19
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean addMemberToTeam(long teamId, List<Long> membersIds) throws Exception {
        if (membersIds.isEmpty()) {
            throw new MyException("没有需要添加的成员或者参数名写错了，是studentId哦", MyException.ID_FORMAT_ERROR);
        }
        for (Long memberId : membersIds) {
            ///如果没有该学生
            Student student = studentDao.getStudentById(memberId);
            //获取team信息，主要是为了拿classId
            //因为只有主课程能修改team，所以拿team表里的class没有任何问题
            Team team = teamDao.getTeamById(teamId);
            if (team.getStatus() == Team.STATUS_IN_REVIEW) {
                throw new MyException("小队还在申请中！不能添加成员", MyException.AUTHORIZATION_ERROR);
            }
            //如果该学生已有队伍
            Team tempTeam = null;
            try {
                tempTeam = teamDao.getTeamBycClassIdAndStudentId(team.getcClassId(), memberId);
            } catch (Exception e) {
            }
            if (tempTeam != null) {
                throw new MyException("成员" + student.getStudentName() + "已有队伍", MyException.AUTHORIZATION_ERROR);
            }
            teamDao.addTeamStudentByTeamIdAndStudentId(teamId, memberId);
        }
        //小组状态判断和修改
        Team team = teamDao.getTeamById(teamId, TeamDao.HAS_MEMBERS);

        //判断队伍是否合法
        if (teamDao.teamStrategy(team.getId())) {
            teamDao.updateStatusByTeamId(Team.STATUS_OK, team.getId());
        } else {
            teamDao.updateStatusByTeamId(Team.STATUS_ERROR, team.getId());
        }

        return true;
    }

    /**
     * Description: 删除成员
     *
     * @Author: 17Wang
     * @Time: 9:12 2018/12/20
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean removeMemberFromTeam(long teamId, long memberId) throws Exception {
        //自己不能踢出自己
        Team myTeam = teamDao.getTeamById(teamId);
        //审核中的队伍不能删除成员
        if (myTeam.getStatus() == Team.STATUS_IN_REVIEW) {
            throw new MyException("小队还在申请中！不能删除成员", MyException.AUTHORIZATION_ERROR);
        }
        if (myTeam.getLeaderId() == memberId) {
            throw new MyException("自己不能踢出自己么么", MyException.AUTHORIZATION_ERROR);
        }
        //上述判断通过，将该成员踢出
        teamDao.deleteTeamStudentByTeamIdAndStudentId(teamId, memberId);

        //小组状态判断和修改
        Team team = teamDao.getTeamById(teamId, TeamDao.HAS_MEMBERS);

        //判断队伍是否合法
        if (teamDao.teamStrategy(team.getId())) {
            teamDao.updateStatusByTeamId(Team.STATUS_OK, team.getId());
        } else {
            teamDao.updateStatusByTeamId(Team.STATUS_ERROR, team.getId());
        }

        return true;
    }

    /**
     * Description: 解散小组
     *
     * @Author: 17Wang
     * @Time: 10:45 2018/12/23
     */
    public boolean dissolveTeam(long teamId) throws Exception {
        Team team = teamDao.getTeamById(teamId);
        //审核中的队伍不能删除成员
        if (team.getStatus() == Team.STATUS_IN_REVIEW) {
            throw new MyException("小队还在申请中！不能解散小组", MyException.AUTHORIZATION_ERROR);
        }
        //删除这个小组
        teamDao.deleteTeamById(teamId);
        return true;
    }

    /**
     * Description: 退出小组
     *
     * @Author: 17Wang
     * @Time: 22:01 2018/12/27
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean quitTeam(long teamId) throws Exception {
        //获取当前登录用户
        Student user = (Student) UserUtils.getNowUser();
        //获取登录用户的队伍信息
        Team team = teamDao.getTeamById(teamId, TeamDao.HAS_MEMBERS);
        //审核中的队伍不能删除成员
        if (team.getStatus() == Team.STATUS_IN_REVIEW) {
            throw new MyException("小队还在申请中！不能退出小组", MyException.AUTHORIZATION_ERROR);
        }
        boolean flag = false;
        for (Student student : team.getStudents()) {
            if (student.getId() == user.getId()) {
                flag = true;
            }
        }
        if (!flag) {
            throw new MyException("你已经退出了该小组", MyException.NOT_FOUND_ERROR);
        }
        //判断当前登录用户是否是队长
        if (user.getId() == team.getLeaderId()) {
            throw new MyException("队长不能退出队伍！只能解散！么么", MyException.AUTHORIZATION_ERROR);
        }
        //删除team_student关系表，解除关系
        try {
            teamDao.deleteTeamStudentByTeamIdAndStudentId(teamId, user.getId());
        } catch (Exception e) {
            throw new MyException("退出队伍失败！不知名Service层原因", MyException.AUTHORIZATION_ERROR);
        }
        //更新当前小组状态
        //判断队伍是否合法
        if (teamDao.teamStrategy(team.getId())) {
            teamDao.updateStatusByTeamId(Team.STATUS_OK, team.getId());
        } else {
            teamDao.updateStatusByTeamId(Team.STATUS_ERROR, team.getId());
        }
        return true;
    }
}
