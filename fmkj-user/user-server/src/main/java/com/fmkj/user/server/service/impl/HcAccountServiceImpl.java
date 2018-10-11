package com.fmkj.user.server.service.impl;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.fmkj.common.base.BaseServiceImpl;
import com.fmkj.common.comenum.PointEnum;
import com.fmkj.common.util.DateUtil;
import com.fmkj.user.dao.domain.*;
import com.fmkj.user.dao.dto.GradeDto;
import com.fmkj.user.dao.dto.HcAccountDto;
import com.fmkj.user.dao.mapper.*;
import com.fmkj.user.server.service.HcAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @Description:
 * @Author: youxun
 * @Version: 1.0
 **/
@Service
@Transactional
public class HcAccountServiceImpl extends BaseServiceImpl<HcAccountMapper, HcAccount> implements HcAccountService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HcAccountServiceImpl.class);

    @Autowired
    private HcAccountMapper hcAccountMapper;

    @Autowired
    private HcPointsRecordMapper hcPointsRecordMapper;

    @Autowired
    private HcUserheadMapper hcUserheadMapper;

    @Autowired
    private HcSessionMapper hcSessionMapper;

    @Autowired
    private HcMoonwellMapper hcMoonwellMapper;

    @Autowired
    private HcToolsMapper hcToolsMapper;

    /**
     * @author yangshengbin
     * @Description：查询最新一条中奖用户信息
     * @date 2018/9/4 0004 10:48
     * @param
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    public List<Map<String, Object>> queryOneNewNotice() {
        return hcAccountMapper.queryOneNewNotice();
    }

    @Override
    public boolean bindEmail(HcAccount ha) {
        int result = hcAccountMapper.updateById(ha);
        if(result > 0){
            HcPointsRecord hcp = new HcPointsRecord();
            hcp.setUid(ha.getId());
            hcp.setPointsId(PointEnum.BIND_EMAIL.pointId);
            hcp.setPointsNum(PointEnum.BIND_EMAIL.pointNum);
            hcPointsRecordMapper.insert(hcp);
            return true;
        }
        return false;
    }

    @Override
    public HcAccount queryUserTaskMessage(Integer uid) {
        return hcAccountMapper.queryUserTaskMessage(uid);
    }

    @Override
    public List<HcAccount> queryAllFriends(Pagination pagination, Integer uid) {
        return hcAccountMapper.selectAllFriends(pagination, uid);
    }

    @Override
    public void uploadUserHead(HcAccount hcAccount, String fileName, String path) {
        int update = hcAccountMapper.updateById(hcAccount);
        if(update > 0){
            HcUserhead hcUserhead = new HcUserhead();
            hcUserhead.setName(fileName);
            hcUserhead.setUrl(path);
            hcUserhead.setUid(hcAccount.getId());
            hcUserhead.setTime(new Date());
            int row = hcUserheadMapper.insert(hcUserhead);
            if(row > 0){
                HcPointsRecord record = new HcPointsRecord();
                record.setPointsId(PointEnum.UPLOAD_HEAD.pointId);
                record.setPointsNum(PointEnum.UPLOAD_HEAD.pointNum);
                record.setUid(hcAccount.getId());
                record.setTime(DateUtil.getNowInMillis(0L));
                hcPointsRecordMapper.insert(record);
            }
        }
    }

    @Override
    public HcAccountDto selectAccountById(Integer id) {
        return hcAccountMapper.selectAccountById(id);
    }

    @Override
    public boolean loginByRcodeAndPhone(HcAccount ha, Integer uid) {
        int row = hcAccountMapper.insert(ha);
        if(row > 0){
            HcAccount where = new HcAccount();
            where.setTelephone(ha.getTelephone());
            HcAccount resultHc =  hcAccountMapper.selectOne(where);
            // 插入hcSession表
            String token = UUID.randomUUID().toString().replace("-", "").toLowerCase();
            Timestamp btime = DateUtil.getNowInMillis(0L);// 获取当前时间戳
            Timestamp etime = DateUtil.getNowInMillis(24L * 3600L * 1000L * 30L);// 获取30天后的时间戳
            Timestamp ltime = DateUtil.getNowInMillis(0L);//
            HcSession hcSession = new HcSession();
            hcSession.setToken(token);
            hcSession.setBtime(btime);
            hcSession.setEtime(etime);
            hcSession.setLtime(ltime);
            hcSession.setUid(resultHc.getId());
            int insert = hcSessionMapper.insert(hcSession);
            if(insert > 0){
                // 插入月亮井表
                HcMoonwell hm = new HcMoonwell();
                hm.setUid(resultHc.getId());
                hm.setCurrentP(0D);
                hm.setMoonnum(0);
                hm.setRocknum(0);
                int moon = hcMoonwellMapper.insert(hm);
                if(moon > 0){
                    // 插入道具表
                    HcTools ht = new HcTools();
                    ht.setUid(resultHc.getId());
                    ht.setTotalrock(3);
                    ht.setTotalmoon(1);
                    int tool = hcToolsMapper.insert(ht);
                    if(tool > 0){
                        // 插入被邀请的人的积分记录(注册的积分记录)
                        HcPointsRecord record = new HcPointsRecord();
                        record.setPointsId(PointEnum.SELF_REGISTER.pointId);
                        record.setPointsNum(PointEnum.SELF_REGISTER.pointNum);
                        record.setUid(resultHc.getId());
                        record.setTime(DateUtil.getNowInMillis(0L));
                        int point = hcPointsRecordMapper.insert(record);
                        if(point > 0){
                            //插入邀请人注册的积分奖励记录
                            HcPointsRecord hcPointsRecord = new HcPointsRecord();
                            hcPointsRecord.setPointsId(PointEnum.INVIT_REGISTER.pointId);
                            hcPointsRecord.setPointsNum(PointEnum.INVIT_REGISTER.pointNum);
                            hcPointsRecord.setUid(uid);
                            hcPointsRecord.setTime(DateUtil.getNowInMillis(0L));
                            int invit = hcPointsRecordMapper.insert(hcPointsRecord);
                            if(invit > 0){
                                //给邀请人1P奖励
                                HcAccount hcAccount = hcAccountMapper.selectById(uid);
                                hcAccount.setMyP(hcAccount.getMyP() + 1);
                                int update = hcAccountMapper.updateById(hcAccount);
                                if(update > 0){
                                    GradeDto gradeDto = hcPointsRecordMapper.selectGrandByUid(hcAccount.getId());
                                    boolean change = changeGrade(gradeDto, ha.getId());
                                    if (change)
                                        return true;
                                    else
                                        throw new RuntimeException("用户等级更新失败！");
                                }
                                else
                                    throw new RuntimeException("给邀请人1P失败！");
                            }
                            else
                                throw new RuntimeException("插入邀请人注册的积分奖励记录失败！");
                        }else
                            throw new RuntimeException("插入被邀请的人的积分记录(注册的积分记录)失败！");
                    }else
                        throw new RuntimeException("插入道具表出现失败！");
                }else
                    throw new RuntimeException("插入月亮井表出现失败！");
            }else
                throw new RuntimeException("插入hcSession表出现失败！");
        }
        return false;
    }

    @Override
    public boolean loginByTelephone(Integer id) {

        String token = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        Timestamp btime = DateUtil.getNowInMillis(0L);// 获取当前时间戳
        Timestamp etime = DateUtil.getNowInMillis(24L * 3600L * 1000L * 30L);// 获取30天后的时间戳
        Timestamp ltime = DateUtil.getNowInMillis(0L);//
        HcSession upSession = new HcSession();
        upSession.setToken(token);
        upSession.setBtime(btime);
        upSession.setEtime(etime);
        upSession.setLtime(ltime);
        upSession.setUid(id);

        HcSession hcSession = new HcSession();
        hcSession.setUid(id);
        int count = hcSessionMapper.selectCount(new EntityWrapper<>(hcSession));
        boolean hasNext = false;
        if(count > 0){
            int update = hcSessionMapper.updateHcSession(upSession);
            if(update > 0){
                hasNext = true;
            }
        }else{
           int row = hcSessionMapper.insert(upSession);
            if(row > 0){
                hasNext = true;
            }
        }
        if(hasNext){
            HcAccount hcAccount = new HcAccount();
            hcAccount.setAuthlock(true);
            hcAccount.setId(id);
            int hcRow = hcAccountMapper.updateById(hcAccount);
            if(hcRow > 0)
                return true;
            else
                throw new RuntimeException("更新AuthLock失败!");
        }
        return false;
    }

    public boolean changeGrade(GradeDto gb, Integer uid) {
        Integer num1 = paseInteger(gb.getNum1());//签到次数
        Integer num2 = paseInteger(gb.getNum2());//参与竞拍次数
        Integer num3 = paseInteger(gb.getNum3());//发起竞拍次数
        Integer num4 = paseInteger(gb.getNum4());//购买月光次数
        Integer num5 = paseInteger(gb.getNum5());//购买石头次数
        Integer num6 = paseInteger(gb.getNum6());//邀请别人注册次数
        Integer num7 = paseInteger(gb.getNum7());//实名认证次数 ==1
        Integer num8 = paseInteger(gb.getNum8());//头像上传次数  ==1
        Integer num9 = paseInteger(gb.getNum9());//绑定邮箱次数  ==1
		Integer num10 = paseInteger(gb.getNum10());//充值次数

        //用户等级5级更新
        if(num1>=290 && num2>=570 && num3>=60 && num4>=30 && num5>=60 &&
                num6>=9 && num7>=1 && num8>=1 && num9>=1) {
            HcAccount ha = new HcAccount();
            ha.setId(uid);
            ha.setGradeId(5);
            hcAccountMapper.updateById(ha);
            return true;
        }
        //用户等级4级更新
        if(num1>=130 && num2>=250 && num3>=30 && num4>=20 &&
                num5>=40 && num6>=6 && num7>=1 && num8>=1 && num9>=1) {
            HcAccount ha = new HcAccount();
            ha.setId(uid);
            ha.setGradeId(4);
            hcAccountMapper.updateById(ha);
            return true;
        }
        //用户等级3级更新
        if(num1>=50 && num2>=90 && num3>=10 && num4>=10 &&
                num5>=20 && num6>=3 && num7>=1 && num8>=1 && num9>=1) {
            HcAccount ha = new HcAccount();
            ha.setId(uid);
            ha.setGradeId(3);
            hcAccountMapper.updateById(ha);
            return true;
        }
        //用户等级2级更新
        if(num1>=10 && num2>=10 && num3>=1 && num4>=1 &&
                num5>=2 && num6>=1 && num7>=1 && num8>=1 && num9>=1) {
            HcAccount ha = new HcAccount();
            ha.setId(uid);
            ha.setGradeId(2);
            hcAccountMapper.updateById(ha);
            return true;
        }
        //用户等级1级更新
        if(num1>=0 && num2>=0 && num3>=0 && num4>=0 &&
                num5>=0 && num6>=0 && num7>=0 && num8>=0 && num9>=0) {
            HcAccount ha = new HcAccount();
            ha.setId(uid);
            ha.setGradeId(1);
            hcAccountMapper.updateById(ha);
            return true;
        }
        return false;
    }

    public Integer paseInteger(Integer num) {
        if(num==null) {
            num=0;
        }
        return num;
    }

}