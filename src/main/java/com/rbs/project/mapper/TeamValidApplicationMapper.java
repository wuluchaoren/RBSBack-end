package com.rbs.project.mapper;

import com.rbs.project.pojo.entity.TeamValidApplication;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 13:44 2018/12/23
 */
@Mapper
@Repository
public interface TeamValidApplicationMapper {

    /**
     * 通过Id查询一条请求
     *
     * @param id
     * @return
     */
    TeamValidApplication findById(long id);

    /**
     * 通过teamId和teacherId查询一条请求
     *
     * @param teamId
     * @param teacherId
     * @return
     */
    TeamValidApplication findByTeamIdAndTeacherId(@Param("teamId") long teamId, @Param("teacherId") long teacherId);

    /**
     * 通过teacherid获取所有合法化请求
     *
     * @param teacherId
     * @return
     */
    List<TeamValidApplication> findByTeacherId(long teacherId);

    /**
     * 新建一个请求
     *
     * @param teamValidApplication
     * @return
     * @throws Exception
     */
    boolean insertApplication(TeamValidApplication teamValidApplication) throws Exception;

    /**
     * 修改请求的原因
     *
     * @param reason
     * @param id
     * @return
     * @throws Exception
     */
    boolean updateReasonById(@Param("reason") String reason, @Param("id") long id) throws Exception;

    /**
     * 修改请求的处理状态
     *
     * @param status
     * @param id
     * @return
     * @throws Exception
     */
    boolean updateStatusById(@Param("status") int status, @Param("id") long id) throws Exception;

    /**
     * 通过team_id删除记录
     * @param teamId
     * @return
     */
    boolean deleteByTeamId(long teamId);
}
