package com.rbs.project.mapper.strategy;

import com.rbs.project.pojo.strategy.TeamStrategy;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 23:42 2018/12/26
 */
@Mapper
@Repository
public interface TeamStrategyMapper {

    /**
     * 通过课程id获取
     *
     * @param courseId
     * @return
     */
    List<TeamStrategy> findByCourseId(long courseId);

    /**
     * 新增一条策略
     *
     * @param teamStrategy
     * @return
     */
    boolean insertStrategy(TeamStrategy teamStrategy);
}
