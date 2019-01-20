package com.rbs.project.controller.vo;

import com.rbs.project.pojo.entity.Round;
import com.rbs.project.pojo.entity.Seminar;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: WinstonDeng
 * @Description: OOAD_Course_ManageSystem
 * @Date: Created in 22:33 2018/12/21
 * @Modified by:
 */
public class RoundInfoVO {
    private Long roundId;
    private Integer roundSerial;
    private List<SeminarInfoVO> seminarInfoVOS;

    public RoundInfoVO(){

    }

    public RoundInfoVO(Round round){
        roundId=round.getId();
        roundSerial=round.getSerial();
        seminarInfoVOS=new ArrayList<>();
        for(Seminar seminar:round.getSeminars()){
            seminarInfoVOS.add(new SeminarInfoVO(seminar));
        }
    }

    public Long getRoundId() {
        return roundId;
    }

    public void setRoundId(Long roundId) {
        this.roundId = roundId;
    }

    public Integer getRoundSerial() {
        return roundSerial;
    }

    public void setRoundSerial(Integer roundSerial) {
        this.roundSerial = roundSerial;
    }

    public List<SeminarInfoVO> getSeminarInfoVOS() {
        return seminarInfoVOS;
    }

    public void setSeminarInfoVOS(List<SeminarInfoVO> seminarInfoVOS) {
        this.seminarInfoVOS = seminarInfoVOS;
    }

    @Override
    public String toString() {
        return "RoundInfoVO{" +
                "roundId=" + roundId +
                ", roundSerial=" + roundSerial +
                ", seminarInfoVOS=" + seminarInfoVOS +
                '}';
    }
}
