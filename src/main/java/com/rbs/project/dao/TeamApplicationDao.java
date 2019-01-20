package com.rbs.project.dao;

import com.rbs.project.exception.MyException;
import com.rbs.project.mapper.TeamMapper;
import com.rbs.project.mapper.TeamValidApplicationMapper;
import com.rbs.project.pojo.entity.Team;
import com.rbs.project.pojo.entity.TeamValidApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 13:44 2018/12/23
 */
@Repository
public class TeamApplicationDao {
    @Autowired
    private TeamValidApplicationMapper teamValidApplicationMapper;

    @Autowired
    private TeamMapper teamMapper;

    public final static int HAS_TEAM = 0;

    private void hasSomethingFun(TeamValidApplication teamValidApplication, int... hasSomething) {
        for (int i : hasSomething) {
            if (i == HAS_TEAM) {
                teamValidApplication.setTeam(teamMapper.findById(teamValidApplication.getTeamId()));
            }
        }
    }

    /**
     * Description: 通过id获取一条请求
     *
     * @Author: 17Wang
     * @Time: 14:55 2018/12/23
     */
    public TeamValidApplication getTeamValidRequestById(long id) throws MyException {
        TeamValidApplication teamValidApplication = teamValidApplicationMapper.findById(id);
        if (teamValidApplication == null) {
            throw new MyException("通过id获取一条合法化请求失败！不存在该记录", MyException.NOT_FOUND_ERROR);
        }
        return teamValidApplication;
    }

    /**
     * Description: 通过teawmid和teacherid获取一条合法化请求
     *
     * @Author: 17Wang
     * @Time: 14:26 2018/12/23
     */
    public TeamValidApplication getTeamValidRequestByTeamIdAndTeacherId(long teamId, long teacherId) throws MyException {
        TeamValidApplication teamValidApplication = teamValidApplicationMapper.findByTeamIdAndTeacherId(teamId, teacherId);
        if (teamValidApplication == null) {
            throw new MyException("通过teawmid和teacherid获取一条合法化请求失败！不存在该记录", MyException.NOT_FOUND_ERROR);
        }
        return teamValidApplication;
    }

    /**
     * Description: 通过teacherid获取所有合法化请求
     *
     * @Author: 17Wang
     * @Time: 14:27 2018/12/23
     */
    public List<TeamValidApplication> getTeamValidRequestByTeacherId(long teacherId, int... hasSomething) {
        List<TeamValidApplication> teamValidApplications = teamValidApplicationMapper.findByTeacherId(teacherId);
        for (TeamValidApplication teamValidApplication : teamValidApplications) {
            hasSomethingFun(teamValidApplication, hasSomething);
        }
        return teamValidApplications;
    }

    /**
     * Description: 队伍合法化请求
     *
     * @Author: 17Wang
     * @Time: 13:49 2018/12/23
     */
    public Long addTeamValidApplication(TeamValidApplication teamValidApplication) throws Exception {
        //判断表里是否已存在该请求
        TeamValidApplication myApplication = teamValidApplicationMapper.findByTeamIdAndTeacherId(teamValidApplication.getTeamId(), teamValidApplication.getTeacherId());
        if (myApplication != null) {
            //更新reason字段
            teamValidApplicationMapper.updateReasonById(teamValidApplication.getReason(), myApplication.getId());
            return myApplication.getId();
        }
        //确认状态为UNDO
        teamValidApplication.setStatus(TeamValidApplication.STATUS_UNDO);
        teamValidApplicationMapper.insertApplication(teamValidApplication);
        return teamValidApplication.getId();
    }

    /**
     * Description: 处理请求
     *
     * @Author: 17Wang
     * @Time: 14:52 2018/12/23
     */
    public boolean updateTeamValidApplicationStatusById(long requestId, int status) throws Exception {
        //查询是否存在
        getTeamValidRequestById(requestId);
        if (!teamValidApplicationMapper.updateStatusById(status, requestId)) {
            throw new MyException("处理请求失败！处理数据库错误", MyException.ERROR);
        }
        return true;
    }

    /**
     * Description: 通过team_id删除队伍合法状态请求
     * @Author: WinstonDeng
     * @Date: 15:03 2018/12/25
     */
    public boolean deleteTeamValidApplicationByTeamId(long teamId) throws MyException {
        return teamValidApplicationMapper.deleteByTeamId(teamId);
    }
}
