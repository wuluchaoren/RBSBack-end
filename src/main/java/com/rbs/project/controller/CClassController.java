package com.rbs.project.controller;

import com.rbs.project.exception.MyException;
import com.rbs.project.service.CClassService;
import com.rbs.project.utils.FileLoadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


/**
 * @Author: WinstonDeng
 * @Description: OOAD_Course_ManageSystem
 * @Date: Created in 10:30 2018/12/18
 * @Modified by:
 */
@RestController
@RequestMapping("/class")
public class CClassController {
    @Autowired
    CClassService cClassService;

    /**
     * Description: 班级界面，上传学生名单
     * @Author: WinstonDeng
     * @Date: 17:24 2018/12/19
     */
    @PostMapping("/{classId}/studentfile")
    @ResponseBody
    public ResponseEntity<String> uploadStudentFileAfterCreateCClass(HttpServletRequest request,@RequestParam("file") MultipartFile file){
        return ResponseEntity.ok().body(FileLoadUtils.upload(request.getServletContext().getRealPath("/resources/studentfile/"),file));
    }
    /**
     * Description: 班级界面，上传学生名单后，解析
     * @Author: WinstonDeng
     * @Date: 11:09 2018/12/18
     */
    @PostMapping("/{classId}")
    @ResponseBody
    public ResponseEntity<Boolean> handleStudentFile(HttpServletRequest request,@PathVariable("classId") long cClassId, @RequestBody Map<String,String> file) throws MyException{
        boolean flag=false;
        String text="fileName";
        if(file.get(text)!=null){
            //直接解析，student表补充原来没有的学生, klass_student要添加
            flag=cClassService.transStudentListFileToDataBase(cClassId,request.getServletContext().getRealPath("/resources/studentfile/"),file.get(text));
        }
        return ResponseEntity.ok().body(flag);
    }

    /**
     * Description: 按id删除班级
     * @Author: WinstonDeng
     * @Date: 11:10 2018/12/18
     */
    @DeleteMapping("/{classId}")
    @ResponseBody
    public ResponseEntity<Boolean> deleteCClassById(@PathVariable("classId") long cClassId) throws MyException {
        return ResponseEntity.ok().body(cClassService.removeCClassById(cClassId));
    }

}
