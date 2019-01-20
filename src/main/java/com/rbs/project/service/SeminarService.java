package com.rbs.project.service;

import com.rbs.project.dao.*;
import com.rbs.project.exception.MyException;
import com.rbs.project.pojo.entity.*;
import com.rbs.project.pojo.relationship.CClassRound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: WinstonDeng
 * @Description: OOAD_Course_ManageSystem
 * <p>
 * !!!!!!!!!!!!!!!!!!!!!!!!!!    增删改前先查出来，检查对应字段，防止脏数据，做到同步    !!!!!!!!!!!!!!!!!!!!!!!!!!!!
 * @Date: Created in 11:46 2018/12/18
 * @Modified by:
 */
@Service
public class SeminarService {
    @Autowired
    private SeminarDao seminarDao;

    @Autowired
    private RoundDao roundDao;

    @Autowired
    private CClassDao cClassDao;

    @Autowired
    private CClassSeminarDao cClassSeminarDao;

    @Autowired
    private TeamDao teamDao;

    @Autowired
    private RoundScoreDao roundScoreDao;

    @Autowired
    private SeminarScoreDao seminarScoreDao;

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private EmailService emailService;

    @Autowired
    private CourseDao courseDao;

    @Autowired
    private RoundService roundService;

    private final static int ADD_SEMINAR=0;
    private final static int UPDATE_SEMINAR=1;


    /**
     * Description: 新建讨论课
     *
     * @Author: WinstonDeng
     * @Date: 16:25 2018/12/18
     */
    @Transactional(rollbackFor = Exception.class)
    public long addSemianr(Seminar seminar, boolean hasEmail) throws Exception {
        //初始化新增讨论课id
        long createSeminarId = -1;
        //是否新建了轮次
        boolean flagRound=false;
        //如果轮次为空，则新建一个轮次，级联修改
        //判断为null太傻吊。接进来数据null的set不进来，沙雕转换为-1，就不是空了，这里再判断，自己难为自己
        if (seminar.getRoundId() == -1) {
            seminar = addRoundBussiness(seminar);
            flagRound=true;
        }
        //其他判空
        if (seminar.getName() == null) {
            throw new MyException("name不能为空", MyException.ERROR);
        }
        if (seminar.getMaxTeam() == null) {
            throw new MyException("maxTeam不能为空", MyException.ERROR);
        }
        if (seminar.getVisible() == null) {
            throw new MyException("visible不能为空", MyException.ERROR);
        }
        //新增的讨论课序号
        int serial = 1;
        //判断序号是否存在
        List<Seminar> seminars = seminarDao.findSeminarByCourseId(seminar.getCourseId());
        List<Integer> serialList = new ArrayList<>();
        for (Seminar temp
                : seminars) {
            serialList.add(temp.getSerial());
        }
        //若该序号已存在，则+1，直到发现不存在的值，作为讨论课序号
        while (serialList.contains(serial)) {
            serial++;
        }
        seminar.setSerial(serial);
        //1. 新增讨论课
        seminarDao.addSeminar(seminar);
        //2. 新增班级讨论课 TODO 级联新增seminar_score 已完成
        cClassSeminarDao.addCClassSeminar(seminar);
        if (hasEmail) {
            //发邮件通知课程下所有班级所有小组成员
            String message = "第" + seminar.getSerial() + "节讨论课:" + seminar.getName() + "已发布，请注意查看！";
            sendSemianrEmail(seminar, message);
        }

        createSeminarId = seminar.getId();
        //找到以当前课程为主课程的所有从课程
        if(!courseDao.listAllCoursesBySeminarMainCourseId(seminar.getCourseId()).isEmpty()){
            //如果有从课程，则同步更新
            seminarCopyToSubCourse(seminar,flagRound);
        }
        //获得主键
        return createSeminarId;
    }

    /**
     * Description: 按id修改讨论课
     *
     * @Author: WinstonDeng
     * @Date: 16:29 2018/12/18
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean updateSeminar(Seminar seminar) throws Exception {
        //是否新建了轮次
        boolean flagRound=false;
        //如果轮次为空，则新建一个轮次，级联修改
        if ( seminar.getRoundId() == -1) {
            seminar = addRoundBussiness(seminar);
            flagRound=true;
        }
        //其他判空
        if ((Long) seminar.getCourseId() == null) {
            throw new MyException("courseId不能为空", MyException.ERROR);
        }
        if (seminar.getName() == null) {
            throw new MyException("name不能为空", MyException.ERROR);
        }
        if (seminar.getMaxTeam() == null) {
            throw new MyException("maxTeam不能为空", MyException.ERROR);
        }
        if (seminar.getVisible() == null) {
            throw new MyException("visible不能为空", MyException.ERROR);
        }
        //发邮件通知课程下所有班级所有小组成员
        String message = "第" + seminar.getSerial() + "节讨论课:" + seminar.getName() + "已修改，请注意查看！";
        sendSemianrEmail(seminar, message);
        return seminarDao.updateSeminarById(seminar);
    }

    /**
     * Description: 按id删除讨论课
     *
     * @Author: WinstonDeng
     * @Date: 16:29 2018/12/18
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean removeSeminarById(long seminarId, boolean hasEmail) throws Exception {
        if ((Long) seminarId == null) {
            throw new MyException("seminarId不能为空", MyException.ERROR);
        }
        Seminar seminar = seminarDao.findSeminarById(seminarId);
        //级联删除
        List<CClassSeminar> cClassSeminars = cClassSeminarDao.findBySeminarId(seminarId);
        for (CClassSeminar cClassSeminar
                : cClassSeminars) {
            // 1. 删除seminar_score
            seminarScoreDao.deleteSeminarScoreByCClassSeminarId(cClassSeminar.getId());
        }
        // 2. 删除班级讨论课
        seminarDao.removeCClassSeminarBySeminarId(seminarId);
        // 3. 删除讨论课
        seminarDao.removeSeminarById(seminarId);
        if (hasEmail) {
            //发邮件通知课程下所有班级所有小组成员
            String message = "第" + seminar.getSerial() + "节讨论课:" + seminar.getName() + "已删除，请注意查看！";
            sendSemianrEmail(seminar, message);
        }
        //如果有从课程，同步更新
        List<Course> courses=courseDao.listAllCoursesBySeminarMainCourseId(seminar.getCourseId());
        for(Course course
                :courses){
            List<Seminar> seminars=seminarDao.findSeminarByCourseId(course.getId());
            for (Seminar temp:seminars){
                if(seminar.getSerial().equals(temp.getSerial())){
                    removeSeminarById(temp.getId(),true);
                }
            }
        }
        return true;
    }

    /**
     * Description: 按id查找讨论课
     *
     * @Author: WinstonDeng
     * @Date: 20:50 2018/12/20
     */
    public Seminar getSeminarById(long seminarId) throws MyException {
        return seminarDao.findSeminarById(seminarId, SeminarDao.HAS_ROUND, SeminarDao.HAS_COURSE);
    }

    /**
     * Description: 获取一个轮次下的所有讨论课
     *
     * @Author: 17Wang
     * @Time: 6:09 2018/12/29
     */
    public List<Seminar> listSeminarByRoundId(long roundId) {
        return seminarDao.listAllSeminarsByRoundId(roundId);
    }

    /**
     * Description: 【私有】 新建轮次的业务逻辑方法
     * 1.新建round表记录
     * 2.新建klass_round表记录
     * 3.新建round_score记录
     *
     * @Author: WinstonDeng
     * @Date: 16:10 2018/12/20
     */
    private Seminar addRoundBussiness(Seminar seminar) throws Exception {
        //      1.新增round记录
        //新建一个轮次
        Round round = new Round();
        //查找当前课程下的所有轮次，确认轮次次序
        int roundNum = roundDao.listByCourseId(seminar.getCourseId()).size();
        //新增的轮次次序+1
        round.setSerial(roundNum + 1);
        round.setCourseId(seminar.getCourseId());
        //设置其他默认值
        round.setPresentationScoreMethod(Round.SCORE_AVERAGE);
        round.setReportScoreMethod(Round.SCORE_AVERAGE);
        round.setQuestionScoreMethod(Round.SCORE_AVERAGE);
        long roundId = roundDao.addRound(round);
        round.setId(roundId);
        //      2.新增klass_round记录
        //与新建的seminar建立关系
        seminar.setRound(round);
        seminar.setRoundId(round.getId());
        //对该课程下的所有班级，新建klass_round记录，并设置其中enrollNumber字段
        List<CClass> cClasses = cClassDao.listByCourseId(seminar.getCourseId());
        for (CClass cClass : cClasses) {
            CClassRound cClassRound = new CClassRound();
            cClassRound.setcClassId(cClass.getId());
            cClassRound.setRoundId(seminar.getRoundId());
            //默认1次
            cClassRound.setEnrollNumber(CClassRound.DEFAULT_ENROLL_NUM);
            cClassDao.addCClassRound(cClassRound);
        }
        //      3.新增round_score记录
        //查找班级下的所有队伍
        List<Team> teams = teamDao.listByCourseId(seminar.getCourseId());
        //插入记录
        for (Team team
                : teams) {
            RoundScore roundScore = new RoundScore();
            roundScore.setRoundId(round.getId());
            roundScore.setTeamId(team.getId());
            roundScoreDao.addRoundScore(roundScore);
        }
        return seminar;
    }

    /**
     * Description: 按courseId获得讨论课列表
     *
     * @Author: WinstonDeng
     * @Date: 18:55 2018/12/21
     */
    public List<Seminar> getSeminarsByCourseId(long courseId) throws MyException {
        return seminarDao.findSeminarByCourseId(courseId, SeminarDao.HAS_CClASS_SEMINAR, SeminarDao.HAS_ROUND);
    }

    /**
     * Description: 发讨论课相关邮件
     *
     * @Author: WinstonDeng
     * @Date: 1:39 2018/12/28
     */
    private void sendSemianrEmail(Seminar seminar, String message) throws Exception {
        List<Team> teams = teamDao.listByCourseId(seminar.getCourseId());
        for (Team team
                : teams) {
            List<Student> students = studentDao.listByTeamId(team.getId());
            for (Student student
                    :students){
                if(student.getEmail()!=null){
                    emailService.sendEmail(new String[]{student.getEmail()},message);
                }
            }
        }
    }

    /**
     * Description: 【私有】共享同步业务逻辑
     * @Author: WinstonDeng
     * @Date: 0:29 2018/12/29
     */
    private void seminarCopyToSubCourse(Seminar seminar,boolean flagRound) throws Exception {
        List<Course> courses=courseDao.listAllCoursesBySeminarMainCourseId(seminar.getCourseId());
        for(Course course
                :courses){
            //创建讨论课副本
            seminar.setCourseId(course.getId());
            Round roundCopyToSubCourse=roundDao.findById(seminar.getRoundId());
            roundCopyToSubCourse.setCourseId(course.getId());
            if(flagRound){
                //新建轮次
                roundCopyToSubCourse.setId(0);
                roundDao.addRound(roundCopyToSubCourse);
                //新建班级轮次
                List<CClass> cClasses=cClassDao.listByCourseId(course.getId());
                for(CClass cClass
                        :cClasses){
                    List<Team> teams=teamDao.listByCClassId(cClass.getId());
                    //新建班级轮次
                    CClassRound cClassRound=new CClassRound();
                    cClassRound.setcClassId(cClass.getId());
                    cClassRound.setRoundId(roundCopyToSubCourse.getId());
                    cClassRound.setEnrollNumber(CClassRound.DEFAULT_ENROLL_NUM);
                    cClassDao.addCClassRound(cClassRound);
                    //新建轮次成绩
                    for(Team team:teams){
                        RoundScore roundScore=new RoundScore();
                        roundScore.setRoundId(roundCopyToSubCourse.getId());
                        roundScore.setTeamId(team.getId());
                        roundScoreDao.addRoundScore(roundScore);
                    }
                }
            }else {
                roundCopyToSubCourse.setId(roundDao.getByCourseIdAndSerial(course.getId(),roundCopyToSubCourse.getSerial()).getId());
            }
            seminar.setRoundId(roundCopyToSubCourse.getId());
            //新建讨论课
            addSemianr(seminar,true);
        }
    }
}
