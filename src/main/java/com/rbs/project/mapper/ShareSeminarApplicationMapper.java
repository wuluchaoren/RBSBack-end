package com.rbs.project.mapper;

import com.rbs.project.pojo.entity.ShareSeminarApplication;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: WinstonDeng
 * @Description: OOAD_Course_ManageSystem
 * @Date: Created in 16:05 2018/12/23
 * @Modified by:
 */
@Repository
public interface ShareSeminarApplicationMapper {
    /**
     * 通过id查找共享讨论课申请
     *
     * @param id
     * @return
     */
    ShareSeminarApplication findById(long id);

    /**
     * 通过主课程id查找共享讨论课申请列表
     *
     * @param mainCourseId
     * @return
     * @throws Exception
     */
    List<ShareSeminarApplication> findByMainCourseId(long mainCourseId) throws Exception;

    /**
     * 通过从课程id查找讨论课共享申请列表
     *
     * @param subCourseId
     * @return
     * @throws Exception
     */
    List<ShareSeminarApplication> findBySubCourseId(long subCourseId) throws Exception;

    /**
     * 删除主从课程和courseId有关的请求
     *
     * @param courseId
     * @return
     * @throws Exception
     */
    boolean deleteByCourseId(long courseId) throws Exception;

    /**
     * 通过id修改讨论课共享请求状态
     * @param id
     * @param status
     * @return
     */
    boolean updateStatusById(@Param("id") long id, @Param("status") Integer status);

    /**
     * 新增讨论课共享申请记录
     * @param shareSeminarApplication
     * @return
     */
    boolean addShareSeminarApplication(ShareSeminarApplication shareSeminarApplication);

    /**
     * 取消讨论课共享记录
     * @param id
     * @return
     */
    boolean deleteSeminarShareApplication(long id);

    /**
     *  获取所有讨论课共享申请
     * @param subTeacherId
     * @return
     */
    List<ShareSeminarApplication> findBySubTeacherId(long subTeacherId);
}
