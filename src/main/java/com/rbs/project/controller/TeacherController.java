package com.rbs.project.controller;

import com.rbs.project.exception.MyException;
import com.rbs.project.pojo.entity.Teacher;
import com.rbs.project.controller.vo.UserVO;
import com.rbs.project.service.TeacherService;
import com.rbs.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:teacher资源接口
 *
 * @Author: 17Wang
 * @Date: 11:53 2018/12/16
 */
@RestController
@RequestMapping("/teacher")
public class TeacherController {
    @Autowired
    private UserService userService;

    @Autowired
    private TeacherService teacherService;

    @PostMapping
    @ResponseBody
    public ResponseEntity<Boolean> createTeacher(@RequestBody UserVO userVO) throws MyException {
        Teacher teacher = new Teacher();
        teacher.setAccount(userVO.getAccount());
        teacher.setTeacherName(userVO.getName());
        teacher.setEmail(userVO.getEmail());
        teacher.setPassword("123456");
        return ResponseEntity.ok(teacherService.createTeacher(teacher));
    }

    @GetMapping
    @ResponseBody
    public List<UserVO> listAllTeachers() {
        List<Teacher> teachers = teacherService.listAllTeachers();
        List<UserVO> userVOS = new ArrayList<>();
        for (Teacher teacher : teachers) {
            userVOS.add(new UserVO(teacher));
        }
        return userVOS;
    }

    @GetMapping("/searchteacher")
    @ResponseBody
    public ResponseEntity<UserVO> findOneTeacher(@RequestParam("identity") String identity) throws MyException {
        Teacher teacher = teacherService.findOneTeacher(identity);
        return ResponseEntity.ok(new UserVO(teacher));
    }

    @PutMapping("/{teacherId}/information")
    @ResponseBody
    public ResponseEntity<UserVO> updateTeacherInfo(@PathVariable("teacherId") long teacherId, @RequestBody UserVO userVO) throws MyException {
        Teacher teacher = new Teacher();
        teacher.setId(teacherId);
        teacher.setAccount(userVO.getAccount());
        teacher.setTeacherName(userVO.getName());
        teacher.setEmail(userVO.getEmail());
        return ResponseEntity.ok(new UserVO(teacherService.resetTeacherInfo(teacher)));
    }

    @PutMapping("/{teacherId}/password")
    @ResponseBody
    public ResponseEntity<UserVO> updateStudentPassword(@PathVariable("teacherId") long teacherId) throws MyException {
        Teacher teacher = new Teacher();
        teacher.setId(teacherId);
        teacher.setPassword("123456");
        return ResponseEntity.ok(new UserVO(teacherService.resetTeacherPassword(teacher)));
    }

    @DeleteMapping("/{teacherId}")
    @ResponseBody
    public ResponseEntity<Boolean> deleteStudent(@PathVariable("teacherId") long teacherId) throws MyException {
        return ResponseEntity.ok(teacherService.deleteTeacher(teacherId));
    }


    @PutMapping("/active")
    @ResponseBody
    public ResponseEntity<Boolean> teacherActive(@RequestBody Teacher teacher) throws MyException {
        return ResponseEntity.ok(userService.userActivation(teacher));
    }
}
