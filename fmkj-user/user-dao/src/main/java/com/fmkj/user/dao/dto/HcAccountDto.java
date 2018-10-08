package com.fmkj.user.dao.dto;

import com.fmkj.user.dao.domain.HcAccount;

public class HcAccountDto extends HcAccount {
    /**
     * 好友数量
     */
    private Integer frirendNum;

    /**
     * 任务数量
     */
    private Integer taskNum;

    public Integer getFrirendNum() {
        return frirendNum;
    }

    public void setFrirendNum(Integer frirendNum) {
        this.frirendNum = frirendNum;
    }

    public Integer getTaskNum() {
        return taskNum;
    }

    public void setTaskNum(Integer taskNum) {
        this.taskNum = taskNum;
    }
}
