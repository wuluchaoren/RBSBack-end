package com.rbs.project.dao;

import com.rbs.project.exception.MyException;
import com.rbs.project.mapper.*;
import com.rbs.project.pojo.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: WinstonDeng
 * TODO 判断是否为讨论课从课程
 * @Description: Dao只做直接的增删改查操作及逻辑，改删前先查处对象，修改相应字段，再改到保存
 * @Date: Created in 23:51 2018/12/18
 * @Modified by:
 */
@Repository
public class CClassSeminarDao {
    @Autowired
    private CClassSeminarMapper cClassSeminarMapper;

    @Autowired
    private CClassMapper cClassMapper;

    @Autowired
    private SeminarMapper seminarMapper;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private TeamMapper teamMapper;

    @Autowired
    private SeminarScoreMapper seminarScoreMapper;

    public final static int HAS_CLASS = 0;
    public final static int HAS_SEMINAR = 1;

    private void hasSomethingFun(CClassSeminar cClassSeminar, int... hasSomething) {
        for (int i : hasSomething) {
            if (i == HAS_CLASS) {
                cClassSeminar.setcClass(cClassMapper.findById(cClassSeminar.getcClassId()));
            }
            if (i == HAS_SEMINAR) {
                cClassSeminar.setSeminar(seminarMapper.findById(cClassSeminar.getSeminarId()));
            }
        }
    }

    /**
     * Description: 按id查找班级讨论课信息
     *
     * @Author: 17Wang
     * @Time: 22:59 2018/12/21
     */
    public CClassSeminar getCClassSeminarById(long cClassSeminarId) throws MyException {
        CClassSeminar cClassSeminar = cClassSeminarMapper.findById(cClassSeminarId);
        if (cClassSeminar == null) {
            throw new MyException("查找班级讨论课信息错误！未找到该班级讨论课", MyException.NOT_FOUND_ERROR);
        }
        return cClassSeminar;
    }

    /**
     * Description: 通过课程找班级讨论课列表
     * @Author: 17Wang
     * @Time: 12:57 2018/12/28
     */
    public List<CClassSeminar> listByCourseId(long courseId){
        return cClassSeminarMapper.findByCourseId(courseId);
    }

    /**
     * Description: 按班级id和讨论课id查找班级讨论课
     *
     * @Author: WinstonDeng
     * @Date: 10:58 2018/12/19
     */
    public CClassSeminar findCClassSeminarByCClassIdAndSeminarId(long cClassId, long seminarId, int... hasSomething) throws MyException {
        CClassSeminar cClassSeminar = cClassSeminarMapper.findByCClassIdAndSeminarId(cClassId, seminarId);
        if (cClassSeminar == null) {
            throw new MyException("查看班级讨论课错误！该记录不存在", MyException.NOT_FOUND_ERROR);
        }
        hasSomethingFun(cClassSeminar, hasSomething);
        return cClassSeminar;
    }

    /**
     * Description: 按班级id和讨论课id修改班级讨论课信息
     *
     * @Author: WinstonDeng
     * @Date: 23:52 2018/12/18
     */
    public boolean updateCClassSeminar(CClassSeminar cClassSeminar) throws MyException {
        //先查后改
        CClassSeminar temp = cClassSeminarMapper.findByCClassIdAndSeminarId(cClassSeminar.getcClassId(), cClassSeminar.getSeminarId());
        if (temp == null) {
            throw new MyException("修改班级讨论课信息错误！未找到次记录", MyException.NOT_FOUND_ERROR);
        }
        try {
            temp.setReportDDL(cClassSeminar.getReportDDL());
            temp.setStatus(cClassSeminar.getStatus());
            cClassSeminarMapper.updateCClassSeminar(temp);
        } catch (Exception e) {
            throw new MyException("修改班级讨论课信息错误！数据库处理错误", MyException.ERROR);
        }
        return true;
    }

    /**
     * Description:
     * 1、新增班级讨论课，创建讨论课的时候，每个班都和讨论课连接关系
     * 2、创建班级讨论课的时候，属于该班级下的所有小组都要与该班级讨论课建立seminarScore联系
     *
     * @Author: WinstonDeng
     * @Date: 10:22 2018/12/21
     */
    public boolean addCClassSeminar(Seminar seminar) throws MyException {
        try {
            //课程下的所有班级
            List<CClass> cClasses = cClassMapper.findByCourseId(seminar.getCourseId());

            for (CClass cClass : cClasses) {
                CClassSeminar cClassSeminar = new CClassSeminar();
                cClassSeminar.setcClassId(cClass.getId());
                cClassSeminar.setSeminarId(seminar.getId());
                cClassSeminar.setReportDDL(null);
                //默认未开始
                cClassSeminar.setStatus(0);
                cClassSeminarMapper.insertCClassSeminar(cClassSeminar);

                //获取该班级下的所有小组
                List<Team> teams = teamMapper.findByCClassId(cClass.getId());
                //建立每个组在这个讨论课下的分数
                for (Team team : teams) {
                    seminarScoreMapper.insertSeminarScore(cClassSeminar.getId(), team.getId());
                }
            }
        } catch (Exception e) {
            throw new MyException("新增班级讨论课错误！数据库处理错误", MyException.ERROR);
        }
        return true;
    }

    /**
     * Description: 通过讨论课id查找班级讨论课
     *
     * @Author: WinstonDeng
     * @Date: 19:38 2018/12/21
     */
    public List<CClassSeminar> findBySeminarId(long seminarId, int... hasSomething) throws MyException {
        List<CClassSeminar> cClassSeminars = null;
        try {
            cClassSeminars = cClassSeminarMapper.findBySeminarId(seminarId);
        } catch (Exception e) {
            throw new MyException("查看班级讨论课错误！数据库处理错误", MyException.ERROR);
        }
        for (CClassSeminar cClassSeminar : cClassSeminars) {
            hasSomethingFun(cClassSeminar, hasSomething);
        }
        return cClassSeminars;
    }

    /**
     * Description: 通过courseId获得正在进行讨论课列表
     *
     * @Author: WinstonDeng
     * @Date: 23:20 2018/12/25
     */
    public List<CClassSeminar> findUnderWayByCourseId(long courseId) throws MyException {
        List<CClass> cClasses = cClassMapper.findByCourseId(courseId);
        List<CClassSeminar> cClassSeminars = new ArrayList<>();
        for (CClass cClass
                : cClasses) {
            List<CClassSeminar> temp = cClassSeminarMapper.findByCClassId(cClass.getId());
            for (CClassSeminar cClassSeminar : temp) {
                if (cClassSeminar.getStatus() == CClassSeminar.STATUS_UNDERWAY) {
                    cClassSeminars.add(cClassSeminar);
                }
            }
        }
        return cClassSeminars;
    }

    /**
     * Description: 通过teacherId获得正在进行的讨论课
     *
     * @Author: WinstonDeng
     * @Date: 0:07 2018/12/26
     */
    public List<CClassSeminar> findUnderWayByTeacherId(long teacherId) {
        List<CClassSeminar> cClassSeminars = new ArrayList<>();
        List<Course> courses = courseMapper.findByTeacherId(teacherId);
        for (Course course
                : courses) {
            List<CClass> cClasses = cClassMapper.findByCourseId(course.getId());
            for (CClass cClass
                    : cClasses) {
                List<CClassSeminar> temp = cClassSeminarMapper.findByCClassId(cClass.getId());
                for (CClassSeminar cClassSeminar : temp) {
                    if (cClassSeminar.getStatus() == CClassSeminar.STATUS_UNDERWAY) {
                        cClassSeminars.add(cClassSeminar);
                    }
                }
            }
        }
        return cClassSeminars;
    }

    /**
     * Description: 通过课程id查找班级讨论课
     * @Author: WinstonDeng
     * @Date: 2:17 2018/12/29
     */
    public List<CClassSeminar> listByCClassId(long cClassId) {
        return cClassSeminarMapper.findByCClassId(cClassId);
    }
}
