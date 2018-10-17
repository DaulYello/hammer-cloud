package com.fmkj.user.server.service.impl;

import com.fmkj.common.annotation.BaseService;
import com.fmkj.common.base.BaseServiceImpl;
import com.fmkj.common.util.StringUtils;
import com.fmkj.user.dao.domain.FmRecyleLog;
import com.fmkj.user.dao.domain.HcAccount;
import com.fmkj.user.dao.dto.CntRDto;
import com.fmkj.user.dao.mapper.FmCntInfoMapper;
import com.fmkj.user.dao.mapper.FmIntegralInfoMapper;
import com.fmkj.user.dao.domain.FmIntegralInfo;
import com.fmkj.user.dao.mapper.FmRecyleLogMapper;
import com.fmkj.user.dao.mapper.HcAccountMapper;
import com.fmkj.user.dao.queryVo.CntRVo;
import com.fmkj.user.server.enmu.RecyleEnum;
import com.fmkj.user.server.enmu.TakeEnum;
import com.fmkj.user.server.service.FmIntegralInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
* @Description: FmIntegralInfo Service实现
* @Author: youxun
* @CreateDate: 2018/10/16.
* @Version: 1.0
**/
@Service
@Transactional
@BaseService
public class FmIntegralInfoServiceImpl extends BaseServiceImpl<FmIntegralInfoMapper, FmIntegralInfo> implements FmIntegralInfoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FmIntegralInfoServiceImpl.class);

    @Autowired
    private FmIntegralInfoMapper fmIntegralInfoMapper;

    @Autowired
    private FmCntInfoMapper fmCntInfoMapper;

    @Autowired
    private FmRecyleLogMapper fmRecyleLogMapper;

    @Autowired
    private HcAccountMapper hcAccountMapper;

    @Override
    public FmIntegralInfo stealRIntegral(CntRVo cntRVo) {
        FmIntegralInfo where = new FmIntegralInfo();
        where.setId(cntRVo.getrId());
        where.setStatus(0);
        FmIntegralInfo fmIntegralInfo = fmIntegralInfoMapper.selectOne(where);
        if(StringUtils.isNull(fmIntegralInfo)){
            return null;
        }
        Date now = new Date();
        //被好友偷走
        fmIntegralInfo.setStatus(-1);
        fmIntegralInfo.setUpdateDate(now);
        int row = fmIntegralInfoMapper.updateById(fmIntegralInfo);
        if(row > 0){
            HcAccount hcAccount = hcAccountMapper.selectById(cntRVo.getUid());
            hcAccount.setMyP(hcAccount.getMyP() + fmIntegralInfo.getIntegralNum());
            int updateRow = hcAccountMapper.updateById(hcAccount);
            if(updateRow > 0){
                FmRecyleLog fmRecyleLog = new FmRecyleLog();
                fmRecyleLog.setUid(cntRVo.getUid());
                fmRecyleLog.setFriendId(cntRVo.getFriendId());
                fmRecyleLog.setTakeDate(now);
                fmRecyleLog.setTakeNum(fmIntegralInfo.getIntegralNum());
                fmRecyleLog.setRecyleType(RecyleEnum.TYPE_R.status);
                fmRecyleLog.setTakeType(TakeEnum.TYPE_USER.status);
                fmRecyleLog.setTakeMsg("偷取了您" + fmIntegralInfo.getIntegralNum() + "R积分");
                int insertRow = fmRecyleLogMapper.insert(fmRecyleLog);
                if(insertRow > 0){
                    return fmIntegralInfo;
                }else{
                    throw new RuntimeException("stealRIntegral插入FmRecyleLog失败！");
                }
            }else{
                throw new RuntimeException("stealRIntegral更新hcAccount失败");
            }
        }else
            throw new RuntimeException("stealRIntegral更新FmIntegralInfo失败");
    }

    @Override
    public FmIntegralInfo getRIntegral(CntRVo cntRVo) {
        FmIntegralInfo where = new FmIntegralInfo();
        where.setId(cntRVo.getrId());
        where.setStatus(0);
        FmIntegralInfo fmIntegralInfo = fmIntegralInfoMapper.selectOne(where);
        if(StringUtils.isNull(fmIntegralInfo)){
            return null;
        }
        Date now = new Date();
        fmIntegralInfo.setStatus(1);
        fmIntegralInfo.setUpdateDate(now);
        int updateRow = fmIntegralInfoMapper.updateById(fmIntegralInfo);
        if(updateRow > 0){
            HcAccount hcAccount = hcAccountMapper.selectById(cntRVo.getUid());
            hcAccount.setMyP(hcAccount.getMyP() + fmIntegralInfo.getIntegralNum());
            int row = hcAccountMapper.updateById(hcAccount);
            if(row > 0){
                FmRecyleLog fmRecyleLog = new FmRecyleLog();
                fmRecyleLog.setUid(cntRVo.getUid());
                fmRecyleLog.setFriendId(cntRVo.getUid());
                fmRecyleLog.setTakeDate(now);
                fmRecyleLog.setTakeNum(fmIntegralInfo.getIntegralNum());
                fmRecyleLog.setRecyleType(RecyleEnum.TYPE_R.status);
                fmRecyleLog.setTakeType(TakeEnum.TYPE_USER.status);
                fmRecyleLog.setTakeMsg("您获取了" + fmIntegralInfo.getIntegralNum() + "R积分");
                int insertRow = fmRecyleLogMapper.insert(fmRecyleLog);
                if(insertRow > 0){
                    return fmIntegralInfo;
                }else{
                    throw new RuntimeException("getRIntegral插入FmRecyleLog失败！");
                }
            }else {
                throw new RuntimeException("getRIntegral更新hcAccount失败！");
            }
        }else{
            throw new RuntimeException("getRIntegral更新FmIntegralInfo失败！");
        }
    }

    @Override
    public CntRDto queryCNTRbyId(Integer uid) {
        CntRDto cntRDto = new CntRDto();
        //统计当天R积分获取的总和
        Double rNum = fmRecyleLogMapper.queryRNumDay(uid);
        cntRDto.setrNum(rNum);
        //统计当天CNT获取的总和
        Double cntNum = fmRecyleLogMapper.queryCntNumDay(uid);
        cntRDto.setCntNum(cntNum);
        return cntRDto;
    }

    @Override
    public List<FmIntegralInfo> queryRList(HashMap<String, Object> paramMap) {
        return fmIntegralInfoMapper.queryRList(paramMap);
    }


}