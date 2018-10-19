package com.fmkj.race.server.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.fmkj.common.annotation.BaseService;
import com.fmkj.common.base.BaseServiceImpl;
import com.fmkj.common.comenum.PointEnum;
import com.fmkj.race.dao.domain.GcActivity;
import com.fmkj.race.dao.domain.GcJoinactivityrecord;
import com.fmkj.race.dao.dto.JoinActivityDto;
import com.fmkj.race.dao.mapper.GcActivityMapper;
import com.fmkj.race.dao.mapper.GcJoinactivityrecordMapper;
import com.fmkj.race.dao.queryVo.JoinActivityPage;
import com.fmkj.race.server.api.HcAccountApi;
import com.fmkj.race.server.hammer.contracts.PuzzleHammer.puzzle.Helper;
import com.fmkj.race.server.hammer.contracts.PuzzleHammer.puzzle.Person;
import com.fmkj.race.server.hammer.contracts.PuzzleHammer.puzzle.State;
import com.fmkj.race.server.rabbitmq.MessageProducer;
import com.fmkj.race.server.service.GcJoinactivityrecordService;
import com.fmkj.race.server.util.CalendarTime;
import com.fmkj.user.dao.domain.HcAccount;
import com.fmkj.user.dao.domain.HcPointsRecord;
import com.fmkj.user.dao.mapper.HcPointsRecordMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;

/**
* @Description: GcJoinactivityrecord Service实现
* @Author: yangshengbin
* @CreateDate: 2018/9/5.
* @Version: 1.0
**/
@Service
@Transactional
@BaseService
public class GcJoinactivityrecordServiceImpl extends BaseServiceImpl<GcJoinactivityrecordMapper, GcJoinactivityrecord> implements GcJoinactivityrecordService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GcJoinactivityrecordServiceImpl.class);

    @Autowired
    private GcJoinactivityrecordMapper gcJoinactivityrecordMapper;

    @Autowired
    private GcActivityMapper gcActivityMapper;

    @Autowired
    private HcAccountApi hcAccountApi;

    @Autowired
    private HcPointsRecordMapper hcPointsRecordMapper;

    @Autowired
    private MessageProducer messageProducer;

    /**
     * @author yangshengbin
     * @Description：插入用户参与记录/更改用户p能量值/
     * @date 2018/9/6 0006 10:02
     * @param aid
     * @param joins
     * @param par
     * @return boolean
    */
    @Override
    public boolean addGcJoinactivityrecordAndUpAccount(Integer aid, GcJoinactivityrecord joins, double par) {
        //更改用户CNT
        try {
            HcAccount hc = new HcAccount();
            hc.setId(joins.getUid());
            hc.setCnt(par);
            boolean flag = hcAccountApi.updateUserP(hc);
            LOGGER.info("更改用户CNT返回结果：" + flag);
            if (flag){
                GcJoinactivityrecord gjr = new GcJoinactivityrecord();
                gjr.setId(joins.getId());
                gjr.setIschain(2);
                int row = gcJoinactivityrecordMapper.updateById(gjr);
                return true;
            }
            return false;
        } catch (Exception e1) {
            throw new RuntimeException("更改用户cnt异常，活动aid:"+aid+",用户:"+joins.getUid()+"," + e1.getMessage());
        }
    }


    /**
     * @author yangshengbin
     * @Description：获取用户合约地址
     * @date 2018/9/6 0006 10:58
     * @param aid
     * @return java.lang.String
    */
    public String queryGcActivityByContract(Integer aid) {
        GcActivity ga = new GcActivity();
        ga.setId(aid);
        GcActivity activity = null;
        String contract = null;
        try {
            activity = gcActivityMapper.selectOne(ga);
        } catch (Exception e) {
            throw new RuntimeException("获取合约地址异常" + e.getMessage());
        }
        if (activity!=null) {
            contract = activity.getContract();
        }
        return contract;
    }






    /**
     * @author yangshengbin
     * @Description：参加活动加载合约
     * @date 2018/9/6 0006 11:03
     * @param contract
     * @param aid
     * @param uid
     * @return boolean
    */
    public boolean participateActivity(String contract, Integer aid, Integer uid,Integer gid) {
        if (contract != null) {    //如果合约地址不为空。根据合约地址把参加活动的人上到对应链上
            Helper helper = new Helper();
            boolean init = helper.init();// 合约实例初始化
            if (!init) {
                LOGGER.error("合约初始化失败");
                return false;
            }
            boolean loadContract = helper.loadContract(contract);//加载合约
            if (!loadContract) {
                LOGGER.error("合约地址加载失败");
                return false;
            }

            helper.changeStage(State.participate);

            HcAccount user = hcAccountApi.selectHcAccountById(uid);// 获取参与活动的用户信息

            helper.particiPuzzle(new Person(user.getNickname(), BigInteger.valueOf(uid)));// 实例出合约用户，参与活动

            //将用户上链记录改为1
            GcJoinactivityrecord gcJoinactivityrecord = new GcJoinactivityrecord();
            gcJoinactivityrecord.setIschain(1);
            GcJoinactivityrecord gcJoinactivityrecord1 = new GcJoinactivityrecord();
            gcJoinactivityrecord1.setUid(uid);
            gcJoinactivityrecord1.setId(gid);
            EntityWrapper<GcJoinactivityrecord> entityWrapper = new EntityWrapper<GcJoinactivityrecord>();
            entityWrapper.setEntity(gcJoinactivityrecord1);

            GcJoinactivityrecord gjr = new GcJoinactivityrecord();
            gjr.setId(gid);
            gjr.setIschain(2);
            try {
                Integer res = gcJoinactivityrecordMapper.update(gcJoinactivityrecord,entityWrapper);
            } catch (Exception e) {
                gcJoinactivityrecordMapper.updateById(gjr);
                throw new RuntimeException("更改用户上链记录异常" + e.getMessage());
            }
            helper.release();
        }
        return true;
    }





    @Override
    /**
     * @author yangshengbin
     * @Description：最后一个用户参与活动
     * @date 2018/9/6 0006 14:12
     * @param contract
     * @param aid
     * @return boolean
    */
    public boolean initAndloadContractAndChangeStage(String contract, Integer aid) {
        Helper helper = new Helper();
        // 合约实例初始化
        boolean init = helper.init();
        if (!init) {
            LOGGER.error("合约初始化失败");
            return false;
        }

        //加载合约
        boolean loadContract = helper.loadContract(contract);
        if (!loadContract) {
            LOGGER.error("合约地址加载失败");
            return false;
        }

        //更新活动状态
        int res = updateGcJoinacTivityByStatus(aid);
        if (res<=0) {
            LOGGER.error("更新活动状态失败");
            return false;
        }

        //通知活动关闭
        boolean stage = helper.changeStage(State.closed);
        if(!stage) {
            LOGGER.error("合约关闭失败");
            return false;
        }
        return true;
    }



    /**
     * @author 杨胜彬
     * @comments 更新活动状态
     * @time 2018年8月8日 上午11:22:10
     * @param aid
     * @return
     * @returnType int
     * @modification
     */
    public int updateGcJoinacTivityByStatus(Integer aid) {
        GcActivity gat = new GcActivity();
        gat.setId(aid);
        gat.setStatus(3);
        int res;
        try {
            res = gcActivityMapper.updateById(gat);
        } catch (Exception e) {
            throw new RuntimeException("更新活动状态异常" + e.getMessage());
        }
        return res;
    }


    /**
     * 活动参与记录
     * @author ru
     * @param aid
     * @param page
     * @return
     */
    public  List<JoinActivityDto>  queryJoinActivityByAid(Pagination page, JoinActivityPage joinActivityPage) {
        return gcJoinactivityrecordMapper.queryJoinActivityByAid(page,joinActivityPage);
    }

    @Override
    public boolean addGcJoinactivityRecord(GcJoinactivityrecord gcJoin) {
        int row = gcJoinactivityrecordMapper.insert(gcJoin);
        if(row > 0){
            //参与活动添加10飞羽
            HcPointsRecord hcp = new HcPointsRecord();
            hcp.setUid(gcJoin.getUid());
            hcp.setPointsId(PointEnum.PART_ACITIVITY.pointId);
            hcp.setPointsNum(PointEnum.PART_ACITIVITY.pointNum);
            hcPointsRecordMapper.insert(hcp);
            messageProducer.send(JSON.toJSONString(gcJoin));//生产消息
            return true;
        }
        return false;
    }

}