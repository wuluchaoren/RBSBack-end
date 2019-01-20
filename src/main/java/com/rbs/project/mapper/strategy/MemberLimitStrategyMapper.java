package com.rbs.project.mapper.strategy;

import com.rbs.project.pojo.strategy.MemberLimitStrategy;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 0:08 2018/12/27
 */
@Mapper
@Repository
public interface MemberLimitStrategyMapper {

    /**
     * 通过id获取策略信息
     *
     * @param id
     * @return
     */
    MemberLimitStrategy findById(long id);

    /**
     * 新增人数策略
     *
     * @param memberLimitStrategy
     * @return
     */
    boolean insertStrategy(MemberLimitStrategy memberLimitStrategy);
}
