package com.rbs.project.pojo.strategy;

/**Bi
 * @Author: WinstonDeng
 * @Description: OOAD_Course_ManageSystem
 * @Date: Created in 15:25 2018/12/15
 * @Modified by:
 */
public class ConflictCourseStrategy {
    private long id;
    /**
     * 冲突课程
     */
    private long courseId;

    //==================================================getter AND setter==================================================//

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }
}
