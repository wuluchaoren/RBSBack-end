package com.rbs.project.mapper;

import com.rbs.project.exception.MyException;
import com.rbs.project.pojo.entity.RoundScore;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 21:37 2018/12/21
 */
@Mapper
@Repository
public interface RoundScoreMapper {

    /**
     * 获取一个轮次下所有小组的轮次成绩
     *
     * @param roundId
     * @return
     */
    List<RoundScore> findByRoundId(long roundId);

    /**
     * 锁定一行数据
     * @param roundId
     * @param teamId
     * @return
     */
    RoundScore findByRoundIdAndTeamId(@Param("roundId") long roundId,@Param("teamId") long teamId);

    /**
     * 新建一个队伍在一个轮次的RoundScore
     *
     * @param roundScore
     * @return
     * @throws Exception
     */
    boolean insertRoundScore(RoundScore roundScore) throws Exception;

    /**
     * 删除一个队伍在一个轮次里的RoundScore
     *
     * @param roundId
     * @param teamId
     * @return
     * @throws Exception
     */
    boolean deleteByPrimaryKey(@Param("roundId") long roundId, @Param("teamId") long teamId) throws Exception;

    /**
     * 通过team_id删除记录
     *
     * @param teamId
     * @return
     */
    boolean deleteByTeamId(long teamId);

    /**
     * 通过轮次id删除轮次成绩
     *
     * @param roundId
     * @return
     */
    boolean deleteByRoundId(long roundId);

    /**
     * 修改轮次的展示分数
     *
     * @param roundId
     * @param teamId
     * @param presentationScore
     * @return
     * @throws Exception
     */
    boolean updatePresentationScore(@Param("roundId") long roundId,@Param("teamId") long teamId,
                                    @Param("presentationScore") double presentationScore) throws Exception;

    /**
     * 修改轮次的提问分数
     *
     * @param roundId
     * @param teamId
     * @param questionScore
     * @return
     * @throws Exception
     */
    boolean updateQuestionScore(@Param("roundId") long roundId,@Param("teamId") long teamId,
                                @Param("questionScore") double questionScore) throws Exception;

    /**
     * 修改轮次的报告分数
     *
     * @param roundId
     * @param teamId
     * @param reportScore
     * @return
     * @throws Exception
     */
    boolean updateReportScore(@Param("roundId") long roundId,@Param("teamId") long teamId,
                              @Param("reportScore") double reportScore) throws Exception;

    /**
     * 修改轮次的总分数
     *
     * @param roundId
     * @param teamId
     * @param totalScore
     * @return
     * @throws Exception
     */
    boolean updateTotalScore(@Param("roundId") long roundId,@Param("teamId") long teamId,
                             @Param("totalScore") double totalScore) throws Exception;
}
