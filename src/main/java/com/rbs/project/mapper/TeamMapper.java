package com.rbs.project.mapper;

import com.rbs.project.pojo.entity.Team;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
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
public interface TeamMapper {
    //=================查找===================

    /**
     * 通过id查找队伍
     *
     * @param id
     * @return
     */
    Team findById(Long id);

    /**
     * 通过leaderId返回一个team
     *
     * @param leaderId
     * @return
     */
    Team findByLeaderId(long leaderId);

    /**
     * 通过班级查找队伍列表，通过klass_team关系找的
     *
     * @param cClassId
     * @return
     */
    List<Team> findByCClassId(Long cClassId);

    /**
     * 新建一个队伍
     *
     * @param team
     * @return
     * @throws Exception
     */
    boolean insertTeam(Team team) throws Exception;

    /**
     * 学生查询自己在一个课程下属于的小组
     *
     * @param cClassId
     * @param studentId
     * @return
     */
    Team getTeamBycClassIdAndStudentId(@Param("cClassId") long cClassId, @Param("studentId") long studentId);

    /**
     * 通过课程id查找队伍列表
     * 主从课程都可以
     *
     * @param courseId
     * @return
     */
    List<Team> findByCourseId(long courseId);

    /**
     * 删除小组
     *
     * @param teamId
     * @return
     */
    boolean deleteById(long teamId);

    /**
     * 修改小组状态
     *
     * @param status
     * @param id
     * @return
     * @throws Exception
     */
    boolean updateStatusById(@Param("status") int status, @Param("id") long id) throws Exception;
}
