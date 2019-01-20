package com.rbs.project.dao;

import com.rbs.project.exception.MyException;
import com.rbs.project.mapper.*;
import com.rbs.project.pojo.entity.Round;
import com.rbs.project.pojo.relationship.CClassRound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: WinstonDeng
 * TODO 判断是否为讨论课从课程
 * @Description: OOAD_Course_ManageSystem
 * @Date: Created in 11:17 2018/12/20
 * @Modified by:
 */

@Repository
public class RoundDao {
    @Autowired
    private RoundMapper roundMapper;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private SeminarMapper seminarMapper;

    @Autowired
    private CClassRoundMapper cClassRoundMapper;


    public final static int HAS_CCLASS_ROUND=0;
    public final static int HAS_SEMINAR=1;

    private void hasSomethingFun(Round round,int ...hasSomething){
        for(int i:hasSomething){
            if(i== HAS_SEMINAR){
                round.setSeminars(seminarMapper.findByRoundId(round.getId()));
            }
            if(i==HAS_CCLASS_ROUND){
                round.setcClassRounds(cClassRoundMapper.findByRoundId(round.getId()));
            }
        }
    }
    /**
     * Description: 讨论课下设置 新增轮次
     * @Author: WinstonDeng
     * @Date: 11:18 2018/12/20
     */
    public long addRound(Round round) throws MyException{
        long createRoundId = -1;
        try {
            roundMapper.insertRound(round);
            createRoundId=round.getId();
        }catch (Exception e){
            throw new MyException("新增轮次错误！数据库处理错误",MyException.ERROR);
        }
        return createRoundId;
    }

    /**
     * Description: 通过课程id查找轮次列表
     * @Author: WinstonDeng
     * @Date: 16:01 2018/12/20
     */
    public List<Round> listByCourseId(long courseId,int ...hasSomething) throws MyException{
        List<Round> rounds=roundMapper.findByCourseId(courseId);
        for(Round round
                :rounds){
            hasSomethingFun(round,hasSomething);
        }
        return rounds;
    }

    /**
     * Description: 通过id查找轮次
     * @Author: WinstonDeng
     * @Date: 19:21 2018/12/21
     */
    public Round findById(long roundId,int ...hasSomething) throws MyException {
        Round round=roundMapper.findById(roundId);
        if(round==null){
            throw new MyException("查找轮次错误！轮次不存在",MyException.NOT_FOUND_ERROR);
        }
        hasSomethingFun(round,hasSomething);
        return round;
    }

    /**
     * Description: 修改轮次分数计算方法
     * @Author: WinstonDeng
     * @Date: 1:47 2018/12/26
     */
    public boolean updateScoreMethod(Round round) throws MyException{
        Round temp=roundMapper.findById(round.getId());
        if(temp==null){
            throw new MyException("更新轮次信息错误！未找到该轮次",MyException.NOT_FOUND_ERROR);
        }
        temp.setQuestionScoreMethod(round.getQuestionScoreMethod());
        temp.setReportScoreMethod(round.getReportScoreMethod());
        temp.setPresentationScoreMethod(round.getPresentationScoreMethod());
        if(!roundMapper.updateScoreMethod(round)){
            throw new MyException("更新轮次信息错误！数据库处理错误",MyException.ERROR);
        }
        return true;
    }

    /**
     * Description: 修改班级轮次报名数
     * @Author: WinstonDeng
     * @Date: 1:47 2018/12/26
     */
    public boolean updateEnrollNumber(CClassRound cClassRound) throws MyException{
        CClassRound temp=cClassRoundMapper.findByPrimaryKeys(cClassRound.getcClassId(),cClassRound.getRoundId());
        if(temp==null){
            throw new MyException("修改班级轮次错误！未找到该记录",MyException.NOT_FOUND_ERROR);
        }
        temp.setEnrollNumber(cClassRound.getEnrollNumber());
        if(!cClassRoundMapper.updateEnrollNumber(temp)){
            throw new MyException("修改班级轮次错误！数据库处理错误",MyException.ERROR);
        }
        return true;
    }

    /**
     * Description: 通过classId roundId查找班级轮次
     * @Author: WinstonDeng
     * @Date: 16:49 2018/12/27
     */
    public CClassRound findCClassRoundByPrimaryKeys(long cClassId, long roundId) throws MyException{
        CClassRound cClassRound=cClassRoundMapper.findByPrimaryKeys(cClassId,roundId);
        if(cClassRound==null){
            throw new MyException("查找班级轮次错误！未找到该记录",MyException.NOT_FOUND_ERROR);
        }
        return cClassRound;
    }

    /**
     * Description: 新增班级轮次
     * @Author: WinstonDeng
     * @Date: 13:54 2018/12/28
     */
    public boolean addCClassRoundByPrimaryKeys(long cClassId, long roundId) throws MyException {
        CClassRound cClassRound=new CClassRound();
        cClassRound.setcClassId(cClassId);
        cClassRound.setRoundId(roundId);
        cClassRound.setEnrollNumber(CClassRound.DEFAULT_ENROLL_NUM);
        return cClassRoundMapper.insertCClassRound(cClassRound);
    }

    /**
     * Description: 通过课程和轮次次序 查找一个唯一的轮次
     * @Author: WinstonDeng
     * @Date: 0:54 2018/12/29
     */
    public Round getByCourseIdAndSerial(long courseId, int serial) throws MyException{
        List<Round> rounds=roundMapper.findByCourseId(courseId);
        Round result=null;
        for(Round round:rounds){
            if(round.getSerial()==serial){
              result=round;
              break;
            }
        }
        if(result==null){
            throw new MyException("查询轮次错误！未找到该记录",MyException.NOT_FOUND_ERROR);
        }
        return result;
    }
}
