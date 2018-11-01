package com.fmkj.user.server.service.impl;

import com.fmkj.common.annotation.BaseService;
import com.fmkj.common.base.BaseServiceImpl;
import com.fmkj.user.dao.domain.BaseBean;
import com.fmkj.user.dao.domain.HcPointsRecord;
import com.fmkj.user.dao.mapper.HcPointsRecordMapper;
import com.fmkj.user.server.service.HcPointsRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* @Description: HcPointsRecord Service实现
* @Author: youxun
* @CreateDate: 2018/9/17.
* @Version: 1.0
**/
@Service
@Transactional
@BaseService
public class HcPointsRecordServiceImpl extends BaseServiceImpl<HcPointsRecordMapper, HcPointsRecord> implements HcPointsRecordService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HcPointsRecordServiceImpl.class);

    @Autowired
    private HcPointsRecordMapper hcPointsRecordMapper;

    @Override
    public HcPointsRecord getHcPointsRecord(Integer uid) {
        return hcPointsRecordMapper.getHcPointsRecord(uid);
    }

    @Override
    public Integer queryPoints(Integer uid) {
        return hcPointsRecordMapper.queryPoints(uid);
    }

    @Override
    public List<BaseBean> queryInvitingFriendsRankWeek() {
        return hcPointsRecordMapper.queryInvitingFriendsRankWeek();
    }

    @Override
    public Integer queryUserScoresByUid(Integer uid) {
        return hcPointsRecordMapper.queryUserScoresByUid(uid);
    }
}