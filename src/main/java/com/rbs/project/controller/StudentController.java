package com.rbs.project.controller;

import com.rbs.project.exception.MyException;
import com.rbs.project.pojo.entity.Student;
import com.rbs.project.controller.vo.UserVO;
import com.rbs.project.service.StudentService;
import com.rbs.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:student资源接口
 *
 * @Author: 17Wang
 * @Date: 11:53 2018/12/16
 */
@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private UserService userService;

    @Autowired
    private StudentService studentService;

    @GetMapping
    @ResponseBody
    public List<UserVO> listAllStudents() {
        List<Student> students = studentService.listAllStudents();
        List<UserVO> userVOS = new ArrayList<>();
        for (Student student : students) {
            userVOS.add(new UserVO(student));
        }
        return userVOS;
    }

    @GetMapping("/searchstudent")
    @ResponseBody
    public ResponseEntity<UserVO> findOneStudent(@RequestParam("identity") String identity) throws MyException {
        Student student = studentService.findOneStudent(identity);
        return ResponseEntity.ok(new UserVO(student));
    }

    @PutMapping("/{studentId}/information")
    @ResponseBody
    public ResponseEntity<UserVO> updateStudentInfo(@PathVariable("studentId") long studentId, @RequestBody UserVO userVO) throws MyException {
        Student student = new Student();
        student.setId(studentId);
        student.setAccount(userVO.getAccount());
        student.setStudentName(userVO.getName());
        student.setEmail(userVO.getEmail());
        return ResponseEntity.ok(new UserVO(studentService.resetStudentInfo(student)));
    }

    @PutMapping("/{studentId}/password")
    @ResponseBody
    public ResponseEntity<UserVO> updateStudentPassword(@PathVariable("studentId") long studentId) throws MyException {
        Student student = new Student();
        student.setId(studentId);
        student.setPassword("123456");
        return ResponseEntity.ok(new UserVO(studentService.resetStudentPassword(student)));
    }

    @DeleteMapping("/{studentId}")
    @ResponseBody
    public ResponseEntity<Boolean> deleteStudent(@PathVariable("studentId") long studentId) throws MyException {
        return ResponseEntity.ok(studentService.deleteStudent(studentId));
    }

    @PutMapping("/active")
    @ResponseBody
    public ResponseEntity<Boolean> studentActive(@RequestBody Student student) throws MyException {
        return ResponseEntity.ok(userService.userActivation(student));
    }
}
