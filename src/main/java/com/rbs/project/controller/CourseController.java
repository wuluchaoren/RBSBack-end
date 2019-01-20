package com.rbs.project.controller;

import com.rbs.project.exception.MyException;
import com.rbs.project.controller.dto.CourseAndStrategyDTO;
import com.rbs.project.controller.dto.CreateCClassDTO;
import com.rbs.project.pojo.entity.*;
import com.rbs.project.pojo.strategy.CourseMemberLimitStrategy;
import com.rbs.project.controller.vo.*;
import com.rbs.project.service.*;
import com.rbs.project.utils.FileLoadUtils;
import com.rbs.project.utils.JsonUtils;
import com.rbs.project.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: WinstonDeng
 * @Description: OOAD_Course_ManageSystem
 * @Date: Created in 14:38 2018/12/16
 * @Modified by:
 */
@RestController
@RequestMapping("/course")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @Autowired
    private CClassService cClassService;

    @Autowired
    private RoundService roundService;

    @Autowired
    private TeamService teamService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private ShareService shareService;

    @Autowired
    private ApplicationService applicationService;

    /**
     * Description: 新建课程
     * 修改VO改成了DTO
     *
     * @Author: 17Wang
     * @Time: 16:46 2018/12/18
     */
    @PostMapping
    @ResponseBody
    public ResponseEntity<Boolean> createCourse(@RequestBody CourseAndStrategyDTO courseAndStrategyDTO) throws Exception {
        //课程基本信息
        Course course = new Course();
        course.setName(courseAndStrategyDTO.getName());
        course.setIntro(courseAndStrategyDTO.getIntro());
        course.setPresentationPercentage(courseAndStrategyDTO.getPresentationPercentage());
        course.setQuestionPercentage(courseAndStrategyDTO.getQuestionPercentage());
        course.setReportPercentage(courseAndStrategyDTO.getReportPercentage());
        course.setTeamStartTime(JsonUtils.StringToTimestamp(courseAndStrategyDTO.getTeamStartTime()));
        course.setTeamEndTime(JsonUtils.StringToTimestamp(courseAndStrategyDTO.getTeamEndTime()));

        course.setMemberLimitStrategy(courseAndStrategyDTO.getMemberLimitStrategy());
        course.setCourseMemberLimitStrategies(courseAndStrategyDTO.getCourseMemberLimitStrategies());

        List<List<Course>> lists = new ArrayList<>();
        //冲突课程List套List  因此得赋初值
        if (courseAndStrategyDTO.getConflictCourses() != null) {
            for (List<Long> longs : courseAndStrategyDTO.getConflictCourses()) {
                List<Course> courses = new ArrayList<>();
                for (Long l : longs) {
                    Course temp = new Course();
                    temp.setId(l);
                    courses.add(temp);
                }
                lists.add(courses);
            }
            course.setConflictCourses(lists);
        } else {
            course.setConflictCourses(new ArrayList<>());
        }
        //判断表示是否存在
        int flag;
        if (courseAndStrategyDTO.getCourseMemberLimitFlag() != null) {
            flag = courseAndStrategyDTO.getCourseMemberLimitFlag();
        } else {
            flag = -1;
        }


        if (course.getName() == null) {
            throw new MyException("课程名不能为空", MyException.ERROR);
        }
        if (course.getTeamStartTime() == null) {
            throw new MyException("组队开始时间不能为空", MyException.ERROR);
        }
        if (course.getTeamEndTime() == null) {
            throw new MyException("组队结束时间不能为空", MyException.ERROR);
        }
        if (course.getPresentationPercentage() == null ||
                course.getQuestionPercentage() == null ||
                course.getReportPercentage() == null) {
            throw new MyException("计算分数规则不能为空", MyException.ERROR);
        }
        return ResponseEntity.ok(courseService.createCourse(course, flag));
    }

    /**
     * Description: 获取所有课程
     *
     * @Author: 17Wang
     * @Time: 17:38 2018/12/23
     */
    @GetMapping("/all")
    @ResponseBody
    public List<CourseInfoVO> listAllCourses() {
        List<CourseInfoVO> courseInfoVOS = new ArrayList<>();
        for (Course course : courseService.listAllCourses()) {
            courseInfoVOS.add(new CourseInfoVO(course));
        }
        return courseInfoVOS;
    }

    /**
     * Description: 获取我的所有课程
     *
     * @Author: 17Wang
     * @Time: 21:29 2018/12/22
     */
    @GetMapping
    @ResponseBody
    public List<CourseInfoVO> listMyCourses() throws MyException {
        List<CourseInfoVO> courseInfoVOS = new ArrayList<>();
        for (Course course : courseService.listMyCourses()) {
            courseInfoVOS.add(new CourseInfoVO(course));
        }
        return courseInfoVOS;
    }

    /**
     * Description: 通过courseId获取一个课程
     *
     * @Author: 17Wang
     * @Time: 21:30 2018/12/22
     */
    @GetMapping("/{courseId}")
    @ResponseBody
    public ResponseEntity<CourseAndStrategyVO> getCourseById(@PathVariable("courseId") long courseId) throws MyException {
        Course course = courseService.getCourseById(courseId);
        List<CourseMemberLimitStrategyVO> courseMemberLimitStrategyVOS = new ArrayList<>();
        try {
            if (course.getCourseMemberLimitStrategies() != null) {
                for (CourseMemberLimitStrategy courseMemberLimitStrategy : course.getCourseMemberLimitStrategies()) {
                    courseMemberLimitStrategyVOS.add(new CourseMemberLimitStrategyVO(courseMemberLimitStrategy).setCourseName(courseService.getCourseById(courseMemberLimitStrategy.getCourseId(), -1).getName()));
                }
            }
        }catch (Exception e){
            System.out.println("courseController:小问题");
        }
        CourseAndStrategyVO courseAndStrategyVO = new CourseAndStrategyVO(course)
                .setCourseMemberLimitStrategyVOS(courseMemberLimitStrategyVOS)
                .setCourseMemberLimitStrategyStyle(courseService.judgeCourseMemberLimitIsAndStyle(courseId));
        return ResponseEntity.ok(courseAndStrategyVO);
    }

    /**
     * Description: 删除一个课程
     *
     * @Author: 17Wang
     * @Time: 21:40 2018/12/22
     */
    @DeleteMapping("/{courseId}")
    @ResponseBody
    public ResponseEntity<Boolean> deleteCourse(@PathVariable("courseId") long courseId) throws Exception {
        return ResponseEntity.ok(courseService.deleteCourseById(courseId));
    }

    /**
     * Description: 获取课程下的所有小组（主课程）
     * 如果他是从课程？=====================================================================================================！！！！！！
     * 搞定，课程下查找有几个班，再把每个班(klass_team）查找有哪些队伍，再把这些队伍信息拿出来
     *
     * @Author: 17Wang
     * @Time: 11:20 2018/12/23
     */
    @GetMapping("/{courseId}/team")
    @ResponseBody
    public List<TeamBaseInfoVO> listTeamAtCourse(@PathVariable("courseId") long courseId) throws MyException {
        List<Team> teams = teamService.listTeamByCourseId(courseId);
        List<TeamBaseInfoVO> teamBaseInfoVOS = new ArrayList<>();
        for (Team team : teams) {
            teamBaseInfoVOS.add(new TeamBaseInfoVO(team));
        }
        return teamBaseInfoVOS;
    }

    /**
     * Description: 查找我在这门课下的队伍
     * 搞定！ 通过courseid和我的id查找我在哪个班级下面，再通过班级号和学号查找队伍信息
     *
     * @Author: 17Wang
     * @Time: 11:44 2018/12/23
     */
    @GetMapping("/{courseId}/team/mine")
    @ResponseBody
    public Map<String, Object> getMyTeam(@PathVariable("courseId") long courseId) throws MyException {
        Team team = teamService.getTeamByCourseIdAndStudentId(courseId);
        Map<String, Object> map = new HashMap<>();
        map.put("teamInfo", new TeamBaseInfoVO(team));

        Course course = courseService.getCourseById(courseId);
        map.put("course", new CourseInfoVO(course));
        CClass cClass = cClassService.getCClassByStudentIdAndCourseId(UserUtils.getNowUser().getId(), courseId);
        map.put("class", new CClassInfoVO(cClass));
        map.put("leader", new UserVO(team.getLeader()));
        map.put("isLeader", UserUtils.getNowUser().getId() == team.getLeaderId() ? true : false);

        List<UserVO> userVOS = new ArrayList<>();
        for (Student student : team.getStudents()) {
            if (student.getId() != team.getLeader().getId()) {
                userVOS.add(new UserVO(student));
            }
        }

        map.put("members", userVOS);
        return map;
    }

    /**
     * Description: 查找一个课程下未组队的学生
     *
     * @Author: 17Wang
     * @Time: 12:04 2018/12/23
     */
    @GetMapping("/{courseId}/team/free")
    @ResponseBody
    public List<UserVO> listFreeStudentAtCourse(@PathVariable("courseId") long courseId) {
        List<Student> students = studentService.listByCourseIdAndTeamId(courseId);
        List<UserVO> userVOS = new ArrayList<>();
        for (Student student : students) {
            userVOS.add(new UserVO(student));
        }
        return userVOS;
    }

    /**
     * Description: 创建班级时，上传学生名单
     *
     * @Author: WinstonDeng
     * @Date: 17:18 2018/12/19
     */
    @PostMapping("/{courseId}/class/studentfile")
    @ResponseBody
    public ResponseEntity<String> uploadStudentFile(HttpServletRequest request, @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok().body(FileLoadUtils.upload(request.getServletContext().getRealPath("/studentfile/"), file));
    }

    /**
     * Description: 创建班级，如果有学生名单（DTO里fileName非空），则解析存库
     *
     * @Author: WinstonDeng
     * @Date: 11:11 2018/12/12
     */
    @PostMapping("/{courseId}/class")
    @ResponseBody
    public ResponseEntity<Long> createcClassInCoursePage(HttpServletRequest request, @PathVariable("courseId") long courseId, @RequestBody CreateCClassDTO createCClassDTO) throws MyException {
        //初始化为-1 表示新建失败
        long cclassId = -1;
        //设置班级基本信息
        CClass cClass = new CClass();
        cClass.setCourseId(courseId);
        cClass.setGrade(createCClassDTO.getGrade());
        cClass.setSerial(createCClassDTO.getSerial());
        cClass.setTime(createCClassDTO.getTime());
        cClass.setPlace(createCClassDTO.getClassroom());
        //获得新建的课程主键
        cclassId = cClassService.createCClass(courseId, cClass);
        //解析学生名单
        if (cclassId != -1) {
            //调用解析学生名单的函数

            if (createCClassDTO.getFileName() != null) {
                cClassService.transStudentListFileToDataBase(cclassId, request.getServletContext().getRealPath("/resources/studentfile/"), createCClassDTO.getFileName());
            }
        }
        return ResponseEntity.ok().body(cclassId);
    }

    /**
     * Description: 通过课程查找班级列表
     *
     * @Author: WinstonDeng
     * @Date: 10:50 2018/12/17
     */
    @GetMapping("/{courseId}/class")
    @ResponseBody
    public ResponseEntity<List<CClassInfoVO>> listAllCClassesInCoursePage(@PathVariable("courseId") long courseId) throws MyException {
        List<CClassInfoVO> cClassInfoVOS = new ArrayList<>();
        List<CClass> cClasses = cClassService.listCClassesByCourseId(courseId);
        for (CClass cClass
                : cClasses) {
            cClassInfoVOS.add(new CClassInfoVO(cClass));
        }
        return ResponseEntity.ok().body(cClassInfoVOS);
    }

    /**
     * Description: 在课程下查看讨论课
     *
     * @Author: WinstonDeng
     * @Date: 17:08 2018/12/21
     */
    @GetMapping("/{courseId}/seminars")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> listAllSeminarsUnderRoundInCoursePage(@PathVariable("courseId") long courseId) throws MyException {
        if (courseId == 0) {
            throw new MyException("课程id不能为空", MyException.ERROR);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("courseId", courseId);
        map.put("courseName", courseService.getCourseById(courseId).getName());
        List<CClass> cClasses = cClassService.listCClassesByCourseId(courseId);
        List<CClassInfoVO> cClassInfoVOS = new ArrayList<>();
        for (CClass cClass
                : cClasses) {
            cClassInfoVOS.add(new CClassInfoVO(cClass));
        }
        map.put("cClasses", cClassInfoVOS);
        List<Round> rounds = roundService.listRoundsByCourseId(courseId);
        List<RoundInfoVO> roundInfoVOS = new ArrayList<>();
        for (Round round
                : rounds) {
            roundInfoVOS.add(new RoundInfoVO(round));
        }
        map.put("rounds", roundInfoVOS);
        return ResponseEntity.ok().body(map);
    }

    /**
     * Description: 按课程id获取所有共享信息
     *
     * @Author: WinstonDeng
     * @Date: 17:28 2018/12/23
     */
    @GetMapping("/{courseId}/share")
    @ResponseBody
    public ResponseEntity<List<ShareInfoVO>> listAllSharesByCourseId(@PathVariable("courseId") long courseId) throws Exception {
        List<ShareInfoVO> shareInfoVOS = new ArrayList<>();
        if (courseId == 0) {
            throw new MyException("courseId不能为空", MyException.ID_FORMAT_ERROR);
        }
        //  1.共享讨论课
        //作为主课程
        List<ShareSeminarApplication> seminarSharesInMainCourse = shareService.listAllShareSeminarsInMainCourseByCourseId(courseId);
        for (ShareSeminarApplication shareSeminarApplication : seminarSharesInMainCourse) {
            ShareInfoVO shareInfoVO = new ShareInfoVO();
            shareInfoVO.setInfo("主课程");
            shareInfoVO.setShareType("共享讨论课");
            shareInfoVO.setShareId(shareSeminarApplication.getId());
            shareInfoVO.setSubCourseId(shareSeminarApplication.getSubCourseId());
            shareInfoVO.setSubCourseName(shareSeminarApplication.getSubCourse().getName());
            shareInfoVO.setSubTeacherName(shareSeminarApplication.getSubCourseTeacher().getTeacherName());
            shareInfoVOS.add(shareInfoVO);
        }
        //作为从课程
        List<ShareSeminarApplication> seminarSharesInSubCourse = shareService.listAllShareSeminarsInSubCourseByCourseId(courseId);
        for (ShareSeminarApplication shareSeminarApplication : seminarSharesInSubCourse) {
            ShareInfoVO shareInfoVO = new ShareInfoVO();
            shareInfoVO.setInfo("从课程");
            shareInfoVO.setShareType("共享讨论课");
            shareInfoVO.setShareId(shareSeminarApplication.getId());
            shareInfoVO.setMainCourseId(shareSeminarApplication.getMainCourseId());
            shareInfoVO.setMainCourseName(shareSeminarApplication.getMainCourse().getName());
            shareInfoVO.setMainTeacherName(shareSeminarApplication.getMainCourseTeacher().getTeacherName());
            shareInfoVOS.add(shareInfoVO);
        }
        //  2.共享分组
        //作为主课程
        List<ShareTeamApplication> teamSharesInMainCourse = shareService.listAllShareTeamsInMainCourseByCourseId(courseId);
        for (ShareTeamApplication shareTeamApplication : teamSharesInMainCourse) {
            ShareInfoVO shareInfoVO = new ShareInfoVO();
            shareInfoVO.setInfo("主课程");
            shareInfoVO.setShareType("共享分组");
            shareInfoVO.setShareId(shareTeamApplication.getId());
            shareInfoVO.setSubCourseId(shareTeamApplication.getSubCourseId());
            shareInfoVO.setSubCourseName(shareTeamApplication.getSubCourse().getName());
            shareInfoVO.setSubTeacherName(shareTeamApplication.getSubCourseTeacher().getTeacherName());
            shareInfoVOS.add(shareInfoVO);
        }
        //作为从课程
        List<ShareTeamApplication> teamSharesInSubCourse = shareService.listAllShareTeamsInSubCourseByCourseId(courseId);
        for (ShareTeamApplication shareTeamApplication : teamSharesInSubCourse) {
            ShareInfoVO shareInfoVO = new ShareInfoVO();
            shareInfoVO.setInfo("从课程");
            shareInfoVO.setShareType("共享分组");
            shareInfoVO.setShareId(shareTeamApplication.getId());
            shareInfoVO.setMainCourseId(shareTeamApplication.getMainCourseId());
            shareInfoVO.setMainCourseName(shareTeamApplication.getMainCourse().getName());
            shareInfoVO.setMainTeacherName(shareTeamApplication.getMainCourseTeacher().getTeacherName());
            shareInfoVOS.add(shareInfoVO);
        }
        return ResponseEntity.ok().body(shareInfoVOS);
    }

    /**
     * Description: 新增队伍共享申请
     *
     * @Author: WinstonDeng
     * @Date: 22:23 2018/12/26
     */
    @PostMapping("/{courseId}/teamshare")
    @ResponseBody
    public ResponseEntity<Boolean> createTeamShareRequest(@PathVariable("courseId") long courseId, @RequestBody Map<String, String> map) throws Exception {
        if (courseId == 0) {
            throw new MyException("courseId不能为空", MyException.ID_FORMAT_ERROR);
        }
        String subCourseId = "subCourseId";
        if (map.get(subCourseId) == null) {
            throw new MyException("subCourseId不能为空", MyException.ID_FORMAT_ERROR);
        }
        return ResponseEntity.ok().body(applicationService.addTeamShareRequest(courseId, Long.parseLong(map.get(subCourseId))));
    }

    /**
     * Description: 取消队伍共享
     *
     * @Author: WinstonDeng
     * @Date: 22:32 2018/12/26
     */
    @DeleteMapping("/teamshare/{teamshareId}")
    @ResponseBody
    public ResponseEntity<Boolean> deleteTeamShare(@PathVariable("teamshareId") long requestId) throws Exception {
        if (requestId == 0) {
            throw new MyException("teamshareId不能为空", MyException.ID_FORMAT_ERROR);
        }
        return ResponseEntity.ok().body(applicationService.removeTeamShare(requestId));
    }

    /**
     * Description: 新增讨论课共享申请
     *
     * @Author: WinstonDeng
     * @Date: 22:23 2018/12/26
     */
    @PostMapping("/{courseId}/seminarshare")
    @ResponseBody
    public ResponseEntity<Boolean> createSeminarShareRequest(@PathVariable("courseId") long courseId, @RequestBody Map<String, String> map) throws Exception {
        if (courseId == 0) {
            throw new MyException("courseId不能为空", MyException.ID_FORMAT_ERROR);
        }
        String subCourseId = "subCourseId";
        if (map.get(subCourseId) == null) {
            throw new MyException("subCourseId不能为空", MyException.ID_FORMAT_ERROR);
        }
        return ResponseEntity.ok().body(applicationService.addSeminarShareRequest(courseId, Long.parseLong(map.get(subCourseId))));
    }

    /**
     * Description: 取消讨论课共享
     *
     * @Author: WinstonDeng
     * @Date: 22:32 2018/12/26
     */
    @DeleteMapping("/seminarshare/{seminarshareId}")
    @ResponseBody
    public ResponseEntity<Boolean> deleteSeminarShare(@PathVariable("seminarshareId") long requestId) throws Exception {
        if (requestId == 0) {
            throw new MyException("seminarshareId不能为空", MyException.ID_FORMAT_ERROR);
        }
        return ResponseEntity.ok().body(applicationService.removeSeminarShare(requestId));
    }
}
