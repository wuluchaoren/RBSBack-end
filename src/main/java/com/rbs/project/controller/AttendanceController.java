package com.rbs.project.controller;

import com.rbs.project.exception.MyException;
import com.rbs.project.pojo.entity.Attendance;
import com.rbs.project.pojo.entity.SeminarScore;
import com.rbs.project.controller.vo.AttendanceVO;
import com.rbs.project.service.AttendanceService;
import com.rbs.project.service.ScoreService;
import com.rbs.project.utils.FileLoadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 11:04 2018/12/20
 */
@RestController
@RequestMapping("/attendance")
public class AttendanceController {
    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private ScoreService scoreService;

    /**
     * Description: 获取一个班级下的一节讨论课的所有的报名
     *
     * @Author: 17Wang
     * @Time: 17:57 2018/12/22
     */
    @GetMapping
    @ResponseBody
    public List<AttendanceVO> listAttendanceByCClassIdAndSeminarId(@RequestParam("cClassId") long cClassId, @RequestParam("seminarId") long seminarId) throws MyException {
        List<Attendance> attendances = attendanceService.listAttendanceByCClassIdAndSeminarId(cClassId, seminarId);
        List<AttendanceVO> attendanceVOS = new ArrayList<>();
        for (Attendance attendance : attendances) {
            SeminarScore seminarScore=scoreService.getSeminarScoreByClassSeminarIdAndTeamId(attendance.getcClassSeminarId(),attendance.getTeamId());
            attendanceVOS.add(new AttendanceVO(attendance).setPresentationScore(seminarScore.getPresentationScore()));
        }
        Collections.sort(attendanceVOS);
        return attendanceVOS;
    }

    /**
     * Description:报名一节讨论课
     *
     * @Author: 17Wang
     * @Time: 17:57 2018/12/22
     */
    @PostMapping
    @ResponseBody
    public ResponseEntity<Boolean> addAttendance(@RequestBody Map<String, Long> map) throws Exception {
        Long cClassId = map.get("classId");
        Long seminarId = map.get("seminarId");
        Long teamId = map.get("teamId");
        Integer teamOrder = Math.toIntExact(map.get("teamOrder"));
        if (cClassId == null || seminarId == null || teamId == null || teamOrder == null) {
            throw new MyException("存在为空的字段，请检查字段命名和赋值", MyException.NOT_FOUND_ERROR);
        }
        return ResponseEntity.ok(attendanceService.addAttendance(teamId, teamOrder, cClassId, seminarId));
    }

    /**
     * Description: 取消讨论课的报名
     *
     * @Author: 17Wang
     * @Time: 17:57 2018/12/22
     */
    @DeleteMapping("/{attendanceId}")
    @ResponseBody
    public ResponseEntity<Boolean> cancelAttendance(@PathVariable("attendanceId") Long attendanceId) throws Exception {
        if (attendanceId == null) {
            throw new MyException("存在为空的字段，请检查字段命名和赋值_attendanceId", MyException.NOT_FOUND_ERROR);
        }
        return ResponseEntity.ok(attendanceService.deleteAttendance(attendanceId));
    }

    /**
     * Description: 上传PPT
     *
     * @Author: WinstonDeng
     * @Date: 19:20 2018/12/25
     */
    @PostMapping("/{attendanceId}/ppt")
    @ResponseBody
    public ResponseEntity<String> uploadPPT(HttpServletRequest request, @PathVariable("attendanceId") long attendanceId, @RequestParam("ppt") MultipartFile file) throws Exception {
        if((Long)attendanceId==null){
            throw new MyException("attendanceId不能为空",MyException.ID_FORMAT_ERROR);
        }
        return ResponseEntity.ok().body(attendanceService.uploadPPT(attendanceId,request.getServletContext().getRealPath("/resources/ppt/"),file));
    }

    /**
     * Description: 下载PPT
     * @Author: WinstonDeng
     * @Date: 20:36 2018/12/25
     */
    @GetMapping("/{attendanceId}/ppt")
    @ResponseBody
    public ResponseEntity<String> downloadPPT(HttpServletRequest request,HttpServletResponse response,@PathVariable("attendanceId") long attendanceId)throws Exception{
        if((Long)attendanceId==null){
            throw new MyException("attendanceId不能为空",MyException.ID_FORMAT_ERROR);
        }
        Attendance attendance=attendanceService.getAttendanceById(attendanceId);
        return ResponseEntity.ok().body(FileLoadUtils.downloadFile(request,response,attendance.getPptUrl(),attendance.getPptName()));
    }

    /**
     * Description: 上传report
     *
     * @Author: WinstonDeng
     * @Date: 19:20 2018/12/25
     */
    @PostMapping("/{attendanceId}/report")
    @ResponseBody
    public ResponseEntity<String> uploadReport(HttpServletRequest request, @PathVariable("attendanceId") long attendanceId, @RequestParam("report") MultipartFile file) throws Exception {
        if((Long)attendanceId==null){
            throw new MyException("attendanceId不能为空",MyException.ID_FORMAT_ERROR);
        }
        return ResponseEntity.ok().body(attendanceService.uploadReport(attendanceId,request.getServletContext().getRealPath("/resources/report/"),file));
    }

    /**
     * Description: 下载PPT
     * @Author: WinstonDeng
     * @Date: 20:36 2018/12/25
     */
    @GetMapping("/{attendanceId}/report")
    @ResponseBody
    public ResponseEntity<String> downloadReport(HttpServletRequest request,HttpServletResponse response,@PathVariable("attendanceId") long attendanceId)throws Exception{
        if((Long)attendanceId==null){
            throw new MyException("attendanceId不能为空",MyException.ID_FORMAT_ERROR);
        }
        Attendance attendance=attendanceService.getAttendanceById(attendanceId);
        return ResponseEntity.ok().body(FileLoadUtils.downloadFile(request,response,attendance.getReportUrl(),attendance.getReportName()));
    }

}
