package com.rbs.project.dao;

import com.rbs.project.exception.MyException;
import com.rbs.project.mapper.AttendanceMapper;
import com.rbs.project.mapper.CClassSeminarMapper;
import com.rbs.project.mapper.TeamMapper;
import com.rbs.project.pojo.entity.Attendance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 11:36 2018/12/20
 */
@Repository
public class AttendanceDao {
    @Autowired
    private AttendanceMapper attendanceMapper;

    @Autowired
    private TeamMapper teamMapper;

    @Autowired
    private CClassSeminarMapper cClassSeminarMapper;

    public static final int HAS_TEAM = 0;

    public static final int HAS_CLASS_SEMINAR = 1;

    private void hasSomethingFun(Attendance attendance, int... hasSomething) {
        for (int i : hasSomething) {
            if (i == HAS_TEAM) {
                attendance.setTeam(teamMapper.findById(attendance.getTeamId()));
            }
            if (i == HAS_CLASS_SEMINAR) {
                attendance.setcClassSeminar(cClassSeminarMapper.findById(attendance.getcClassSeminarId()));
            }
        }
    }

    /**
     * Description: 通过attendanceId查询一个报名状况失败
     *
     * @Author: 17Wang
     * @Time: 22:39 2018/12/21
     */
    public Attendance getById(long attendanceId, int... hasSomething) throws MyException {
        Attendance attendance = attendanceMapper.findById(attendanceId);
        if (attendance == null) {
            throw new MyException("通过attendanceId查询一个报名状况失败！", MyException.NOT_FOUND_ERROR);
        }
        hasSomethingFun(attendance, hasSomething);
        return attendance;
    }

    /**
     * Description: 通过teamorder 获取一节班级讨论课下的展示信息
     * @Author: 17Wang
     * @Time: 15:09 2018/12/26
    */
    public Attendance getAttendanceBycClassIdAndSeminarIdAndTeamOrder(Attendance attendance, int... hasSomething) throws MyException {
        Attendance myAttendance = attendanceMapper.findByCClassIdAndSeminarIdAndTeamOrder(attendance);
        if (myAttendance == null) {
            throw new MyException("通过teamorder 获取一节班级讨论课下的展示信息AttendanceDao！数据不存在", MyException.NOT_FOUND_ERROR);
        }
        hasSomethingFun(myAttendance, hasSomething);
        return myAttendance;
    }

    /**
     * Description: 获得一个班级下的讨论课的报名信息
     *
     * @Author: 17Wang
     * @Time: 13:01 2018/12/21
     */
    public List<Attendance> listByCClassIdAndSeminarId(long cClassId, long seminarId, int... hasSomething) {
        List<Attendance> attendances = attendanceMapper.findByCClassIdAndSeminarId(cClassId, seminarId);
        for (Attendance attendance : attendances) {
            hasSomethingFun(attendance, hasSomething);
        }
        return attendances;
    }

    /**
     * Description: 新增报名
     *
     * @Author: 17Wang
     * @Time: 20:03 2018/12/21
     */
    public boolean addAttendance(Attendance attendance) {
        return attendanceMapper.insertAttendance(attendance);
    }

    /**
     * Description: 通过主键删除展示信息
     *
     * @Author: 17Wang
     * @Time: 23:14 2018/12/21
     */
    public boolean deleteAttendanceById(long attendanceId) throws Exception {
        return attendanceMapper.deleteAttendanceById(attendanceId);
    }


    /**
     * Description: 通过id修改attendance PPT
     *
     * @Author: WinstonDeng
     * @Date: 19:48 2018/12/25
     */
    public boolean updateAttendancePPT(Attendance attendance) throws Exception {
        if (!attendanceMapper.updateAttendancePPT(attendance)) {
            throw new MyException("修改attendance错误！数据库处理错误", MyException.ERROR);
        }
        return true;
    }

    /**
     * Description: 通过id修改attendance Report
     *
     * @Author: WinstonDeng
     * @Date: 19:48 2018/12/25
     */
    public boolean updateAttendanceReport(Attendance attendance) throws Exception {
        if (!attendanceMapper.updateAttendanceReport(attendance)) {
            throw new MyException("修改attendance错误！数据库处理错误", MyException.ERROR);
        }
        return true;
    }

    /**
     * Description:修改展示的状态 0为未展示 1为已展示
     *
     * @Author: 17Wang
     * @Time: 14:51 2018/12/26
     */
    public boolean updateAttendancePresent(int present, long attendanceId) throws Exception {
        if (!attendanceMapper.updatePresentById(present, attendanceId)) {
            throw new MyException("修改展示的状态！数据库处理错误", MyException.ERROR);
        }
        return true;
    }

    /**
     * Description: 查找一节班级讨论课下最大的TeamOrder，使用SQL语句查
     * @Author: 17Wang
     * @Time: 17:38 2018/12/26
     */
    public int getMaxTeamOrderByClassIdAndSeminarId(long classId,long seminarId){
        return attendanceMapper.findMaxTeamOrderByClassIdAndSeminarId(classId,seminarId);
    }
}
