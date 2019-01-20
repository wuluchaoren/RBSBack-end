package com.rbs.project.service;

import com.rbs.project.dao.*;
import com.rbs.project.exception.MyException;
import com.rbs.project.pojo.entity.*;
import com.rbs.project.utils.LogicUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 16:15 2018/12/22
 */
@Service
public class ScoreService {
    @Autowired
    private SeminarScoreDao seminarScoreDao;

    @Autowired
    private RoundScoreDao roundScoreDao;

    @Autowired
    private TeamDao teamDao;

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private EmailService emailService;

    @Autowired
    private SeminarDao seminarDao;

    /**
     * Description: 获取一个班级下的一节讨论课的所有展示成绩
     *
     * @Author: 17Wang
     * @Time: 16:19 2018/12/22
     */
    public List<SeminarScore> listAllSeminarScoreBySeminarIdAndCClassId(long semianrId, long cClassId) throws MyException {
        List<SeminarScore> seminarScores = seminarScoreDao.listAllSeminarScoreBySeminarIdAndCClassId(semianrId, cClassId);
        //带上有班级信息的team
        for (SeminarScore seminarScore : seminarScores) {
            seminarScore.setTeam(teamDao.getTeamById(seminarScore.getTeamId(), TeamDao.HAS_CCLASS));
        }
        return seminarScores;
    }

    /**
     * Description: 一个队伍 一节讨论课只能报名一次 所有这两个id可以锁定一个分数
     *
     * @Author: 17Wang
     * @Time: 6:22 2018/12/29
     */
    public SeminarScore getSeminarScoreBySeminarIdAndTeamId(long seminarId, long teamId) throws MyException {
        return seminarScoreDao.getBySeminarIdAndTeamId(seminarId, teamId);
    }

    /**
     * Description: 获取一个轮次下所有队伍的讨论课的成绩
     *
     * @Author: 17Wang
     * @Time: 17:02 2018/12/22
     */
    public List<RoundScore> listAllRoundScoreByRoundId(long roundId) throws MyException {
        List<RoundScore> roundScores = roundScoreDao.listAllRoundScoreByRoundId(roundId);
        for (RoundScore roundScore : roundScores) {
            roundScore.setTeam(teamDao.getTeamById(roundScore.getTeamId(), TeamDao.HAS_CCLASS));
        }
        return roundScores;
    }

    /**
     * Description: 通过roundId和teamId锁定一条轮次分数
     *
     * @Author: 17Wang
     * @Time: 5:58 2018/12/29
     */
    public RoundScore getRoundScoreByRoundIdAndTeamId(long roundId, long teamId) throws MyException {
        RoundScore roundScore = roundScoreDao.getByRoundIdAndTeamId(roundId, teamId);
        if (roundScore == null) {
            throw new MyException("通过roundId和teamId锁定一条轮次分数错误！不存在该记录", MyException.NOT_FOUND_ERROR);
        }
        return roundScore;
    }

    /**
     * Description: 获取一节展示的分数
     *
     * @Author: 17Wang
     * @Time: 3:39 2018/12/29
     */
    public SeminarScore getSeminarScoreByClassSeminarIdAndTeamId(long classSeminarId, long teamId) {
        return seminarScoreDao.getByClassSeminarIdAndTeamId(classSeminarId, teamId);
    }

    /**
     * Description: 修改展示的展示分数
     *
     * @Author: 17Wang
     * @Time: 17:37 2018/12/22
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean updatePresentationScore(long seminarId, long classId, long teamId, double presentationScore) throws Exception {
        //修改展示的展示分数
        seminarScoreDao.updatePresentationScore(seminarId, classId, teamId, presentationScore);
        //修改轮次的展示分数
        roundScoreDao.updatePresentationScore(seminarDao.findSeminarById(seminarId).getRoundId(), teamId);

        return true;
    }

    /**
     * Description: 修改展示的报告分数
     *
     * @Author: 17Wang
     * @Time: 20:18 2018/12/22
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean updateReportScore(long seminarId, long classId, long teamId, double reportScore) throws Exception {
        //修改展示的报告分数
        seminarScoreDao.updateReportScore(seminarId, classId, teamId, reportScore);
        //修改轮次的报告分数
        roundScoreDao.updateReportScore(seminarDao.findSeminarById(seminarId).getRoundId(), teamId);

        return true;
    }


    /**
     * Description: 修改展示的提问分数的同时修改总分
     *
     * @Author: 17Wang
     * @Time: 20:04 2018/12/28
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean updateQuestionScore(long seminarId, long classId, long teamId, double questionScore) throws Exception {
        //修改提问分数
        seminarScoreDao.updateQuestionScore(seminarId, classId, teamId, questionScore);
        //修改轮次的提问分数
        roundScoreDao.updateQuestionScore(seminarDao.findSeminarById(seminarId).getRoundId(), teamId);

        return true;
    }

    /**
     * Description: 修改3*6个分数
     *
     * @Author: 17Wang
     * @Time: 4:49 2018/12/29
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean updateAllScoreByCClassSeminar(long classId, long seminarId, List<SeminarScore> seminarScores) throws Exception {
        for (SeminarScore seminarScore : seminarScores) {
            seminarScoreDao.updatePresentationScore(seminarId, classId, seminarScore.getTeamId(), seminarScore.getPresentationScore());
            seminarScoreDao.updateQuestionScore(seminarId, classId, seminarScore.getTeamId(), seminarScore.getQuestionScore());
            seminarScoreDao.updateReportScore(seminarId, classId, seminarScore.getTeamId(), seminarScore.getReportScore());
        }
        return true;
    }

    /**
     * Description: 修改 3*1 个分数
     * @Author: 17Wang
     * @Time: 7:10 2018/12/29
    */
    @Transactional(rollbackFor = Exception.class)
    public boolean updateScoreByCClassSeminar(long classId, long seminarId,SeminarScore seminarScore) throws Exception {
        seminarScoreDao.updatePresentationScore(seminarId, classId, seminarScore.getTeamId(), seminarScore.getPresentationScore());
        seminarScoreDao.updateQuestionScore(seminarId, classId, seminarScore.getTeamId(), seminarScore.getQuestionScore());
        seminarScoreDao.updateReportScore(seminarId, classId, seminarScore.getTeamId(), seminarScore.getReportScore());
        return true;
    }

    /**
     * Description: 发送成绩相关邮件
     *
     * @Author: WinstonDeng
     * @Date: 1:44 2018/12/28
     */
    private void sendScoreEmail(Seminar seminar, String message) throws Exception {
        List<Team> teams = teamDao.listByCourseId(seminar.getCourseId());
        for (Team team
                : teams) {
            List<Student> students = studentDao.listByTeamId(team.getId());
            for (Student student
                    : students) {
                emailService.sendEmail(new String[]{student.getEmail()}, message);
            }
        }
    }
}
