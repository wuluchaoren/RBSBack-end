package com.rbs.project.mapper;

import com.rbs.project.pojo.entity.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 10:02 2018/12/29
 */
@Mapper
@Repository
public interface AdminMapper {

    /**
     * 通过用户名查找管理员
     * @param acoount
     * @return
     */
    Admin findByAccount(String acoount);
}
