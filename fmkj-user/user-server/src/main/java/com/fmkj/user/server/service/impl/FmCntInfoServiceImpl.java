package com.fmkj.user.server.service.impl;

import com.fmkj.common.annotation.BaseService;
import com.fmkj.common.base.BaseServiceImpl;
import com.fmkj.common.util.StringUtils;
import com.fmkj.user.dao.domain.FmIntegralInfo;
import com.fmkj.user.dao.domain.FmRecyleLog;
import com.fmkj.user.dao.domain.HcAccount;
import com.fmkj.user.dao.mapper.FmCntInfoMapper;
import com.fmkj.user.dao.domain.FmCntInfo;
import com.fmkj.user.dao.mapper.FmRecyleLogMapper;
import com.fmkj.user.dao.mapper.HcAccountMapper;
import com.fmkj.user.dao.queryVo.CntRVo;
import com.fmkj.user.server.enmu.RecyleEnum;
import com.fmkj.user.server.enmu.TakeEnum;
import com.fmkj.user.server.service.FmCntInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
* @Description: FmCntInfo Service实现
* @Author: youxun
* @CreateDate: 2018/10/16.
* @Version: 1.0
**/
@Service
@Transactional
@BaseService
public class FmCntInfoServiceImpl extends BaseServiceImpl<FmCntInfoMapper, FmCntInfo> implements FmCntInfoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FmCntInfoServiceImpl.class);

    @Autowired
    private FmCntInfoMapper fmCntInfoMapper;

    @Autowired
    private HcAccountMapper hcAccountMapper;

    @Autowired
    private FmRecyleLogMapper fmRecyleLogMapper;

    @Override
    public FmCntInfo getCntByUid(CntRVo cntRVo) {
        FmCntInfo where = new FmCntInfo();
        where.setId(cntRVo.getCntId());
        where.setStatus(0);
        FmCntInfo fmCntInfo = fmCntInfoMapper.selectOne(where);
        if(StringUtils.isNull(fmCntInfo)){
            return null;
        }
        Date now = new Date();
        fmCntInfo.setStatus(1);
        fmCntInfo.setUpdateDate(now);
        int row = fmCntInfoMapper.updateById(fmCntInfo);
        if(row > 0){
            HcAccount hcAccount = hcAccountMapper.selectById(cntRVo.getUid());
            hcAccount.setCnt(hcAccount.getCnt() + fmCntInfo.getCntNum());
            int updateRow = hcAccountMapper.updateById(hcAccount);
            if(updateRow > 0){
                FmRecyleLog fmRecyleLog = new FmRecyleLog();
                fmRecyleLog.setUid(cntRVo.getUid());
                fmRecyleLog.setFriendId(cntRVo.getUid());
                fmRecyleLog.setTakeDate(now);
                fmRecyleLog.setTakeNum(fmCntInfo.getCntNum());
                fmRecyleLog.setRecyleType(RecyleEnum.TYPE_CNT.status);
                fmRecyleLog.setTakeType(TakeEnum.USER_GET.status);
                fmRecyleLog.setTakeMsg("您获取了" + fmCntInfo.getCntNum() + "CNT");
                int insertRow = fmRecyleLogMapper.insert(fmRecyleLog);
                if(insertRow > 0){
                    return fmCntInfo;
                }else{
                    throw new RuntimeException("getCntByUid插入fmRecyleLog失败！");
                }
            }else{
                throw new RuntimeException("getCntByUid更新hcAccount失败！");
            }
        }else{
            throw new RuntimeException("getCntByUid更新fmCntInfo失败！");
        }
    }

    @Override
    public List<FmIntegralInfo> queryCNTList(HashMap<String, Object> paramMap) {
        return fmCntInfoMapper.queryCNTList(paramMap);
    }
}