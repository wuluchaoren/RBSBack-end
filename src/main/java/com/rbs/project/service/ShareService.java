package com.rbs.project.service;

import com.rbs.project.dao.ShareDao;
import com.rbs.project.exception.MyException;
import com.rbs.project.pojo.entity.ShareSeminarApplication;
import com.rbs.project.pojo.entity.ShareTeamApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: WinstonDeng
 * @Description: OOAD_Course_ManageSystem
 * @Date: Created in 16:28 2018/12/23
 * @Modified by:
 */

@Service
public class ShareService {

    @Autowired
    private ShareDao shareDao;

    /**
     * Description: 通过从课程id查找待办共享讨论课信息
     * @Author: WinstonDeng
     * @Date: 16:29 2018/12/23
     */
    public List<ShareSeminarApplication> listUnhandleShareSeminarApplicationBySubCourseId(long subCourseId)throws Exception{
        return shareDao.listAllShareSeminarApplicationsBySubCourseId(subCourseId);
    }

    /**
     * Description: 通过课程id查找【作为主课程】的讨论课共享信息列表
     * @Author: WinstonDeng
     * @Date: 17:28 2018/12/23
     */
    public List<ShareSeminarApplication> listAllShareSeminarsInMainCourseByCourseId(long courseId) throws Exception{
        //主课程方 发起的所有共享记录
        List<ShareSeminarApplication> seminarSharesInMainCourse=new ArrayList<>();
        List<ShareSeminarApplication> shareSeminarApplications=shareDao.listAllShareSeminarApplicationsByMainCourseId(courseId,ShareDao.HAS_SUB_COURSE,ShareDao.HAS_SUB_COURSE_TEACHER);
        for(ShareSeminarApplication shareSeminarApplication:shareSeminarApplications){
            //如果是同意的记录
            if(shareSeminarApplication.getStatus()==ShareSeminarApplication.STATUS_ACCEPT){
                seminarSharesInMainCourse.add(shareSeminarApplication);
            }
        }
        return seminarSharesInMainCourse;
    }

    /**
     * Description: 通过课程id查找【作为从课程】的讨论课共享信息列表
     * @Author: WinstonDeng
     * @Date: 17:41 2018/12/23
     */
    public List<ShareSeminarApplication> listAllShareSeminarsInSubCourseByCourseId(long courseId) throws Exception{
        //从课程方 接受的所有共享记录
        List<ShareSeminarApplication> seminarSharesInSubCourse=new ArrayList<>();
        List<ShareSeminarApplication> shareSeminarApplications=shareDao.listAllShareSeminarApplicationsBySubCourseId(courseId,ShareDao.HAS_MAIN_COURSE,ShareDao.HAS_MAIN_COURSE_TEACHER);
        for(ShareSeminarApplication shareSeminarApplication:shareSeminarApplications){
            //如果是同意的记录
            if(shareSeminarApplication.getStatus()==ShareSeminarApplication.STATUS_ACCEPT){
                seminarSharesInSubCourse.add(shareSeminarApplication);
            }
        }
        return seminarSharesInSubCourse;
    }

    /**
     * Description: 通过从课程id查找待办共享分组信息
     * @Author: WinstonDeng
     * @Date: 16:29 2018/12/23
     */
    public List<ShareTeamApplication> listUnhandleShareTeamApplicationBySubCourseId(long subCourseId)throws Exception{
        return shareDao.listAllShareTeamApplicationsBySubCourseId(subCourseId);
    }

    /**
     * Description: 通过课程id查找【作为主课程】的分组共享信息列表
     * @Author: WinstonDeng
     * @Date: 17:28 2018/12/23
     */
    public List<ShareTeamApplication> listAllShareTeamsInMainCourseByCourseId(long courseId) throws Exception{
        //主课程方 发起的所有共享记录
        List<ShareTeamApplication> teamSharesInMainCourse=new ArrayList<>();
        List<ShareTeamApplication> shareTeamApplications=shareDao.listAllShareTeamApplicationsByMainCourseId(courseId,ShareDao.HAS_SUB_COURSE,ShareDao.HAS_SUB_COURSE_TEACHER);
        for(ShareTeamApplication shareTeamApplication:shareTeamApplications){
            //如果是同意的记录
            if(shareTeamApplication.getStatus()==ShareTeamApplication.STATUS_ACCEPT){
                teamSharesInMainCourse.add(shareTeamApplication);
            }
        }
        return teamSharesInMainCourse;
    }

    /**
     * Description: 通过课程id查找【作为从课程】的分组共享信息列表
     * @Author: WinstonDeng
     * @Date: 17:41 2018/12/23
     */
    public List<ShareTeamApplication> listAllShareTeamsInSubCourseByCourseId(long courseId) throws Exception{
        //从课程方 接受的所有共享记录
        List<ShareTeamApplication> teamSharesInSubCourse=new ArrayList<>();
        List<ShareTeamApplication> shareTeamApplications=shareDao.listAllShareTeamApplicationsBySubCourseId(courseId,ShareDao.HAS_MAIN_COURSE,ShareDao.HAS_MAIN_COURSE_TEACHER);
        for(ShareTeamApplication shareTeamApplication:shareTeamApplications){
            //如果是同意的记录
            if(shareTeamApplication.getStatus()==ShareTeamApplication.STATUS_ACCEPT){
                teamSharesInSubCourse.add(shareTeamApplication);
            }
        }
        return teamSharesInSubCourse;
    }
}
