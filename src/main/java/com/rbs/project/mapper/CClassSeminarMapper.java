package com.rbs.project.mapper;

import com.rbs.project.pojo.entity.CClassSeminar;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: WinstonDeng
 * @Description: OOAD_Course_ManageSystem
 * @Date: Created in 18:56 2018/12/16
 * @Modified by:
 */
@Mapper
@Repository
public interface CClassSeminarMapper {
    //===============================查找=========================

    /**
     * 通过id找班级讨论课
     * @param id
     * @return
     */
    CClassSeminar findById(long id);

    /**
     * 通过班级找班级讨论课列表
     * @param cClassId
     * @return
     */
    List<CClassSeminar> findByCClassId(long cClassId);

    /**
     * 通过课程找班级讨论课列表
     * @param courseId
     * @return
     */
    List<CClassSeminar> findByCourseId(long courseId);

    /**
     * 通过讨论课id查找班级讨论课
     * @param seminarId
     * @return
     */
    List<CClassSeminar> findBySeminarId(long seminarId);

    /**
     * 通过班级和讨论课查找班级讨论课
     * @param cClassId
     * @param seminarId
     * @return
     */
    CClassSeminar findByCClassIdAndSeminarId(@Param("cClassId") long cClassId, @Param("seminarId") long seminarId);

    //==============================修改=========================

    /**
     * 通过班级和讨论课修改班级讨论课信息
     * @param cClassSeminar
     * @return
     */
    boolean updateCClassSeminar(CClassSeminar cClassSeminar);


    //==============================删除=========================
    /**
     * 通过讨论课id删除班级讨论课
     * @param seminarId
     * @return
     */
    boolean removeCClassSeminarBySeminarId(long seminarId);

    //==============================新增=========================

    /**
     * 新增班级讨论课
     * @param cClassSeminar
     * @return
     */
    boolean insertCClassSeminar(CClassSeminar cClassSeminar);


}
