package com.rbs.project.controller.dto;

/**
 * @Author: WinstonDeng
 * @Description: 新建班级DTO
 * @Date: Created in 14:35 2018/12/16
 * @Modified by:
 */
public class CreateCClassDTO {
    /**
     * 年级
     */
    private Integer grade;
    /**
     * 班级序号
     */
    private Integer serial;
    /**
     * 上课时间
     */
    private String time;
    /**
     * 上课教室
     */
    private String classroom;
    /**
     * 学生名单文件名
     */
    private String fileName;

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Integer getSerial() {
        return serial;
    }

    public void setSerial(Integer serial) {
        this.serial = serial;
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
        return "CreateCClassDTO{" +
                "grade=" + grade +
                ", serial=" + serial +
                ", time='" + time + '\'' +
                ", classroom='" + classroom + '\'' +
                ", fileName='" + fileName + '\'' +
                '}';
    }
}
