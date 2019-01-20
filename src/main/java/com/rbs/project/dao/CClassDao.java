package com.rbs.project.dao;

import com.rbs.project.exception.MyException;
import com.rbs.project.mapper.*;
import com.rbs.project.pojo.relationship.CClassRound;
import com.rbs.project.pojo.relationship.CClassStudent;
import com.rbs.project.pojo.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: WinstonDeng
 * @Description: OOAD_Course_ManageSystem
 * @Date: Created in 12:50 2018/12/16
 * @Modified by:
 */
@Repository
public class CClassDao {

    @Autowired
    private CClassMapper cClassMapper;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private TeamMapper teamMapper;

    @Autowired
    private CClassSeminarMapper cClassSeminarMapper;

    @Autowired
    private CClassStudentMapper cClassStudentMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private CClassRoundMapper cClassRoundMapper;

    @Autowired
    private RoundMapper roundMapper;

    /**
     * 是否添加队伍信息
     */
    public static final int HAS_TEAMS = 0;
    /**
     * 是否添加讨论课信息
     */
    public static final int HAS_CCLASS_SEMINARS = 1;
    /**
     * 是否添加课程信息
     */
    public static final int HAS_COURSE = 2;

    private void hasSomethingFun(CClass cClass, int... hasSomething) {
        for (int i : hasSomething) {
            if (i == HAS_TEAMS) {
                List<Team> teams = teamMapper.findByCClassId(cClass.getId());
                cClass.setTeams(teams);
            }
            if (i == HAS_CCLASS_SEMINARS) {
                List<CClassSeminar> cClassSeminars = cClassSeminarMapper.findByCClassId(cClass.getCourseId());
                cClass.setcClassSeminars(cClassSeminars);
            }
            if (i == HAS_COURSE) {
                Course course = courseMapper.findById(cClass.getCourseId());
                cClass.setCourse(course);
            }
        }
    }

    /**
     * Description: 通过班级id查看班级
     *
     * @Author: WinstonDeng
     * @Date: 16:30 2018/12/19
     */

    public CClass getById(long cClassId, int... hasSomething) throws MyException {
        CClass cClass = cClassMapper.findById(cClassId);
        if (cClass == null) {
            throw new MyException("通过id查找班级错误！未找到班级", MyException.NOT_FOUND_ERROR);
        }
        hasSomethingFun(cClass, hasSomething);
        return cClass;
    }

    /**
     * Description: 通过课程id查班级
     *
     * @Author: WinstonDeng
     * @Date: 12:58 2018/12/16
     */
    public List<CClass> listByCourseId(long courseId, int... hasSomething) throws MyException {
        List<CClass> cClasses = cClassMapper.findByCourseId(courseId);

        for (CClass cClass : cClasses) {
            hasSomethingFun(cClass, hasSomething);
        }

        return cClasses;
    }

    /**
     * Description: 通过学生号和课程号查找该学生在该课程下属于哪个班
     *
     * @Author: 17Wang
     * @Time: 22:34 2018/12/22
     */
    public CClass getCClassByStudentIdAndCourseId(long studentId, long courseId) throws MyException {
        CClass cClass = cClassMapper.findByStudentIdAndCourseId(studentId, courseId);
        if (cClass == null) {
            System.out.println("CClassDao:"+studentId+" "+courseId);
            throw new MyException("通过学生号和课程号查找该学生在该课程下属于哪个班错误！不存在改行", MyException.NOT_FOUND_ERROR);
        }
        return cClass;
    }

    /**
     * Description: 新增班级
     *
     * @Author: WinstonDeng
     * @Date: 13:01 2018/12/16
     */
    public boolean addCClass(long courseId, CClass cClass) throws MyException {
        try {
            if (courseMapper.findById(courseId) == null) {
                throw new MyException("新建课程错误！未找到课程", MyException.NOT_FOUND_ERROR);
            }
            cClass.setCourseId(courseId);
            cClassMapper.insertCClass(cClass);
        } catch (Exception e) {
            throw new MyException("新建课程错误！数据库处理错误", MyException.ERROR);
        }
        return true;
    }

    /**
     * Description: 按id删除班级
     * ！！！！！！！！！！！！！缺失级联删除！！！！！！！！！！！！
     *
     * @Author: WinstonDeng
     * @Date: 11:30 2018/12/18
     */
    public boolean removeCClass(long cClassId) throws MyException {
        boolean flag = false;
        try {
            if (cClassMapper.findById(cClassId) == null) {
                throw new MyException("删除课程错误！未找到课程", MyException.NOT_FOUND_ERROR);
            }
            flag = cClassMapper.deleteCClassById(cClassId);
        } catch (Exception e) {
            throw new MyException("删除课程错误！数据库处理错误", MyException.ERROR);
        }
        return flag;
    }

    /**
     * Description: 新增班级学生
     *
     * @Author: WinstonDeng
     * @Date: 16:18 2018/12/19
     */
    public boolean addCClassStudent(CClassStudent cClassStudent) throws MyException {
        //如果不存在记录，才新增
        if(cClassStudentMapper.getByPrimaryKeys(cClassStudent.getcClassId(),cClassStudent.getStudentId())==null) {
            if(!cClassStudentMapper.insertCClassStudent(cClassStudent)){
                throw new MyException("新增班级学生错误！数据库处理错误", MyException.ERROR);
            }
        }
        return true;
    }

    /**
     * Description: 新增班级轮次 对应klass_round表
     *
     * @Author: WinstonDeng
     * @Date: 15:34 2018/12/20
     */
    public boolean addCClassRound(CClassRound cClassRound) throws MyException {
        try {
            cClassRoundMapper.insertCClassRound(cClassRound);
        } catch (Exception e) {
            throw new MyException("新增班级轮次错误！数据库处理出错", MyException.ERROR);
        }
        return true;
    }

    /**
     * Description: 批量修改klass_student的某一teamId为null
     *
     * @Author: WinstonDeng
     * @Date: 11:26 2018/12/25
     */

    public boolean updateTeamIdCollectionToNull(long teamId) throws Exception {
        //修改查找学生的方式
        if (studentMapper.findByTeamId(teamId) == null) {
            throw new MyException("批量修改班级学生队伍id错误！未找到该队伍下的记录", MyException.NOT_FOUND_ERROR);
        }
        if (!cClassStudentMapper.updateTeamIdCollectionToNull(teamId)) {
            throw new MyException("批量修改班级学生队伍id错误！数据库处理错误", MyException.ERROR);
        }
        return true;
    }
}
