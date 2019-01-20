package com.rbs.project.mapper;

import com.rbs.project.pojo.entity.Question;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: WinstonDeng
 * @Description: OOAD_Course_ManageSystem
 * @Date: Created in 15:09 2018/12/22
 * @Modified by:
 */
@Repository
public interface QuestionMapper {
    /**
     * 通过id查找提问
     * @param id
     * @return
     */
    Question findById(long id);

    /**
     *  通过班级id和讨论课id查找提问
     * @param cClassSeminarId
     * @return
     */
    List<Question> findByCClassSeminarId(long cClassSeminarId);

    /**
     * 新增提问
     * @param question
     * @return
     */
    boolean insertQuestion(Question question);

    /**
     * 修改讨论课
     * @param question
     * @return
     */
    boolean updateQuestion(Question question);

    /**
     * 通过复合主键查询提问
     * @param cClassSeminarId
     * @param teamId
     * @param studentId
     * @return
     */
    Question findByPrimaryKeys(@Param("cClassSeminarId") long cClassSeminarId,@Param("teamId") long teamId,@Param("studentId") long studentId);
}
