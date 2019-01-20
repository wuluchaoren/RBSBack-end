package com.rbs.project.dao;

import com.rbs.project.exception.MyException;
import com.rbs.project.mapper.*;
import com.rbs.project.pojo.entity.Course;
import com.rbs.project.pojo.entity.Round;
import com.rbs.project.pojo.entity.RoundScore;
import com.rbs.project.pojo.entity.SeminarScore;
import com.rbs.project.pojo.relationship.CClassRound;
import com.rbs.project.utils.LogicUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 21:36 2018/12/21
 */
@Repository
public class RoundScoreDao {
    @Autowired
    private RoundScoreMapper roundScoreMapper;

    @Autowired
    private SeminarScoreMapper seminarScoreMapper;

    @Autowired
    private RoundMapper roundMapper;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private CClassRoundMapper cClassRoundMapper;

    /**
     * Description: 查找一轮下的所有轮次成绩
     *
     * @Author: 17Wang
     * @Time: 17:04 2018/12/22
     */
    public List<RoundScore> listAllRoundScoreByRoundId(long roundId) {
        return roundScoreMapper.findByRoundId(roundId);
    }

    /**
     * Description:
     * @Author: 17Wang
     * @Time: 6:00 2018/12/29
    */
    public RoundScore getByRoundIdAndTeamId(long roundId,long teamId){
        return roundScoreMapper.findByRoundIdAndTeamId(roundId, teamId);
    }

    /**
     * Description: 新增轮次分数
     *
     * @Author: 17Wang
     * @Time: 21:39 2018/12/21
     */
    public boolean addRoundScore(RoundScore roundScore) throws Exception {
        if (!roundScoreMapper.insertRoundScore(roundScore)) {
            throw new MyException("新增轮次分数失败！数据库处理错误", MyException.ERROR);
        }
        return true;
    }

    /**
     * Description: 修改轮次展示成绩
     *
     * @Author: 17Wang
     * @Time: 20:41 2018/12/28
     */
    public boolean updatePresentationScore(long roundId, long teamId) throws Exception {
        Double presentationScore = Double.valueOf(0);
        //一个小组在一个轮次下所有讨论课成绩
        List<SeminarScore> seminarScores = seminarScoreMapper.findByRoundIdAndTeamId(roundId, teamId);
        //Round的信息
        Round round = roundMapper.findById(roundId);
        //klass_round的信息 获取enrollNumber
        CClassRound cClassRound = cClassRoundMapper.findByRoundIdAndTeamId(roundId, teamId);

        //如果是平均分
        if (round.getPresentationScoreMethod() == Round.SCORE_AVERAGE) {
            double sumPresentationScore = 0.0;
            for (SeminarScore seminarScore : seminarScores) {
                sumPresentationScore += seminarScore.getPresentationScore();
            }
            presentationScore = sumPresentationScore / cClassRound.getEnrollNumber();
        }
        //如果是最高分
        if (round.getPresentationScoreMethod() == Round.SCORE_MAX) {
            double maxPresentationScore = 0.0;
            for (SeminarScore seminarScore : seminarScores) {
                if (seminarScore.getPresentationScore() == null) {
                    continue;
                }
                if (seminarScore.getPresentationScore() > maxPresentationScore) {
                    maxPresentationScore = seminarScore.getPresentationScore();
                }
            }
            presentationScore = maxPresentationScore;
        }

        //修改展示分数
        if (!roundScoreMapper.updatePresentationScore(roundId, teamId, presentationScore)) {
            throw new MyException("修改轮次展示分数失败！数据库处理错误", MyException.ERROR);
        }
        //修改总分
        updateTotalScore(roundId, teamId);
        return true;
    }


    /**
     * Description: 修改轮次提问成绩
     *
     * @Author: 17Wang
     * @Time: 22:40 2018/12/28
     */
    public boolean updateQuestionScore(long roundId, long teamId) throws Exception {
        Double questionScore = Double.valueOf(0);
        //一个小组在一个轮次下所有讨论课成绩
        List<SeminarScore> seminarScores = seminarScoreMapper.findByRoundIdAndTeamId(roundId, teamId);
        //Round的信息
        Round round = roundMapper.findById(roundId);

        CClassRound cClassRound = cClassRoundMapper.findByRoundIdAndTeamId(roundId, teamId);

        //如果是平均分
        if (round.getQuestionScoreMethod() == Round.SCORE_AVERAGE) {
            double sumQuestionScore = 0.0;
            for (SeminarScore seminarScore : seminarScores) {
                if (seminarScore.getQuestionScore() == null) {
                    continue;
                }
                sumQuestionScore += seminarScore.getQuestionScore();
            }
            questionScore = sumQuestionScore / cClassRound.getEnrollNumber();
        }
        //如果是最高分
        if (round.getQuestionScoreMethod() == Round.SCORE_MAX) {
            double maxQuestionScore = 0.0;
            for (SeminarScore seminarScore : seminarScores) {
                if (seminarScore.getQuestionScore() == null) {
                    continue;
                }
                if (seminarScore.getQuestionScore() > maxQuestionScore) {
                    maxQuestionScore = seminarScore.getQuestionScore();
                }
            }
            questionScore = maxQuestionScore;
        }

        //修改展示分数
        if (!roundScoreMapper.updateQuestionScore(roundId, teamId, questionScore)) {
            throw new MyException("修改轮次提问分数失败！数据库处理错误", MyException.ERROR);
        }
        //修改总分
        updateTotalScore(roundId, teamId);
        return true;
    }

    /**
     * Description: 修改轮次报告成绩
     *
     * @Author: 17Wang
     * @Time: 22:44 2018/12/28
     */
    public boolean updateReportScore(long roundId, long teamId) throws Exception {
        Double reportScore = Double.valueOf(0);
        //一个小组在一个轮次下所有讨论课成绩
        List<SeminarScore> seminarScores = seminarScoreMapper.findByRoundIdAndTeamId(roundId, teamId);
        //Round的信息
        Round round = roundMapper.findById(roundId);

        CClassRound cClassRound = cClassRoundMapper.findByRoundIdAndTeamId(roundId, teamId);

        //如果是平均分
        if (round.getReportScoreMethod() == Round.SCORE_AVERAGE) {
            double sumReportScore = 0.0;
            for (SeminarScore seminarScore : seminarScores) {
                if (seminarScore.getReportScore() == null) {
                    continue;
                }
                sumReportScore += seminarScore.getReportScore();
            }
            reportScore = sumReportScore / cClassRound.getEnrollNumber();
        }
        //如果是最高分
        if (round.getReportScoreMethod() == Round.SCORE_MAX) {
            double maxReportScore = 0.0;
            for (SeminarScore seminarScore : seminarScores) {
                if (seminarScore.getReportScore() == null) {
                    continue;
                }
                if (seminarScore.getReportScore() > maxReportScore) {
                    maxReportScore = seminarScore.getReportScore();
                }
            }
            reportScore = maxReportScore;
        }

        //修改展示分数
        if (!roundScoreMapper.updateReportScore(roundId, teamId, reportScore)) {
            throw new MyException("修改轮次展示分数失败！数据库处理错误", MyException.ERROR);
        }
        //修改总分
        updateTotalScore(roundId, teamId);
        return true;
    }

    /**
     * Description:修改轮次总分
     *
     * @Author: 17Wang
     * @Time: 22:47 2018/12/28
     */
    private boolean updateTotalScore(long roundId, long teamId) throws Exception {
        //获取一次轮次成绩的信息
        RoundScore roundScore = roundScoreMapper.findByRoundIdAndTeamId(roundId, teamId);
        //获取这个轮次属于哪一个课程，课程有计算该分数的规则
        Course course = courseMapper.findById(roundMapper.findById(roundId).getCourseId());
        double totalScore = LogicUtils.calculateRoundTotalScore(roundScore, course);
        //修改总分
        if (!roundScoreMapper.updateTotalScore(roundId, teamId, totalScore)) {
            throw new MyException("修改轮次的总分数错误！数据库执行错误", MyException.ERROR);
        }
        return true;
    }

    //=========================================================================删除轮次分数

    /**
     * Description: 删除轮次分数
     *
     * @Author: 17Wang
     * @Time: 23:12 2018/12/21
     */
    public boolean deleteRoundScoreByPrimaryKey(long roundId, long teamId) throws Exception {
        return roundScoreMapper.deleteByPrimaryKey(roundId, teamId);
    }

    /**
     * Description: 删除轮次分数
     *
     * @Author: 17Wang
     * @Time: 20:40 2018/12/28
     */
    public boolean deleteRoundScoreByTeamId(long teamId) throws MyException {
        return roundScoreMapper.deleteByTeamId(teamId);
    }
}
