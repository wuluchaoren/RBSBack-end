package com.rbs.project.service;

import com.rbs.project.dao.StudentDao;
import com.rbs.project.exception.MyException;
import com.rbs.project.pojo.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 16:37 2018/12/16
 */
@Service
public class StudentService {
    @Autowired
    private StudentDao studentDao;

    /**
     * Description: 获取所有学生信息
     *
     * @Author: 17Wang
     * @Time: 16:51 2018/12/16
     */
    public List<Student> listAllStudents() {
        return studentDao.listAllStudents();
    }

    /**
     * Description: 通过id获取一个学生
     *
     * @Author: 17Wang
     * @Time: 8:49 2018/12/29
     */
    public Student findStudentById(long studentId) throws MyException {
        return studentDao.getStudentById(studentId);
    }

    /**
     * Description: 通过姓名或者学号查询一个学生信息
     *
     * @Author: 17Wang
     * @Time: 23:04 2018/12/16
     */
    public Student findOneStudent(String identity) throws MyException {
        Student student;
        try {
            student = studentDao.getStudentByAccount(identity);
        } catch (MyException e) {
            try {
                student = studentDao.getStudentByStudentName(identity);
            } catch (MyException e1) {
                throw new MyException("通过姓名或者学号查询一个学生信息出错！", MyException.NOT_FOUND_ERROR);
            }
        }
        return student;
    }

    /**
     * Description:获取一个课程下没有组队的所有学生
     *
     * @Author: 17Wang
     * @Time: 13:19 2018/12/23
     */
    public List<Student> listByCourseIdAndTeamId(long courseId) {
        return studentDao.listByCourseIdAndTeamIdIsNULL(courseId);
    }

    /**
     * Description:管理员修改某一学生的信息
     *
     * @Author: 17Wang
     * @Time: 23:23 2018/12/16
     */
    @Transactional(rollbackFor = Exception.class)
    public Student resetStudentInfo(Student student) throws MyException {
        Student temp = studentDao.getStudentById(student.getId());

        //修改学生账号
        if (!temp.getUsername().equals(student.getUsername())) {
            temp.setAccount(student.getUsername());
            studentDao.updateAccountByStudent(temp);
        }

        //修改学生名字
        if (!temp.getStudentName().equals(student.getStudentName())) {
            temp.setStudentName(student.getStudentName());
            studentDao.updateStudentNameByStudent(temp);
        }

        //修改学生邮箱
        if (temp.getEmail() == null) {
            temp.setEmail(" ");
        }
        if (!temp.getEmail().equals(student.getEmail()) && student.getEmail() != null) {
            temp.setEmail(student.getEmail());
            studentDao.updateEmailByStudent(temp);
        }

        return temp;
    }

    /**
     * Description: 修改学生密码
     *
     * @Author: 17Wang
     * @Time: 22:04 2018/12/17
     */
    @Transactional(rollbackFor = Exception.class)
    public Student resetStudentPassword(Student student) throws MyException {
        Student temp = studentDao.getStudentById(student.getId());

        //修改学生密码
        if (!temp.getPassword().equals(student.getPassword())) {
            temp.setPassword(student.getPassword());
            studentDao.updatePasswordByStudent(temp);
        }

        return temp;
    }

    /**
     * Description: 删除学生
     *
     * @Author: 17Wang
     * @Time: 22:04 2018/12/17
     */
    public boolean deleteStudent(long studentId) throws MyException {
        return studentDao.deleteStudentByStudentId(studentId);
    }
}
