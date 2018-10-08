package com.fmkj.user.server.service;

import com.fmkj.common.base.BaseService;
import com.fmkj.user.dao.domain.HcFriend;

/**
* @Description: HcFriend Service接口
* @Author: youxun
* @CreateDate: 2018/9/19.
* @Version: 1.0
**/
public interface HcFriendService extends BaseService<HcFriend> {

    void addFriend(HcFriend hcFriend);

    void passFReq(HcFriend hcFriend);
}