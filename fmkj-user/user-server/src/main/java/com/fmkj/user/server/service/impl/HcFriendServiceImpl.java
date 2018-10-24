package com.fmkj.user.server.service.impl;

import com.fmkj.common.annotation.BaseService;
import com.fmkj.common.base.BaseServiceImpl;
import com.fmkj.user.dao.domain.FmRecyleLog;
import com.fmkj.user.dao.mapper.FmRecyleLogMapper;
import com.fmkj.user.dao.mapper.HcFriendMapper;
import com.fmkj.user.dao.domain.HcFriend;
import com.fmkj.user.server.service.HcFriendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* @Description: HcFriend Service实现
* @Author: youxun
* @CreateDate: 2018/9/19.
* @Version: 1.0
**/
@Service
@Transactional
@BaseService
public class HcFriendServiceImpl extends BaseServiceImpl<HcFriendMapper, HcFriend> implements HcFriendService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HcFriendServiceImpl.class);

    @Autowired
    private HcFriendMapper hcFriendMapper;

    @Override
    public void addFriend(HcFriend hcFriend) {
        // 添加好友需要插入两条数据
        hcFriend.setPass(0);
        int row = hcFriendMapper.insert(hcFriend);
        if(row > 0){
            HcFriend hc = new HcFriend();
            hc.setAccountId(hcFriend.getFriendId());
            hc.setFriendId(hcFriend.getAccountId());
            hc.setMsg(hcFriend.getMsg());
            hc.setPass(0);
            hcFriendMapper.insert(hc);
        }
    }

    @Override
    public void passFReq(HcFriend hcFriend) {
        int row = hcFriendMapper.passFReq(hcFriend);
        if(row > 0){
            HcFriend hc = new HcFriend();
            hc.setAccountId(hcFriend.getFriendId());
            hc.setFriendId(hcFriend.getAccountId());
            hc.setMsg(hcFriend.getMsg());
            hc.setPass(hcFriend.getPass());
            hcFriendMapper.passFReq(hc);
        }
    }


}