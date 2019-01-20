package com.rbs.project.pojo;

/**
 * @Author: 17Wang
 * @Date: 19:17 2018/12/5
 * @Description:
 */
public class RespInfo {
    private Integer status;
    private String msg;
    private Object obj;
    private String jwtToken;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}
