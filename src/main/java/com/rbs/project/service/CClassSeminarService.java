package com.rbs.project.service;

import com.rbs.project.dao.CClassSeminarDao;
import com.rbs.project.dao.QuestionDao;
import com.rbs.project.exception.MyException;
import com.rbs.project.pojo.entity.CClass;
import com.rbs.project.pojo.entity.CClassSeminar;
import com.rbs.project.pojo.entity.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: WinstonDeng
 * @Description: service层专门处理业务，增删改查前对输入判空
 * @Date: Created in 0:06 2018/12/19
 * @Modified by:
 */
@Service
public class CClassSeminarService {
    @Autowired
    private CClassSeminarDao cClassSeminarDao;

    @Autowired
    private QuestionDao questionDao;


    /**
     * Description: 修改班级讨论课
     *
     * @Author: WinstonDeng
     * @Date: 0:06 2018/12/19
     */
    public boolean updateCClassSeminar(CClassSeminar cClassSeminar) throws MyException {
        CClassSeminar temp = cClassSeminarDao.findCClassSeminarByCClassIdAndSeminarId(cClassSeminar.getcClassId(), cClassSeminar.getSeminarId());
        if (!cClassSeminar.getReportDDL().equals(temp.getReportDDL())) {
            temp.setReportDDL(cClassSeminar.getReportDDL());
        }
        //如果修改的是status
        if (!cClassSeminar.getStatus().equals(temp.getStatus())) {
            //此处应有websocket通知状态改变
            temp.setStatus(cClassSeminar.getStatus());
        }
        cClassSeminarDao.updateCClassSeminar(cClassSeminar);
        return true;
    }


    /**
     * Description: 查看班级讨论课
     *
     * @Author: WinstonDeng
     * @Date: 17:47 2018/12/21
     */
    public CClassSeminar getCClassSeminar(long cClassId, long seminarId) throws MyException {
        CClassSeminar cClassSeminar = cClassSeminarDao.findCClassSeminarByCClassIdAndSeminarId(cClassId, seminarId);
        if (cClassSeminar == null) {
            throw new MyException("查看班级讨论课错误！该记录不存在", MyException.NOT_FOUND_ERROR);
        }
        return cClassSeminar;
    }

    /**
     * Description: 通过id查看班级讨论课
     *
     * @Author: 17Wang
     * @Time: 9:12 2018/12/29
     */
    public CClassSeminar getCClassSeminarById(long id) throws MyException {
        return cClassSeminarDao.getCClassSeminarById(id);
    }

    /**
     * Description: 通过讨论课id查看班级讨论课列表
     *
     * @Author: WinstonDeng
     * @Date: 19:44 2018/12/21
     */
    public List<CClassSeminar> listAllCClassSeminarsBySeminarId(long seminarId) throws MyException {
        return cClassSeminarDao.findBySeminarId(seminarId);
    }

    /**
     * Description: 查看班级讨论课下所有提问
     *
     * @Author: WinstonDeng
     * @Date: 15:54 2018/12/22
     */
    public List<Question> listAllQuestionsByCClassIdAndSeminarId(long cClassId, long seminarId) throws MyException {
        CClassSeminar cClassSeminar = cClassSeminarDao.findCClassSeminarByCClassIdAndSeminarId(cClassId, seminarId);
        return questionDao.listAllQuestionsByCClassSemianr(cClassSeminar, QuestionDao.HAS_CCLASS_SEMINAR, QuestionDao.HAS_STUDENT, QuestionDao.HAS_TEAM);
    }

    /**
     * Description: 修改班级讨论课下的提问
     *
     * @param question
     * @Author: WinstonDeng
     * @Date: 16:02 2018/12/22
     */
    public boolean updateQuestion(Question question) throws MyException {
        Question temp = questionDao.getById(question.getId());
        temp.setSelected(question.getSelected());
        temp.setScore(question.getScore());
        return questionDao.updateQuestion(temp);
    }

    /**
     * Description: 获得当前课程正在进行的讨论课列表
     *
     * @Author: WinstonDeng
     * @Date: 23:18 2018/12/25
     */
    public List<CClassSeminar> listAllUnderWaySeminarsByTeacherId(long teacherId) throws MyException {
        return cClassSeminarDao.findUnderWayByTeacherId(teacherId);
    }
}
