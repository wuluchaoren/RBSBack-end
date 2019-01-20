package com.rbs.project.dao;

import com.rbs.project.exception.MyException;
import com.rbs.project.mapper.*;
import com.rbs.project.mapper.strategy.*;
import com.rbs.project.pojo.entity.Course;
import com.rbs.project.pojo.entity.Student;
import com.rbs.project.pojo.entity.Team;
import com.rbs.project.pojo.strategy.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 11:13 2018/12/19
 */
@Repository
public class TeamDao {
    @Autowired
    private TeamMapper teamMapper;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private CClassMapper cClassMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private TeamStudentMapper teamStudentMapper;

    @Autowired
    private CClassTeamMapper cClassTeamMapper;

    @Autowired
    private RoundScoreMapper roundScoreMapper;

    @Autowired
    private SeminarScoreMapper seminarScoreMapper;

    @Autowired
    private AttendanceMapper attendanceMapper;

    //策略

    @Autowired
    private TeamStrategyMapper teamStrategyMapper;

    @Autowired
    private TeamOrStrategyMapper teamOrStrategyMapper;

    @Autowired
    private TeamAndStrategyMapper teamAndStrategyMapper;

    @Autowired
    private MemberLimitStrategyMapper memberLimitStrategyMapper;

    @Autowired
    private CourseMemberLimitStrategyMapper courseMemberLimitStrategyMapper;

    @Autowired
    private ConflictCourseStrategyMapper conflictCourseStrategyMapper;

    public static final int HAS_COURSE = 0;
    public static final int HAS_CCLASS = 1;
    public static final int HAS_LEADER = 2;
    public static final int HAS_MEMBERS = 3;

    private void hasSomethingFun(Team team, int... hasSomething) {
        for (int i : hasSomething) {
            if (i == HAS_COURSE) {
                team.setCourse(courseMapper.findById(team.getCourseId()));
            }
            if (i == HAS_CCLASS) {
                team.setcClass(cClassMapper.findById(team.getcClassId()));
            }
            if (i == HAS_LEADER) {
                team.setLeader(studentMapper.findById(team.getLeaderId()));
            }
            if (i == HAS_MEMBERS) {
                team.setStudents(studentMapper.findByTeamId(team.getId()));
            }
        }
    }

    /**
     * Description: 通过teamid返回一个team
     *
     * @Author: 17Wang
     * @Time: 22:22 2018/12/19
     */
    public Team getTeamById(long teamId, int... hasSomething) throws MyException {
        Team team = teamMapper.findById(teamId);
        if (team == null) {
            throw new MyException("获取队伍错误！找不到该队伍", MyException.NOT_FOUND_ERROR);
        }
        hasSomethingFun(team, hasSomething);
        return team;
    }

    /**
     * Description: 通过leaderId返回一个team
     *
     * @Author: 17Wang
     * @Time: 21:02 2018/12/23
     */
    public Team getTeamByLeaderId(long leaderId, int... hasSomething) throws MyException {
        Team team = teamMapper.findByLeaderId(leaderId);
        hasSomethingFun(team, hasSomething);
        return team;
    }

    /**
     * Description: 新建队伍 在这个方法同时新增klass_team
     *
     * @Author: 17Wang
     * @Time: 9:06 2018/12/20
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean addTeam(Team team) throws Exception {
        if (!teamMapper.insertTeam(team)) {
            throw new MyException("新建队伍出错！Team数据库处理错误", MyException.ERROR);
        }
        if (!cClassTeamMapper.insertBycClassIdAndTeamId(team.getcClassId(), team.getId())) {
            throw new MyException("新建队伍出错！KlassTeam数据库处理错误", MyException.ERROR);
        }
        return true;
    }

    /**
     * Description:
     *
     * @Author: 17
     * @Date: 15:48 2018/12/23
     */
    public Team getTeamBycClassIdAndStudentId(long cClassId, long studentId, int... hasSomething) throws MyException {
        Team team = teamMapper.getTeamBycClassIdAndStudentId(cClassId, studentId);
        if (team == null) {
            throw new MyException("该学生在该班级下无小组", MyException.NOT_FOUND_ERROR);
        }
        hasSomethingFun(team, hasSomething);
        return team;
    }

    /**
     * Description: 获取一个班级下的所有队伍
     *
     * @Author: 17Wang
     * @Time: 9:39 2018/12/20
     */
    public List<Team> listByCClassId(long cClassId, int... hasSomething) {
        List<Team> teams = teamMapper.findByCClassId(cClassId);
        for (Team team : teams) {
            hasSomethingFun(team, hasSomething);
        }
        return teams;
    }

    /**
     * Description: 通过课程id查看队伍列表（主从都可以）
     *
     * @Author: WinstonDeng
     * @Date: 21:24 2018/12/22
     */
    public List<Team> listByCourseId(long courseId, int... hasSomething) throws MyException {
        List<Team> teams = teamMapper.findByCourseId(courseId);
        if (teams == null) {
            throw new MyException("查看队伍错误！该记录不存在", MyException.NOT_FOUND_ERROR);
        }
        for (Team team : teams) {
            hasSomethingFun(team, hasSomething);
        }
        return teams;
    }

    /**
     * Description:
     * （已做）删除小组 删team表 删klass_team表 删team_student
     * （已做）成绩：round_score seminar_score
     *
     * @Author: 17Wang
     * @Time: 10:59 2018/12/23
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteTeamById(long teamId) throws MyException {
        //将该小组下的成员置为无小组状态
        //通过删除team_student表，解除team和student的关系
        List<Student> students = studentMapper.findByTeamId(teamId);
        for (Student student : students) {
            deleteTeamStudentByTeamIdAndStudentId(teamId, student.getId());
        }
        //检查是否有该行
        Team team = getTeamById(teamId);
        if (!teamMapper.deleteById(teamId)) {
            throw new MyException("删除小组错误！数据库处理错误", MyException.ERROR);
        }
        //直接删掉所有关于team的东西
        cClassTeamMapper.deleteByTeamId(teamId);
        //删除attendance
        attendanceMapper.deleteByTeamId(teamId);
        //删除成绩
        roundScoreMapper.deleteByTeamId(teamId);
        seminarScoreMapper.deleteByTeamId(teamId);
        return true;
    }

    /**
     * Description: 修改小组状态
     *
     * @Author: 17Wang
     * @Time: 15:20 2018/12/23
     */
    public boolean updateStatusByTeamId(int status, long teamId) throws Exception {
        getTeamById(teamId);
        if (!teamMapper.updateStatusById(status, teamId)) {
            throw new MyException("修改小组状态错误！数据库处理错误", MyException.ERROR);
        }
        return true;
    }

    /**
     * Description: 新增teamStudent表字段
     *
     * @Author: 17Wang
     * @Time: 22:52 2018/12/25
     */
    public boolean addTeamStudentByTeamIdAndStudentId(long teamId, long studentId) throws Exception {
        if (!teamStudentMapper.insertByTeamIdAndStudentId(teamId, studentId)) {
            throw new MyException("新增teamStudent表字段错误！数据库处理错误", MyException.ERROR);
        }
        return true;
    }

    /**
     * Description: 删除teamStudent表字段
     *
     * @Author: 17Wang
     * @Time: 23:43 2018/12/25
     */
    public boolean deleteTeamStudentByTeamIdAndStudentId(long teamId, long studentId) throws MyException {
        return teamStudentMapper.deleteByTeamIdAndStudentId(teamId, studentId);
    }

    /**
     * Description: 新增klass_team表
     *
     * @Author: WinstonDeng
     * @Date: 16:34 2018/12/26
     */
    public boolean addCClassTeam(long teamId, long cClassId) throws Exception {
        if (!cClassTeamMapper.insertBycClassIdAndTeamId(cClassId, teamId)) {
            throw new MyException("新增klass_team关系错误！数据库处理错误", MyException.ERROR);
        }
        return true;
    }

    //=============================================究极策略递归============================================//

    /**
     * Description: 存TeamId 直到递归到最底层
     *
     * @Author: 17Wang
     * @Time: 0:59 2018/12/27
     */
    private boolean judgeTeam(String strategyName, long strategyId, long teamId) {
        boolean flag = false;
        switch (strategyName) {
            case "TeamAndStrategy":
                flag = myTeamAndStrategy(strategyId, teamId);
                break;
            case "TeamOrStrategy":
                flag = myTeamOrStrategy(strategyId, teamId);
                break;
            case "ConflictCourseStrategy":
                flag = myConflictCourseStrategy(strategyId, teamId);
                break;
            case "MemberLimitStrategy":
                flag = myMemberLimitStrategy(strategyId, teamId);
                break;
            case "CourseMemberLimitStrategy":
                flag = myCourseMemberLimitStrategy(strategyId, teamId);
                break;
            default:
                ;
        }
        return flag;
    }

    /**
     * Description:team策略
     *
     * @Author: 17Wang
     * @Time: 0:44 2018/12/27
     */
    public boolean teamStrategy(long teamId) {
        //获取当前team的信息
        Team team = teamMapper.findById(teamId);
        //获取所有的策略
        List<TeamStrategy> teamStrategies = teamStrategyMapper.findByCourseId(team.getCourseId());
        for (TeamStrategy teamStrategy : teamStrategies) {
            //如果一个为false，直接返回false
            if (!judgeTeam(teamStrategy.getStrategyName(), teamStrategy.getStrategyId(), teamId)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Description:TeamAndStrategy
     * 有一个错误就返回false
     *
     * @Author: 17Wang
     * @Time: 0:44 2018/12/27
     */
    private boolean myTeamAndStrategy(long strategyId, long teamId) {
        // 和 策略
        List<TeamAndStrategy> teamAndStrategies = teamAndStrategyMapper.findById(strategyId);
        for (TeamAndStrategy teamAndStrategy : teamAndStrategies) {
            //如果一个为false，直接返回false
            if (!judgeTeam(teamAndStrategy.getStrategyName(), teamAndStrategy.getStrategyId(), teamId)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Description:TeamOrStrategy
     * 有一个正确就返回true
     *
     * @Author: 17Wang
     * @Time: 0:44 2018/12/27
     */
    private boolean myTeamOrStrategy(long strategyId, long teamId) {
        // 或 策略 如果有一个正确了就可以
        List<TeamOrStrategy> teamOrStrategies = teamOrStrategyMapper.findById(strategyId);
        for (TeamOrStrategy teamOrStrategy : teamOrStrategies) {
            //如果一个为false，直接返回false
            if (judgeTeam(teamOrStrategy.getStrategyName(), teamOrStrategy.getStrategyId(), teamId)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Description:ConflictCourseStrategy
     *
     * @Author: 17Wang
     * @Time: 0:44 2018/12/27
     */
    private boolean myConflictCourseStrategy(long strategyId, long teamId) {
        //冲突课程策略
        List<ConflictCourseStrategy> conflictCourseStrategies = conflictCourseStrategyMapper.findById(strategyId);
        //第几个冲突课程策略
        Map<Long, Boolean> hasCourseId = new HashMap<>();
        //拿出队伍信息
        Team team = null;
        try {
            team = getTeamById(teamId, HAS_MEMBERS);
        } catch (MyException e) {
            e.printStackTrace();
        }
        for (ConflictCourseStrategy conflictCourseStrategy : conflictCourseStrategies) {
            //TODO 判断冲突课程的策略
            //hasThisCourse如果为true，说明这个队伍下有成员有该 冲突课程组 中一个课程
            boolean hasThisCourse = false;
            long courseId = conflictCourseStrategy.getCourseId();
            //如果不存在 就报错
            //判断Team的状态
            for (Student student : team.getStudents()) {
                //学生属于哪些课程
                List<Course> courses = courseMapper.findByStudentId(student.getId());
                for (Course course : courses) {
                    //存在，跳出
                    if (courseId == course.getId()) {
                        hasCourseId.put(courseId, true);
                        hasThisCourse = true;
                        break;
                    }
                }
                //跳出遍历学生
                if (hasThisCourse) {
                    break;
                }
            }
        }
        if (hasCourseId.size() > 1) {
            return false;
        }

        return true;
    }

    /**
     * Description:MemberLimitStrategy
     *
     * @Author: 17Wang
     * @Time: 0:44 2018/12/27
     */
    private boolean myMemberLimitStrategy(long strategyId, long teamId) {
        //队伍成员限制策略
        MemberLimitStrategy memberLimitStrategy = memberLimitStrategyMapper.findById(strategyId);
        //拿到team的member信息
        Team team = null;
        try {
            team = getTeamById(teamId, HAS_MEMBERS);
        } catch (MyException e) {
            e.printStackTrace();
        }
        //如果有一个不符合就不行
        if (team.getStudents().size() < memberLimitStrategy.getMinMember() ||
                team.getStudents().size() > memberLimitStrategy.getMaxMember()) {
            return false;
        }


        return true;
    }

    /**
     * Description:CourseMemberLimitStrategy
     *
     * @Author: 17Wang
     * @Time: 0:44 2018/12/27
     */
    private boolean myCourseMemberLimitStrategy(long strategyId, long teamId) {
        //获取team的member信息
        Team team = null;
        try {
            team = getTeamById(teamId, HAS_MEMBERS);
        } catch (MyException e) {
            e.printStackTrace();
        }
        CourseMemberLimitStrategy courseMemberLimitStrategy = courseMemberLimitStrategyMapper.findById(strategyId);
        //获取一个 课程人数限制策略组 的策略的course是哪一个
        Long courseId = courseMemberLimitStrategy.getCourseId();
        int teamMemberCount = 0;

        //找到这个Team的所有学生，每个学生找到所有的course信息，如果这个学生存在courseId的课程，teamMemberCount++,break+break（两段break）
        List<Student> students = studentMapper.findByTeamId(teamId);
        for (Student student : students) {
            List<Course> courses = courseMapper.findByStudentId(student.getId());
            for (Course course : courses) {
                if (course.getId() == courseId) {
                    teamMemberCount++;
                    break;
                }
            }
        }
        //如果有一个不符合返回false
        if (teamMemberCount < courseMemberLimitStrategy.getMinMember() ||
                teamMemberCount > courseMemberLimitStrategy.getMaxMember()) {
            return false;
        }


        return true;
    }

    /**
     * Description: TODO ！！！！！FBI警告 ！！！！！
     * 判断当前小组是否在klass_team中，用于共享小组共享删除后，对其参与的attendence score等
     * 存在性的判断
     *
     * @Author: WinstonDeng
     * @Date: 17:26 2018/12/27
     */
    public boolean isExistInCClassTeam(long teamId) {
        if (!cClassTeamMapper.findByTeamId(teamId).isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

}
