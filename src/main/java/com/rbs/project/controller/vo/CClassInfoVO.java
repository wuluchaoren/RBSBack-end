package com.rbs.project.controller.vo;

import com.rbs.project.pojo.entity.CClass;

/**
 * @Author: WinstonDeng
 * @Description: OOAD_Course_ManageSystem
 * @Date: Created in 11:10 2018/12/17
 * @Modified by:
 */
public class CClassInfoVO {
    /**
     * id
     */
    private Long id;
    /**
     * 班级名
     */
    private String name;
    /**
     * 上课时间
     */
    private String time;
    /**
     * 上课教室
     */
    private String classroom;
    /**
     * 学生名单
     */
    private String fileName;

    public CClassInfoVO(CClass cClass) {
        id = cClass.getId();
        name = String.valueOf(cClass.getGrade()) + '-' + String.valueOf(cClass.getSerial());
        time = cClass.getTime();
        classroom = cClass.getPlace();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return "CClassInfoVO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", time='" + time + '\'' +
                ", classroom='" + classroom + '\'' +
                ", fileName='" + fileName + '\'' +
                '}';
    }
}
