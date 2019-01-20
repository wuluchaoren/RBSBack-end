package com.rbs.project.utils;

import com.rbs.project.dao.CourseDao;
import com.rbs.project.exception.MyException;
import com.rbs.project.pojo.entity.*;


/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 16:00 2018/12/19
 */
public class LogicUtils {

    private static CourseDao courseDao = new CourseDao();

    /**
     * Description: 计算一次讨论课成绩的总分
     *
     * @Author: 17Wang
     * @Time: 20:38 2018/12/22
     */
    public static double calculateSeminarTotalScore(SeminarScore seminarScore, Course course) throws MyException {
        if (seminarScore == null) {
            throw new MyException("计算一次讨论课成绩的总分出错！传入讨论课成绩不存在", MyException.ERROR);
        }
        if (course == null) {
            throw new MyException("计算一次讨论课成绩的总分出错！传入课程不存在", MyException.ERROR);
        }
        double presentationPercentage = course.getPresentationPercentage() / 100.0;
        double questionPercentage = course.getQuestionPercentage() / 100.0;
        double reportPercentage = course.getReportPercentage() / 100.0;

        double presentationScore = seminarScore.getPresentationScore() * presentationPercentage;
        double questionScore = seminarScore.getQuestionScore() * questionPercentage;
        double reportScore = seminarScore.getReportScore() * reportPercentage;

        return presentationScore + questionScore + reportScore;
    }

    /**
     * Description: 计算一个轮次成绩的总分
     *
     * @Author: 17Wang
     * @Time: 22:54 2018/12/28
     */
    public static double calculateRoundTotalScore(RoundScore roundScore, Course course) throws MyException {
        if (roundScore == null) {
            throw new MyException("计算一次轮次成绩的总分出错！传入轮次成绩不存在", MyException.ERROR);
        }
        if (course == null) {
            throw new MyException("计算一个轮次成绩的总分出错！传入课程不存在", MyException.ERROR);
        }
        double presentationPercentage = course.getPresentationPercentage() / 100.0;
        double questionPercentage = course.getQuestionPercentage() / 100.0;
        double reportPercentage = course.getReportPercentage() / 100.0;
        Double presentationScore = Double.valueOf(0);
        Double questionScore = Double.valueOf(0);
        Double reportScore = Double.valueOf(0);
        if (roundScore.getPresentationScore() != null) {
            presentationScore = roundScore.getPresentationScore();
        }
        if (roundScore.getQuestionScore() != null) {
            questionScore = roundScore.getQuestionScore();
        }
        if (roundScore.getReportScore() != null) {
            reportScore = roundScore.getReportScore();
        }

        return presentationScore * presentationPercentage +
                questionScore * questionPercentage +
                reportScore * reportPercentage;
    }

}
