package com.rbs.project.mapper;

import com.rbs.project.pojo.relationship.CClassRound;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: WinstonDeng
 * @Description: OOAD_Course_ManageSystem
 * @Date: Created in 15:37 2018/12/20
 * @Modified by:
 */
@Repository
public interface CClassRoundMapper {
    /**
     * 通过班级和轮次查找班级轮次记录
     * @param cClassId
     * @param roundId
     * @return
     */
    CClassRound findByPrimaryKeys(@Param("cClassId") long cClassId,@Param("roundId") long roundId);

    /**
     * 新增班级轮次
     * @param cClassRound
     * @return
     */
    boolean insertCClassRound(CClassRound cClassRound);

    /**
     * 修改班级轮次报名数
     * @param cClassRound
     * @return
     */
    boolean updateEnrollNumber(CClassRound cClassRound);

    /**
     * 锁定课程轮次
     * @param roundId
     * @param teamId
     * @return
     */
    CClassRound findByRoundIdAndTeamId(@Param("roundId") long roundId,@Param("teamId") long teamId);

    /**
     * 通过roundId查找所有班级轮次
     * @param roundId
     * @return
     */
    List<CClassRound> findByRoundId(long roundId);

    /**
     * 通过班级和轮次id 删除记录
     * @param cClassId
     * @param roundId
     * @return
     */
    boolean deleteByPrimaryKeys(@Param("cClassId") long cClassId,@Param("roundId") long roundId);
}
