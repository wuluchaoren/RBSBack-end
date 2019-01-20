package com.rbs.project.mapper;

import com.rbs.project.pojo.entity.Seminar;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: WinstonDeng
 * @Description: OOAD_Course_ManageSystem
 * @Date: Created in 14:10 2018/12/16
 * @Modified by:
 */
@Mapper
@Repository
public interface SeminarMapper {
    //========================查找=====================

    /**
     * 通过id找讨论课
     * @param id
     * @return
     */
    Seminar findById(long id);

    /**
     * 通过课程找讨论课列表
     * @param courseId
     * @return
     */
    List<Seminar> findByCourseId(long courseId);

    /**
     * 通过轮次找讨论课列表
     * @param roundId
     * @return
     */
    List<Seminar> findByRoundId(long roundId);

    //=======================新增=====================

    /**
     * 新增讨论课
     * @param seminar
     * @return
     */
    boolean insertSeminar(Seminar seminar);

    //=======================删除=====================

    /**
     * 按id删除讨论课
     * @param seminarId
     * @return
     */
    boolean removeSeminarById(long seminarId);

    //=======================修改=====================

    /**
     * 修改讨论课信息
     * @param seminar
     * @return
     */
    boolean updateSeminar(Seminar seminar);


}
