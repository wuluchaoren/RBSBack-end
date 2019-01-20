package com.rbs.project.mapper;

import com.rbs.project.pojo.entity.Student;
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
public interface TeamStudentMapper {
    /**
     * 新增teamStudent表字段
     *
     * @param teamId
     * @param studentId
     * @return
     * @throws Exception
     */
    boolean insertByTeamIdAndStudentId(@Param("teamId") long teamId, @Param("studentId") long studentId) throws Exception;

    /**
     * 删除teamStudent表字段
     *
     * @param teamId
     * @param studentId
     * @return
     */
    boolean deleteByTeamIdAndStudentId(@Param("teamId") long teamId, @Param("studentId") long studentId);
}
