package com.rbs.project.controller;

import com.alibaba.fastjson.JSON;
import com.rbs.project.exception.MyException;
import com.rbs.project.pojo.entity.Student;
import com.rbs.project.pojo.entity.Teacher;
import com.rbs.project.pojo.entity.User;
import com.rbs.project.service.UserService;
import com.rbs.project.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Description: user资源接口
 *
 * @Author: 17Wang
 * @Date: 11:51 2018/12/16
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/password")
    @ResponseBody
    public ResponseEntity<Boolean> getPassword(@RequestParam("account") String account) throws MyException {
        return ResponseEntity.ok().body(userService.getPassword(account));
    }

    @PutMapping("/password")
    @ResponseBody
    public ResponseEntity<Boolean> resetPassword(@RequestBody Map<String, String> password) throws MyException {
        String newPassword = password.get("newPassword");
        String oldPassword = password.get("oldPassword");
        if (oldPassword.equals(newPassword)) {
            throw new MyException("新老密码不能相同", MyException.ERROR);
        }
        if (oldPassword == null || newPassword.equals("")) {
            throw new MyException("老密码不能为空", MyException.ERROR);
        }
        if (newPassword == null || newPassword.equals("")) {
            throw new MyException("新密码不能为空", MyException.ERROR);
        }
        return ResponseEntity.ok(userService.resetPassword(password.get("newPassword")));
    }

    @GetMapping("/information")
    @ResponseBody
    public Map<String, Object> getInfomation() throws MyException {
        User user;
        try {
            user = UserUtils.getNowUser();
        } catch (MyException e) {
            throw new MyException("获取当前用户信息出错！当前无用户 " + e.getMessage(), MyException.NOT_FOUND_ERROR);
        }

        Map<String, Object> map = new HashMap<>();
        map.put("id", user.getId());
        map.put("account", user.getUsername());
        map.put("email", user.getEmail());
        if (user instanceof Student) {
            map.put("name", ((Student) user).getStudentName());
            map.put("role", "student");
        } else if (user instanceof Teacher) {
            map.put("name", ((Teacher) user).getTeacherName());
            map.put("role", "teacher");
        }

        return map;
    }

    @PutMapping("/email")
    @ResponseBody
    public ResponseEntity<Boolean> resetEmail(@RequestBody Map<String, String> email) throws MyException {
        return ResponseEntity.ok(userService.resetEmail(email.get("email")));
    }

    @RequestMapping(value = "test2.html")
    public String test2(){
        return "test2";
    }
}
