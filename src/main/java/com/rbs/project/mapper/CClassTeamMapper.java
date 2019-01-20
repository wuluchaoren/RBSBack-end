package com.rbs.project.mapper;

import com.rbs.project.pojo.relationship.CClassTeam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 21:58 2018/12/25
 */
@Mapper
@Repository
public interface CClassTeamMapper {

    /**
     * 新增一条数据
     *
     * @param cClassId
     * @param teamId
     * @return
     * @throws Exception
     */
    boolean insertBycClassIdAndTeamId(@Param("cClassId") long cClassId, @Param("teamId") long teamId) throws Exception;

    /**
     * 解除所有与teamId共享的关联信息
     *
     * @param teamId
     * @return
     */
    boolean deleteByTeamId(long teamId);

    /**
     * 通过课程和小组删除记录
     *
     * @param cClassId
     * @param teamId
     * @return
     */
    boolean deleteByCClassIdAndTeamId(@Param("cClassId") long cClassId,@Param("teamId") long teamId);

    /**
     * 判断当前小组是否在klass_team中，用于共享小组共享删除后，对其参与的attendence score等
     * 存在性的判断
     * @param teamId
     * @return
     */
    List<CClassTeam> findByTeamId(long teamId);
}
