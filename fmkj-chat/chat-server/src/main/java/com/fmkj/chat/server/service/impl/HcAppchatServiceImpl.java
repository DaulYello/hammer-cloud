package com.fmkj.chat.server.service.impl;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.fmkj.chat.dao.domain.User;
import com.fmkj.chat.dao.domain.WebMessage;
import com.fmkj.chat.dao.queryVo.ChatQueryVo;
import com.fmkj.common.annotation.BaseService;
import com.fmkj.common.base.BaseServiceImpl;
import com.fmkj.chat.dao.mapper.HcAppchatMapper;
import com.fmkj.chat.dao.domain.HcAppchat;
import com.fmkj.chat.server.service.HcAppchatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* @Description: HcAppchat Service实现
* @Author: youxun
* @CreateDate: 2018/11/13.
* @Version: 1.0
**/
@Service
@Transactional
@BaseService
public class HcAppchatServiceImpl extends BaseServiceImpl<HcAppchatMapper, HcAppchat> implements HcAppchatService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HcAppchatServiceImpl.class);


    @Autowired
    private HcAppchatMapper hcAppchatMapper;

    @Override
    public User queryUserInfo(Integer sendId) {
        return hcAppchatMapper.queryUserInfo(sendId);
    }

    @Override
    public List<WebMessage> getChatPage(Pagination pagination, ChatQueryVo queryVo) {
        return hcAppchatMapper.queryChatPage(pagination, queryVo);
    }

    @Override
    public boolean updateChatStatus(ChatQueryVo queryVo) {
        return hcAppchatMapper.updateChatStatus(queryVo);
    }

    @Override
    public List queryNewsCaht(ChatQueryVo queryVo) {
        return hcAppchatMapper.queryNewsCaht(queryVo);
    }

    @Override
    public List queryNewActivity() {
        return hcAppchatMapper.queryNewActivity();
    }

    @Override
    public List queryNewsRaceInfo(ChatQueryVo queryVo) {
        return hcAppchatMapper.queryNewsRaceInfo(queryVo);
    }

    @Override
    public List queryNewsOrder(ChatQueryVo queryVo) {
        return hcAppchatMapper.queryNewsOrder(queryVo);
    }

    @Override
    public List queryApplyList(ChatQueryVo queryVo) {
        return hcAppchatMapper.queryApplyList(queryVo);
    }

    @Override
    public List queryRefuseList(ChatQueryVo queryVo) {
        return hcAppchatMapper.queryRefuseList(queryVo);
    }
}