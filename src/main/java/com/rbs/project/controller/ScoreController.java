package com.rbs.project.controller;

import com.rbs.project.exception.MyException;
import com.rbs.project.controller.dto.ClassSeminarAndScoreDTO;
import com.rbs.project.controller.dto.ScoreDTO;
import com.rbs.project.pojo.entity.*;
import com.rbs.project.pojo.strategy.TeamStrategy;
import com.rbs.project.controller.vo.*;
import com.rbs.project.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 16:06 2018/12/22
 */
@RestController
@RequestMapping
public class ScoreController {
    @Autowired
    private ScoreService scoreService;

    @Autowired
    private SeminarService seminarService;

    @Autowired
    private RoundService roundService;

    @Autowired
    private TeamService teamService;

    @Autowired
    private CClassService cClassService;


    /**
     * Description: 获取一个班级的一节讨论课所有报名小组的成绩
     *
     * @Author: 17Wang
     * @Time: 20:28 2018/12/22
     */
    @GetMapping("/seminarscore")
    @ResponseBody
    public Map<String, Object> listAllSeminarScore(@RequestParam("seminarId") long seminarId, @RequestParam("classId") long classId) throws MyException {
        List<SeminarScore> seminarScores = scoreService.listAllSeminarScoreBySeminarIdAndCClassId(seminarId, classId);
        String seminarName = seminarService.getSeminarById(seminarId).getName();
        Map<String, Object> map = new HashMap<>();
        map.put("seminarName", seminarName);
        List<ScoreVO> scoreVOS = new ArrayList<>();
        for (SeminarScore seminarScore : seminarScores) {
            scoreVOS.add(new ScoreVO().revertSeminarScore(seminarScore));
        }
        map.put("scores", scoreVOS);
        return map;
    }

    /**
     * Description: 修改一节班级讨论课下所有小组的三个成绩
     *
     * @Author: 17Wang
     * @Time: 4:56 2018/12/29
     */
    @PutMapping("/seminarscore")
    @ResponseBody
    public ResponseEntity<Boolean> updateAllSeminarScore(@RequestBody ClassSeminarAndScoreDTO allScore) throws Exception {
        List<SeminarScore> seminarScores = new ArrayList<>();
        for (ScoreDTO scoreDTO : allScore.getScoreDTOS()) {
            SeminarScore seminarScore = new SeminarScore();
            seminarScore.setTeamId(scoreDTO.getTeamId());
            seminarScore.setPresentationScore(scoreDTO.getPresentationScore());
            seminarScore.setQuestionScore(scoreDTO.getQuestionScore());
            seminarScore.setReportScore(scoreDTO.getReportScore());
            seminarScores.add(seminarScore);
        }

        return ResponseEntity.ok(scoreService.updateAllScoreByCClassSeminar(allScore.getClassId(), allScore.getSeminarId(), seminarScores));
    }

    /**
     * Description:获取一个讨论课下一个小组的成绩
     *
     * @Author: 17Wang
     * @Time: 9:01 2018/12/29
     */
    @GetMapping("/seminarscore/team/{teamId}")
    @ResponseBody
    public SeminarScore getSeminarScore(@PathVariable("teamId") long teamId, @RequestParam("seminarId") long seminarId) throws MyException {
        return scoreService.getSeminarScoreBySeminarIdAndTeamId(seminarId, teamId);
    }

    /**
     * Description: 修改一节班级讨论课下一个小组的三个成绩
     *
     * @Author: 17Wang
     * @Time: 7:02 2018/12/29
     */
    @PutMapping("/seminarscore/team/{teamId}")
    @ResponseBody
    public ResponseEntity<Boolean> updateSeminarScore(@RequestBody BigSeminarVO bigSeminarVO, @PathVariable("teamId") long teamId) throws Exception {
        SeminarScore seminarScore = new SeminarScore();
        seminarScore.setTeamId(teamId);

        seminarScore.setPresentationScore(bigSeminarVO.getSeminarPresentationScore());
        seminarScore.setQuestionScore(bigSeminarVO.getSeminarQuestionScore());
        seminarScore.setReportScore(bigSeminarVO.getSeminarReportScore());
        return ResponseEntity.ok(scoreService.updateScoreByCClassSeminar(bigSeminarVO.getClassId(), bigSeminarVO.getSeminarId(), seminarScore));
    }

    /**
     * Description: 轮次+ 所有小组（轮次分数） + 该轮讨论课三项
     *
     * @Author: 17Wang
     * @Time: 4:57 2018/12/29
     */
    @GetMapping("/allscore/allteam")
    @ResponseBody
    public List<RoundTeamScoreVO> getAllRoundTeamScoreVO(@RequestParam("courseId") long courseId) throws MyException {
        //带有每一轮次下讨论课的信息
        List<Round> rounds = roundService.listRoundsByCourseId(courseId);
        //
        List<Team> teams = teamService.listTeamByCourseId(courseId);
        List<RoundTeamScoreVO> roundTeamScoreVOS = new ArrayList<>();
        for (Round round : rounds) {
            RoundTeamScoreVO roundTeamScoreVO = new RoundTeamScoreVO();
            roundTeamScoreVO.setRoundInfoVO(new RoundInfoVO(round));

            //所有队伍的信息
            List<BigTeamVO> bigTeamVOS = new ArrayList<>();
            for (Team team : teams) {
                //设置队伍基础信息
                BigTeamVO bigTeamVO = new BigTeamVO(team);

                //设置该轮次下的Round分数，容易查
                RoundScore roundScore = scoreService.getRoundScoreByRoundIdAndTeamId(round.getId(), team.getId());
                bigTeamVO.setBigRoundScoreVO(new BigRoundScoreVO(roundScore));

                //设置该轮次下的讨论课信息
                List<Seminar> seminars = seminarService.listSeminarByRoundId(round.getId());
                List<BigSeminarVO> bigSeminarVOS = new ArrayList<>();
                for (Seminar seminar : seminars) {
                    //设置讨论课基本信息 和 设置讨论课分数信息
                    SeminarScore seminarScore = scoreService.getSeminarScoreBySeminarIdAndTeamId(seminar.getId(), team.getId());
                    BigSeminarVO bigSeminarVO = new BigSeminarVO(seminar, seminarScore);
                    long classId = 0;
                    try {
                        classId = cClassService.getCClassByStudentIdAndCourseId(team.getLeaderId(), courseId).getId();
                    } catch (Exception e) {
                    }
                    bigSeminarVO.setClassId(classId);
                    bigSeminarVOS.add(bigSeminarVO);
                }
                bigTeamVO.setBigSeminarVOS(bigSeminarVOS);

                //放进team层
                bigTeamVOS.add(bigTeamVO);
            }
            roundTeamScoreVO.setBigTeamVOS(bigTeamVOS);

            //加入最终列表
            roundTeamScoreVOS.add(roundTeamScoreVO);
        }
        return roundTeamScoreVOS;
    }


    /**
     * Description: （展示）打分或者修改分数* 1
     *
     * @Author: 17Wang
     * @Time: 20:29 2018/12/22
     */
    @PutMapping("/seminarscore/presentationscore")
    @ResponseBody
    public ResponseEntity<Boolean> updatePresentationScore(@RequestBody Map<String, Object> map) throws Exception {
        Long seminarId = Long.valueOf(String.valueOf(map.get("seminarId")));
        Long classId = Long.valueOf(String.valueOf(map.get("classId")));
        Long teamId = Long.valueOf(String.valueOf(map.get("teamId")));
        Double presentationScore = Double.valueOf(String.valueOf(map.get("presentationScore")));
        if (seminarId == null || classId == null || teamId == null || presentationScore == null) {
            throw new MyException("存在为空的字段，或者参数名错误", MyException.ERROR);
        }
        return ResponseEntity.ok(scoreService.updatePresentationScore(seminarId, classId, teamId, presentationScore));
    }

    /**
     * Description: （报告）打分或者修改分数 * 1
     *
     * @Author: 17Wang
     * @Time: 20:29 2018/12/22
     */
    @PutMapping("/seminarscore/reportscore")
    @ResponseBody
    public ResponseEntity<Boolean> updateReportScore(@RequestBody Map<String, Number> map) throws Exception {
        Long seminarId = Long.valueOf(String.valueOf(map.get("seminarId")));
        Long classId = Long.valueOf(String.valueOf(map.get("classId")));
        Long teamId = Long.valueOf(String.valueOf(map.get("teamId")));
        Double reportScore = Double.valueOf(String.valueOf(map.get("reportScore")));
        if (seminarId == null || classId == null || teamId == null || reportScore == null) {
            throw new MyException("存在为空的字段，或者参数名错误", MyException.ERROR);
        }
        return ResponseEntity.ok(scoreService.updateReportScore(seminarId, classId, teamId, reportScore));
    }

    /**
     * Description: （报告）打分 * 6
     *
     * @Author: 17Wang
     * @Time: 9:26 2018/12/29
     */
    @PutMapping("/seminarscore/reportscore/all")
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<Boolean> updateAllReportScore(@RequestParam("seminarId") long seminarId, @RequestParam("classId") long classId, @RequestBody List<ScoreDTO> scoreDTOS) throws Exception {
        for (ScoreDTO scoreDTO : scoreDTOS) {
            scoreService.updateReportScore(seminarId, classId, scoreDTO.getTeamId(), scoreDTO.getReportScore());
        }
        return ResponseEntity.ok(true);
    }

    /**
     * Description: 获取一个轮次的所有小组的成绩
     *
     * @Author: 17Wang
     * @Time: 21:31 2018/12/22
     */
    @GetMapping("/roundscore")
    @ResponseBody
    public Map<String, Object> listAllRoundScore(@RequestParam("roundId") long roundId) throws MyException {
        //带有队伍信息的
        List<RoundScore> roundScores = scoreService.listAllRoundScoreByRoundId(roundId);
        return null;
    }
}
