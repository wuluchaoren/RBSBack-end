package com.rbs.project.mapper;

import com.rbs.project.pojo.entity.ShareTeamApplication;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: WinstonDeng
 * @Description: OOAD_Course_ManageSystem
 * @Date: Created in 21:53 2018/12/24
 * @Modified by:
 */
@Repository
public interface ShareTeamApplicationMapper {
    /**
     * 通过id查找组队共享申请
     *
     * @param id
     * @return
     */
    ShareTeamApplication findById(long id);

    /**
     * 通过主课程id查找组队共享申请列表
     *
     * @param mainCourseId
     * @return
     */
    List<ShareTeamApplication> findByMainCourseId(long mainCourseId);

    /**
     * 通过从课程id查找组队共享申请列表
     *
     * @param subCourseId
     * @return
     */
    List<ShareTeamApplication> findBySubCourseId(long subCourseId);


    /**
     * 通过id 和状态码 status 修改组队共享请求状态
     *
     * @param id
     * @param status
     * @return
     */
    boolean updateStatusById(@Param("id") long id, @Param("status") Integer status);

    /**
     * 删除主从课程和courseId有关的请求
     *
     * @param courseId
     * @return
     * @throws Exception
     */
    boolean deleteByCourseId(long courseId) throws Exception;

    /**
     * 新增组队共享申请
     * @param shareTeamApplication
     * @return
     */
    boolean addShareTeamApplication(ShareTeamApplication shareTeamApplication);

    /**
     * 删除队伍共享记录
     * @param id
     * @return
     */
    boolean deleteTeamShareApplication(long id);

    /**
     * 通过从课程老师id获取所有组队恭喜申请
     * @param subTeacherId
     * @return
     */
    List<ShareTeamApplication> findBySubTeacherId(long subTeacherId);
}
