package com.rbs.project.controller;

import com.rbs.project.dao.AttendanceDao;
import com.rbs.project.exception.MyException;
import com.rbs.project.controller.dto.CreateSeminarDTO;
import com.rbs.project.controller.dto.UpdateSeminarDTO;
import com.rbs.project.pojo.entity.*;
import com.rbs.project.controller.vo.CClassInfoVO;
import com.rbs.project.controller.vo.QuestionInfoVO;
import com.rbs.project.service.*;
import com.rbs.project.socket.StudentPool;
import com.rbs.project.utils.JsonUtils;
import com.rbs.project.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: WinstonDeng
 * @Description: OOAD_Course_ManageSystem
 * @Date: Created in 11:43 2018/12/18
 * @Modified by:
 */
@RestController
@RequestMapping("/seminar")
public class SeminarController {
    @Autowired
    private SeminarService seminarService;

    @Autowired
    private CClassSeminarService cClassSeminarService;

    @Autowired
    private CClassService cClassService;

    @Autowired
    private CourseService courseService;

    /**
     * Description: 新增讨论课
     *
     * @Author: WinstonDeng
     * @Date: 13:04 2018/12/18
     */
    @PostMapping
    @ResponseBody
    public ResponseEntity<Long> createSeminar(@RequestBody CreateSeminarDTO createSeminarDTO) throws Exception {
        //初始化新增讨论课id
        long createSeminarId = -1;
        Seminar seminar = new Seminar();
        if (createSeminarDTO.getCourseId() == null) {
            throw new MyException("courseId不能为空", MyException.ID_FORMAT_ERROR);
        }
        //如果是从课程，直接报错
        if(courseService.getCourseById(createSeminarDTO.getCourseId()).getSeminarMainCourseId()!=0){
            throw new MyException("从课程无法创建讨论课",MyException.ERROR);
        }
        seminar.setCourseId(createSeminarDTO.getCourseId());
        if (createSeminarDTO.getRoundId() == null) {
            seminar.setRoundId(-1);
        } else {
            seminar.setRoundId(createSeminarDTO.getRoundId());
        }
        seminar.setName(createSeminarDTO.getName());
        seminar.setIntro(createSeminarDTO.getIntro());
        seminar.setMaxTeam(createSeminarDTO.getMaxTeam());
        seminar.setVisible(createSeminarDTO.getVisible());
        seminar.setEnrollStartTime(JsonUtils.StringToTimestamp(createSeminarDTO.getEnrollStartTime()));
        seminar.setEnrollEndTime(JsonUtils.StringToTimestamp(createSeminarDTO.getEnrollEndTime()));
        //获得主键
        createSeminarId = seminarService.addSemianr(seminar,true);
        return ResponseEntity.ok().body(createSeminarId);
    }

    /**
     * Description: 按id和班级获取讨论课
     *
     * @Author: WinstonDeng
     * @Date: 23:25 2018/12/19
     */
    @GetMapping("/{seminarId}/class/{classId}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getSeminarById(@PathVariable("seminarId") long seminarId, @PathVariable("classId") long cClassId) throws MyException {
        if (seminarId == 0) {
            throw new MyException("seminarId不能为空", MyException.ID_FORMAT_ERROR);
        }
        Seminar seminar = seminarService.getSeminarById(seminarId);
        Map<String, Object> seminarView = new HashMap<>();
        if (cClassId == 0) {
            throw new MyException("classId不能为空", MyException.ID_FORMAT_ERROR);
        }
        //转换格式
        seminarView.put("seminarId", seminar.getId());
        seminarView.put("courseId", seminar.getCourseId());
        seminarView.put("courseName", seminar.getCourse().getName());
        seminarView.put("roundId", seminar.getRoundId());
        seminarView.put("roundSerial", seminar.getRound().getSerial());
        seminarView.put("seminarTopic", seminar.getName());
        seminarView.put("seminarSerial", seminar.getSerial());
        seminarView.put("seminarIntro", seminar.getIntro());
        seminarView.put("visible", seminar.getVisible());
        seminarView.put("enrollStartTime", JsonUtils.TimestampToString(seminar.getEnrollStartTime()));
        seminarView.put("enrollEndTime", JsonUtils.TimestampToString(seminar.getEnrollEndTime()));
        seminarView.put("maxTeam", seminar.getMaxTeam());
        //状态
        seminarView.put("status", cClassSeminarService.getCClassSeminar(cClassId, seminarId).getStatus());
        return ResponseEntity.ok().body(seminarView);
    }

    /**
     * Description: 按id获取讨论课
     *
     * @Author: WinstonDeng
     * @Date: 23:25 2018/12/19
     */
    @GetMapping("/{seminarId}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getSeminarBySeminarId(@PathVariable("seminarId") long seminarId) throws MyException {
        if ((Long) seminarId == null) {
            throw new MyException("seminarId不能为空", MyException.ID_FORMAT_ERROR);
        }
        Seminar seminar = seminarService.getSeminarById(seminarId);
        Map<String, Object> seminarView = new HashMap<>();
        //转换格式
        seminarView.put("seminarId", seminar.getId());
        seminarView.put("courseId", seminar.getCourseId());
        seminarView.put("courseName", seminar.getCourse().getName());
        seminarView.put("roundId", seminar.getRoundId());
        seminarView.put("roundSerial", seminar.getRound().getSerial());
        seminarView.put("seminarTopic", seminar.getName());
        seminarView.put("seminarSerial", seminar.getSerial());
        seminarView.put("seminarIntro", seminar.getIntro());
        seminarView.put("visible", seminar.getVisible());
        seminarView.put("enrollStartTime", JsonUtils.TimestampToString(seminar.getEnrollStartTime()));
        seminarView.put("enrollEndTime", JsonUtils.TimestampToString(seminar.getEnrollEndTime()));
        seminarView.put("maxTeam", seminar.getMaxTeam());
        return ResponseEntity.ok().body(seminarView);
    }

    /**
     * Description: 删除讨论课
     *
     * @Author: WinstonDeng
     * @Date: 13:04 2018/12/18
     */
    @DeleteMapping("/{seminarId}")
    @ResponseBody
    public ResponseEntity<Boolean> deleteSeminarById(@PathVariable("seminarId") long seminarId) throws Exception {
        if (seminarId == 0) {
            throw new MyException("seminarId不能为空", MyException.ID_FORMAT_ERROR);
        }
        long courseId=seminarService.getSeminarById(seminarId).getCourseId();
        //如果是从课程，直接报错
        if(courseService.getCourseById(courseId).getSeminarMainCourseId()!=0){
            throw new MyException("从课程无法删除讨论课",MyException.ERROR);
        }
        return ResponseEntity.ok().body(seminarService.removeSeminarById(seminarId,true));
    }

    /**
     * Description: 修改讨论课信息
     *
     * @Author: WinstonDeng
     * @Date: 13:06 2018/12/18
     */
    @PutMapping("/{seminarId}")
    @ResponseBody
    public ResponseEntity<Boolean> updateSeminarById(@PathVariable("seminarId") long seminarId, @RequestBody UpdateSeminarDTO updateSeminarDTO) throws Exception {
        if (seminarId == 0) {
            throw new MyException("seminarId不能为空", MyException.ID_FORMAT_ERROR);
        }
        long courseId=seminarService.getSeminarById(seminarId).getCourseId();
        //如果是从课程，直接报错
        if(courseService.getCourseById(courseId).getSeminarMainCourseId()!=0){
            throw new MyException("从课程无法修改讨论课",MyException.ERROR);
        }
        //DTO转Entity
        Seminar seminar = new Seminar();
        seminar.setId(seminarId);
        if (updateSeminarDTO.getRoundId() == null) {
            seminar.setRoundId(-1);
        } else {
            seminar.setRoundId(updateSeminarDTO.getRoundId());
        }
        seminar.setName(updateSeminarDTO.getTopic());
        seminar.setIntro(updateSeminarDTO.getIntro());
        seminar.setMaxTeam(updateSeminarDTO.getMaxTeam());
        seminar.setVisible(updateSeminarDTO.getVisible());
        seminar.setEnrollStartTime(JsonUtils.StringToTimestamp(updateSeminarDTO.getEnrollStartTime()));
        seminar.setEnrollEndTime(JsonUtils.StringToTimestamp(updateSeminarDTO.getEnrollEndTime()));
        return ResponseEntity.ok().body(seminarService.updateSeminar(seminar));
    }

    /**
     * Description: 修改班级讨论课 reportDDL
     *
     * @Author: WinstonDeng
     * @Date: 11:27 2018/12/19
     */
    @PutMapping("/{seminarId}/class/{classId}/reportDDL")
    @ResponseBody
    public ResponseEntity<Boolean> updateCClassSeminarReportDDL(@PathVariable("seminarId") long seminarId,
                                                                @PathVariable("classId") long cClassId,
                                                                @RequestBody Map<String, String> updateMap) throws MyException {
        if ((Long) seminarId == null) {
            throw new MyException("seminarId不能为空", MyException.ID_FORMAT_ERROR);
        }
        if ((Long) cClassId == null) {
            throw new MyException("classId不能为空", MyException.ID_FORMAT_ERROR);
        }
        final String reportDDL = "reportDDL";
        CClassSeminar cClassSeminar = cClassSeminarService.getCClassSeminar(cClassId, seminarId);
        if (updateMap.get(reportDDL).isEmpty()) {
            throw new MyException("reportDDL不能为空", MyException.ERROR);
        }
        cClassSeminar.setReportDDL(JsonUtils.StringToTimestamp(updateMap.get(reportDDL)));
        return ResponseEntity.ok().body(cClassSeminarService.updateCClassSeminar(cClassSeminar));
    }

    /**
     * Description: 修改班级讨论课 status
     *
     * @Author: WinstonDeng
     * @Date: 11:27 2018/12/19
     */
    @PutMapping("/{seminarId}/class/{classId}/status")
    @ResponseBody
    public ResponseEntity<Boolean> updateCClassSeminarStatus(@PathVariable("seminarId") long seminarId,
                                                             @PathVariable("classId") long cClassId,
                                                             @RequestBody Map<String, String> updateMap) throws MyException {
        if ((Long) seminarId == null) {
            throw new MyException("seminarId不能为空", MyException.ID_FORMAT_ERROR);
        }
        if ((Long) cClassId == null) {
            throw new MyException("classId不能为空", MyException.ID_FORMAT_ERROR);
        }
        final String status = "status";
        CClassSeminar cClassSeminar = cClassSeminarService.getCClassSeminar(cClassId, seminarId);
        if (updateMap.get(status).isEmpty()) {
            throw new MyException("status不能为空", MyException.ERROR);
        }
        cClassSeminar.setStatus(Integer.parseInt(updateMap.get(status)));
        cClassSeminar.setReportDDL(JsonUtils.StringToTimestamp("2019-02-01 23:59:59"));
        return ResponseEntity.ok().body(cClassSeminarService.updateCClassSeminar(cClassSeminar));
    }

    /**
     * Description: 查看班级下讨论课的所有提问
     *
     * @Author: WinstonDeng
     * @Date: 16:05 2018/12/22
     */
    @GetMapping("/{seminarId}/class/{classId}/question")
    @ResponseBody
    public ResponseEntity<List<QuestionInfoVO>> listAllQuestions(@PathVariable("seminarId") long seminarId, @PathVariable("classId") long cClassId) throws MyException {
        if ((Long) seminarId == null) {
            throw new MyException("seminarId不能为空", MyException.ID_FORMAT_ERROR);
        }
        if ((Long) cClassId == null) {
            throw new MyException("classId不能为空", MyException.ID_FORMAT_ERROR);
        }
        List<Question> questions = cClassSeminarService.listAllQuestionsByCClassIdAndSeminarId(cClassId, seminarId);
        List<QuestionInfoVO> questionInfoVOS = new ArrayList<>();
        for (Question question : questions) {
            questionInfoVOS.add(new QuestionInfoVO(question));
        }
        return ResponseEntity.ok().body(questionInfoVOS);
    }

    /**
     * Description: 获取正在进行的讨论课
     *
     * @Author: WinstonDeng
     * @Date: 23:10 2018/12/25
     */
    @GetMapping("/underway")
    @ResponseBody
    public ResponseEntity<List<Map<String, Object>>> getUnderWaySeminarId() throws MyException {
        Teacher teacher = (Teacher) UserUtils.getNowUser();
        List<CClassSeminar> cClassSeminars = cClassSeminarService.listAllUnderWaySeminarsByTeacherId(teacher.getId());
        List<Map<String, Object>> maps = new ArrayList<>();
        for (CClassSeminar cClassSeminar : cClassSeminars) {
            Map<String, Object> map = new HashMap<>();
            map.put("seminarId", cClassSeminar.getSeminarId());
            CClass cClass = cClassService.getClassById(cClassSeminar.getcClassId());
            CClassInfoVO cClassInfoVO = new CClassInfoVO(cClass);
            map.put("class", cClassInfoVO);
            maps.add(map);
        }
        return ResponseEntity.ok().body(maps);
    }
}
