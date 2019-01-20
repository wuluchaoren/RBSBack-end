package com.rbs.project.service;

import com.rbs.project.dao.*;
import com.rbs.project.exception.MyException;
import com.rbs.project.pojo.entity.Round;
import com.rbs.project.pojo.entity.Seminar;
import com.rbs.project.pojo.relationship.CClassStudent;
import com.rbs.project.pojo.entity.CClass;
import com.rbs.project.pojo.entity.Student;
import com.rbs.project.utils.ExcelUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * @Author: WinstonDeng
 * @Description: OOAD_Course_ManageSystem
 * @Date: Created in 14:41 2018/12/16
 * @Modified by:
 */
@Service
public class CClassService {

    @Autowired
    private CClassDao cClassDao;

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private CClassSeminarDao cClassSeminarDao;

    @Autowired
    private SeminarDao seminarDao;

    @Autowired
    private RoundDao roundDao;

    /**
     * Description: 创建班级
     *
     * @Author: WinstonDeng
     * @Date: 11:34 2018/12/12
     */
    public long createCClass(long courseId, CClass cClass) throws MyException {
        //新建记录的主键值 初始化为-1
        long createCClassId = -1;
        //判空
        if ((Long) courseId == null) {
            throw new MyException("courseId不能为空", MyException.ERROR);
        }
        if (cClass.getGrade() == null) {
            throw new MyException("grade不能为空", MyException.ERROR);
        }
        if (cClass.getSerial() == null) {
            throw new MyException("serial不能为空", MyException.ERROR);
        }
        if (cClass.getTime() == null) {
            throw new MyException("time不能为空", MyException.ERROR);
        }
        if (cClass.getPlace() == null) {
            throw new MyException("location不能为空", MyException.ERROR);
        }

        cClass.setCourseId(courseId);
        //插入klass表
        cClassDao.addCClass(courseId, cClass);

        //TODO 建立班级讨论课联系 已完成
        //1.获取这个班级属于的课程下的所有讨论课
        List<Seminar> seminars = seminarDao.findSeminarByCourseId(cClass.getCourseId());
        //2.建立班级和讨论课之间的联系
        for (Seminar seminar : seminars) {
            cClassSeminarDao.addCClassSeminar(seminar);
        }
        //获得主键
        createCClassId = cClass.getId();

        //TODO 建立班级轮次关系klass_round 已完成
        List<Round> rounds=roundDao.listByCourseId(courseId);
        for(Round round
                :rounds){
            roundDao.addCClassRoundByPrimaryKeys(createCClassId,round.getId());
        }

        return createCClassId;
    }

    /**
     * Description: 解析名单excel文件 并导入数据库
     *
     * @Author: WinstonDeng
     * @Date: 14:44 2018/12/12
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean transStudentListFileToDataBase(long cclassId, String filePath, String fileName) throws MyException {
        //读取路径
        Set<Student> students = ExcelUtils.transExcelToSet(filePath + fileName);
        for (Student student
                : students) {
            try {
                long stdId=studentDao.getStudentByAccount(student.getUsername()).getId();
                student.setId(stdId);
            } catch (MyException e) {
                //默认密码
                student.setPassword("123456");
                //初始状态
                student.setActive(false);
                //增加到student
                long studentId = studentDao.addStudent(student);
                student.setId(studentId);
            }
            //不管新班级中，学生存不存在，都要增加到klass_student
            CClassStudent cClassStudent = new CClassStudent();
            cClassStudent.setcClassId(cclassId);
            cClassStudent.setCourseId(cClassDao.getById(cclassId).getCourseId());
            cClassStudent.setStudentId(student.getId());
            cClassDao.addCClassStudent(cClassStudent);
        }
        return true;
    }

    /**
     * Description: 通过课程查看班级列表
     *
     * @Author: WinstonDeng
     * @Date: 10:42 2018/12/17
     */
    public List<CClass> listCClassesByCourseId(long courseId) throws MyException {
        if ((Long) courseId == null) {
            throw new MyException("courseId不能为空", MyException.ERROR);
        }
        return cClassDao.listByCourseId(courseId);
    }

    /**
     * Description: 获取一个学生在一个课程下的班级
     *
     * @Author: 17Wang
     * @Time: 14:27 2018/12/28
     */
    public CClass getCClassByStudentIdAndCourseId(long studentId, long courseId) throws MyException {
        return cClassDao.getCClassByStudentIdAndCourseId(studentId, courseId);
    }


    /**
     * Description: 按id删除班级
     *
     * @Author: WinstonDeng
     * @Date: 11:10 2018/12/18
     */
    public boolean removeCClassById(long cClassId) throws MyException {
        if ((Long) cClassId == null) {
            throw new MyException("cClassId不能为空", MyException.ERROR);
        }
        return cClassDao.removeCClass(cClassId);
    }


    /**
     * Description: 按id获取班级
     *
     * @Author: WinstonDeng
     * @Date: 21:19 2018/12/26
     */
    public CClass getClassById(long cClassId) throws MyException {
        return cClassDao.getById(cClassId);
    }
}
