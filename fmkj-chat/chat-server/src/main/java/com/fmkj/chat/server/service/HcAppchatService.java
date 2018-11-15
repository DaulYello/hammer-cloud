package com.fmkj.chat.server.service;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.fmkj.chat.dao.domain.HcAppchat;
import com.fmkj.chat.dao.domain.User;
import com.fmkj.chat.dao.domain.WebMessage;
import com.fmkj.chat.dao.queryVo.ChatQueryVo;
import com.fmkj.common.base.BaseService;

import java.util.HashMap;
import java.util.List;

/**
* @Description: HcAppchat Service接口
* @Author: youxun
* @CreateDate: 2018/11/13.
* @Version: 1.0
**/
public interface HcAppchatService extends BaseService<HcAppchat> {

    User queryUserInfo(Integer sendId);

    List<WebMessage> getChatPage(Pagination pagination, ChatQueryVo queryVo);

    boolean updateChatStatus(ChatQueryVo queryVo);

    List queryNewsCaht(ChatQueryVo queryVo);

    List queryNewActivity();

    List queryNewsRaceInfo(ChatQueryVo queryVo);

    List queryNewsOrder(ChatQueryVo queryVo);

    List queryApplyList(ChatQueryVo queryVo);

    List queryRefuseList(ChatQueryVo queryVo);
}