package com.rbs.project.controller;

import com.rbs.project.dao.TeamDao;
import com.rbs.project.exception.MyException;
import com.rbs.project.pojo.entity.*;
import com.rbs.project.controller.vo.RequestInfoVO;
import com.rbs.project.controller.vo.TeamValidApplicationVO;
import com.rbs.project.service.ApplicationService;
import com.rbs.project.service.CourseService;
import com.rbs.project.service.TeamService;
import com.rbs.project.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 14:21 2018/12/23
 */
@RestController
@RequestMapping("/request")
public class RequestController {
    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private TeamService teamService;

    /**
     * Description: 老师的专属接口，获取所有的小组合法化请求
     *
     * @Author: 17Wang
     * @Time: 14:40 2018/12/23
     */
    @GetMapping("/team")
    @ResponseBody
    public List<TeamValidApplicationVO> listTeamRequestByTeacherId() throws MyException {
        Teacher teacher = (Teacher) UserUtils.getNowUser();
        List<TeamValidApplication> teamValidApplications = applicationService.listTeamApplicationByTeacherId(teacher.getId());
        List<TeamValidApplicationVO> teamValidApplicationVOS = new ArrayList<>();
        for (TeamValidApplication teamValidApplication : teamValidApplications) {
            try {
                Team team = teamService.getTeamById(teamValidApplication.getTeamId(), TeamDao.HAS_CCLASS);
                if (team.getStatus() == Team.STATUS_IN_REVIEW) {
                    Course course = courseService.getCourseById(team.getCourseId(), -1);
                    teamValidApplicationVOS.add(
                            new TeamValidApplicationVO(teamValidApplication)
                                    .setTeam(team)
                                    .setCourse(course));
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return teamValidApplicationVOS;
    }

    /**
     * Description: 更新小组合法状态
     *
     * @Author: 17（Winston补充的注释，看到删掉）
     * @Date: 14:24 2018/12/26
     */
    @PutMapping("/team")
    @ResponseBody
    public ResponseEntity<Boolean> updateTeamApplicationStatus(@RequestParam("requestId") long requestId, @RequestParam("status") int status) throws Exception {
        return ResponseEntity.ok(applicationService.updateTeamValidApplicationStatus(requestId, status));
    }

    /**
     * Description: 按id处理共享队伍请求 同意/拒绝 注意这里输入的是字符串accept/reject
     *
     * @Author: WinstonDeng
     * @Date: 20:02 2018/12/24
     */
    @PutMapping("/teamshare/{teamshareId}")
    @ResponseBody
    public ResponseEntity<Boolean> handleTeamShareRequest(@PathVariable("teamshareId") long requestId, @RequestBody Map<String, String> handle) throws Exception {
        String handleType = "handleType";
        String accept = "accept";
        String reject = "reject";
        Integer status = null;
        if (handle.get(handleType) == null) {
            throw new MyException("handleType不能为空", MyException.ERROR);
        } else if (handle.get(handleType).equals(accept)) {
            status = ShareTeamApplication.STATUS_ACCEPT;
        } else if (handle.get(handleType).equals(reject)) {
            status = ShareTeamApplication.STATUS_REJECT;
        } else {
            throw new MyException("handleType格式错误，只能输入accept或reject", MyException.ERROR);
        }
        return ResponseEntity.ok().body(applicationService.updateTeamShareApplicationStatus(requestId, status));
    }

    /**
     * Description: 按id处理共享讨论课请求 同意/拒绝 注意这里输入的是字符串accept/reject
     *
     * @Author: WinstonDeng
     * @Date: 18:12 2018/12/27
     */
    @PutMapping("/seminarshare/{seminarshareId}")
    @ResponseBody
    public ResponseEntity<Boolean> handleSeminarShareRequest(@PathVariable("seminarshareId") long requestId, @RequestBody Map<String, String> handle) throws Exception {
        String handleType = "handleType";
        String accept = "accept";
        String reject = "reject";
        Integer status = null;
        if (handle.get(handleType) == null) {
            throw new MyException("handleType不能为空", MyException.ERROR);
        } else if (handle.get(handleType).equals(accept)) {
            status = ShareSeminarApplication.STATUS_ACCEPT;
        } else if (handle.get(handleType).equals(reject)) {
            status = ShareSeminarApplication.STATUS_REJECT;
        } else {
            throw new MyException("handleType格式错误，只能输入accept或reject", MyException.ERROR);
        }
        return ResponseEntity.ok().body(applicationService.updateSeminarShareApplicationStatus(requestId, status));
    }

    /**
     * Description: 获取所有待办共享申请
     *
     * @Author: WinstonDeng
     * @Date: 15:32 2018/12/28
     */
    @GetMapping
    @ResponseBody
    public ResponseEntity<List<RequestInfoVO>> listAllUnhandleRequest() throws Exception {
        //获取当前的老师
        Teacher teacher = (Teacher) UserUtils.getNowUser();
        List<RequestInfoVO> requestInfoVOS = new ArrayList<>();
        //讨论课共享请求
        List<ShareSeminarApplication> shareSeminarApplications = applicationService.listSeminarShareApplicationByTeacherId(teacher.getId());
        for (ShareSeminarApplication shareSeminarApplication : shareSeminarApplications) {
            requestInfoVOS.add(new RequestInfoVO(shareSeminarApplication));
        }
        //组队共享请求
        List<ShareTeamApplication> shareTeamApplications = applicationService.listTeamShareApplicationByTeacherId(teacher.getId());
        for (ShareTeamApplication shareTeamApplication : shareTeamApplications) {
            requestInfoVOS.add(new RequestInfoVO(shareTeamApplication));
        }
        return ResponseEntity.ok().body(requestInfoVOS);
    }
}
