package com.rbs.project.service;

import com.rbs.project.dao.*;
import com.rbs.project.exception.MyException;
import com.rbs.project.pojo.entity.Attendance;
import com.rbs.project.pojo.entity.CClassSeminar;
import com.rbs.project.pojo.entity.RoundScore;
import com.rbs.project.utils.FileLoadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.List;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 11:04 2018/12/20
 */
@Service
public class AttendanceService {
    @Autowired
    private AttendanceDao attendanceDao;

    @Autowired
    private TeamDao teamDao;

    @Autowired
    private CClassSeminarDao cClassSeminarDao;

    @Autowired
    private SeminarDao seminarDao;

    @Autowired
    private RoundScoreDao roundScoreDao;

    @Autowired
    private SeminarScoreDao seminarScoreDao;

    /**
     * Description: 获取一个班级下的有一个讨论课下的所有报名信息
     *
     * @Author: 17Wang
     * @Time: 11:34 2018/12/20
     */
    public List<Attendance> listAttendanceByCClassIdAndSeminarId(long cClassId, long seminarId) throws MyException {
        List<Attendance> attendances = attendanceDao.listByCClassIdAndSeminarId(cClassId, seminarId, AttendanceDao.HAS_CLASS_SEMINAR);
        for (Attendance attendance : attendances) {
            attendance.setTeam(teamDao.getTeamById(attendance.getTeamId(), TeamDao.HAS_CCLASS));
        }
        return attendances;
    }

    /**
     * Description: 报名一节讨论课
     *
     * @Author: 17Wang
     * @Time: 19:39 2018/12/21
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean addAttendance(long teamId, int teamOrder, long cClassId, long seminarId) throws Exception {
        //报名检查
        List<Attendance> attendances = attendanceDao.listByCClassIdAndSeminarId(cClassId, seminarId);
        for (Attendance attendance : attendances) {
            if (attendance.getTeamId() == teamId) {
                throw new MyException("报名讨论课失败！该队已报名过了该节讨论课", MyException.ERROR);
            }
            if (attendance.getTeamOrder() == teamOrder) {
                throw new MyException("报名讨论课失败！该位置已被其他队伍报名", MyException.ERROR);
            }
        }

        //主要查找到cClassSeminar的Id
        Long cClassSeminarId = cClassSeminarDao.findCClassSeminarByCClassIdAndSeminarId(cClassId, seminarId).getId();
        Attendance attendance = new Attendance();
        attendance.setcClassSeminarId(cClassSeminarId);
        attendance.setTeamId(teamId);
        attendance.setTeamOrder(teamOrder);

        //设置状态
        attendance.setPresent(Attendance.PRESENT_NO_START);
        attendanceDao.addAttendance(attendance);

        //新增roundScore 应该在新增Round和新增队伍的时候
        //新增seminarScore 增改在新建seminar的时候创建
        //seminarScoreDao.addSeminarScore(cClassSeminarId, teamId);

        return true;
    }

    /**
     * Description: 取消报名
     *
     * @Author: 17Wang
     * @Time: 22:33 2018/12/21
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteAttendance(long attendanceId) throws Exception {
        //查找有没有这个
        Attendance attendance = attendanceDao.getById(attendanceId);
        long cClassSeminarId = attendance.getcClassSeminarId();
        long teamId = attendance.getTeamId();
        long seminarId = cClassSeminarDao.getCClassSeminarById(cClassSeminarId).getSeminarId();
        long roundId = seminarDao.findSeminarById(seminarId).getRoundId();

        //删除讨论课成绩
        seminarScoreDao.deleteSeminarScoreByPrimaryKey(cClassSeminarId, teamId);

        //删除报名
        attendanceDao.deleteAttendanceById(attendanceId);

        return true;
    }

    /**
     * Description: 向attendanceId上传PPT
     *
     * @Author: WinstonDeng
     * @Date: 19:39 2018/12/25
     */
    public String uploadPPT(long attendanceId, String realPath, MultipartFile file) throws Exception {
        Attendance attendance = attendanceDao.getById(attendanceId);
        if (attendance == null) {
            throw new MyException("上传PPT错误！未找到该attendance记录", MyException.NOT_FOUND_ERROR);
        }
        String fileName = FileLoadUtils.upload(realPath, file);
        if (fileName.isEmpty()) {
            throw new MyException("上传PPT错误！", MyException.ERROR);
        }
        attendance.setPptName(fileName);
        attendance.setPptUrl(realPath);
        attendanceDao.updateAttendancePPT(attendance);
        return fileName;
    }

    /**
     * Description: 向attendanceId上传report
     *
     * @Author: WinstonDeng
     * @Date: 19:39 2018/12/25
     */
    public String uploadReport(long attendanceId, String realPath, MultipartFile file) throws Exception {
        Attendance attendance = attendanceDao.getById(attendanceId);
        if (attendance == null) {
            throw new MyException("上传Report错误！未找到该attendance记录", MyException.NOT_FOUND_ERROR);
        }
        String fileName = FileLoadUtils.upload(realPath, file);
        if (fileName.isEmpty()) {
            throw new MyException("上传Report错误！", MyException.ERROR);
        }
        attendance.setReportName(fileName);
        attendance.setReportUrl(realPath);
        attendanceDao.updateAttendanceReport(attendance);
        return fileName;
    }

    /**
     * Description: 通过id获得展示
     *
     * @Author: WinstonDeng
     * @Date: 22:05 2018/12/25
     */
    public Attendance getAttendanceById(long attendanceId, int... hasSomething) throws MyException {
        return attendanceDao.getById(attendanceId, hasSomething);
    }

    /**
     * Description: 修改当前展示为已经展示过了
     *
     * @Author: 17Wang
     * @Time: 14:48 2018/12/26
     */
    public boolean turnStatusToIsPresent(long attendanceId) throws Exception {
        //查询是否存在
        attendanceDao.getById(attendanceId);
        return attendanceDao.updateAttendancePresent(1,attendanceId);
    }

    /**
     * Description: 如果这个班级讨论课下没有这个展示，返回null，上层判断是不是null来继续操作
     * @Author: 17Wang
     * @Time: 15:07 2018/12/26
    */
    public Attendance getAttendanceBycClassIdAndSeminarIdAndTeamOrder(Attendance attendance,int ...hasSomething) throws MyException {
        try {
            return attendanceDao.getAttendanceBycClassIdAndSeminarIdAndTeamOrder(attendance,hasSomething);
        }catch (Exception e){
            return null;
        }
    }

    /**
     * Description: 查找一节班级讨论课下最大的TeamOrder，使用SQL语句查
     * @Author: 17Wang
     * @Time: 17:38 2018/12/26
    */
    public int getMaxTeamOrderByClassIdAndSeminarId(long classId,long seminarId){
        return attendanceDao.getMaxTeamOrderByClassIdAndSeminarId(classId,seminarId);
    }
}
