package com.rbs.project.service;

import com.rbs.project.dao.QuestionDao;
import com.rbs.project.exception.MyException;
import com.rbs.project.pojo.entity.CClassSeminar;
import com.rbs.project.pojo.entity.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 15:35 2018/12/26
 */
@Service
public class QuestionService {
    @Autowired
    private QuestionDao questionDao;

    @Autowired
    private ScoreService scoreService;

    @Autowired
    private CClassSeminarService cClassSeminarService;

    /**
     * Description: 新增一个分数，修改semianrScore的分数
     *
     * @Author: 17Wang
     * @Time: 9:19 2018/12/29
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean addQuestion(Question question) throws Exception {
        //增加提问
        questionDao.addQuestion(question);
        //获取班级讨论课的信息
        CClassSeminar cClassSeminar = cClassSeminarService.getCClassSeminarById(question.getcClassSeminarId());
        //获取所有的提问
        List<Question> questionList = questionDao.getByCClassSeminarId(question.getcClassSeminarId());
        Double totalQuestionScore = 0.0;
        Long number = 0L;
        for (Question q : questionList) {
            if (q.getTeamId() == question.getTeamId()) {
                totalQuestionScore += q.getScore();
                number++;
            }
        }
        //修改分数
        scoreService.updateQuestionScore(cClassSeminar.getSeminarId(), cClassSeminar.getcClassId(), question.getTeamId(), totalQuestionScore/number);
        return true;
    }

    /**
     * Description:
     *
     * @Author: 17Wang
     * @Time: 12:53 2018/12/29
     */
    public List<Question> getQuestionBycClassSeminarId(long cClassSemianrId) {
        return questionDao.getByCClassSeminarId(cClassSemianrId);
    }

}
