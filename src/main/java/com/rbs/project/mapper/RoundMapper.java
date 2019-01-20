package com.rbs.project.mapper;

import com.rbs.project.pojo.entity.Round;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: WinstonDeng
 * @Description: OOAD_Course_ManageSystem
 * @Date: Created in 10:59 2018/12/20
 * @Modified by:
 */
@Repository
public interface RoundMapper {
    /**
     * 通过id查找轮次
     *
     * @param id
     * @return
     */
    Round findById(long id);

    /**
     * 通过courseId查找轮次列表
     *
     * @param courseId
     * @return
     */
    List<Round> findByCourseId(long courseId);

    /**
     * 新增轮次
     *
     * @param round
     * @return
     */
    boolean insertRound(Round round);

    /**
     * 按id修改分数计算方法
     *
     * @param round
     * @return
     */
    boolean updateScoreMethod(Round round);

    /**
     * 通过id删除轮次
     *
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteById(long id) throws Exception;


}
