package com.rbs.project.controller.dto;

import java.util.List;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 4:18 2018/12/29
 */
public class ClassSeminarAndScoreDTO {
    private Long classId;
    private Long seminarId;

    private List<ScoreDTO> scoreDTOS;

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public Long getSeminarId() {
        return seminarId;
    }

    public void setSeminarId(Long seminarId) {
        this.seminarId = seminarId;
    }

    public List<ScoreDTO> getScoreDTOS() {
        return scoreDTOS;
    }

    public void setScoreDTOS(List<ScoreDTO> scoreDTOS) {
        this.scoreDTOS = scoreDTOS;
    }

    @Override
    public String toString() {
        return "ClassSeminarAndScoreDTO{" +
                "classId=" + classId +
                ", seminarId=" + seminarId +
                ", scoreDTOS=" + scoreDTOS +
                '}';
    }
}
