package com.fmkj.race.server.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.fmkj.common.annotation.BaseService;
import com.fmkj.common.base.BaseServiceImpl;
import com.fmkj.common.comenum.PointEnum;
import com.fmkj.common.util.StringUtils;
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
import com.fmkj.race.server.service.GcJoinactivityrecordService;
import com.fmkj.user.dao.domain.HcAccount;
import com.fmkj.user.dao.domain.HcPointsRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
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
        //如果合约地址不为空。根据合约地址把参加活动的人上到对应链上
        Helper helper = new Helper();
        boolean init = helper.init();// 合约实例初始化2秒多
        if (!init) {
            LOGGER.info("合约初始化失败");
            return false;
        }

        boolean loadContract = helper.loadContract(contract);//加载合约100mmss
        if (!loadContract) {
            LOGGER.info("合约地址加载失败");
            return false;
        }
        long startTime = System.currentTimeMillis();
        helper.changeStage(State.participate);
        long times = System.currentTimeMillis() - startTime;
        LOGGER.info("改变状态值15174：" + times);

        HcAccount user = hcAccountApi.selectHcAccountById(uid);// 获取参与活动的用户信息
        LOGGER.info("获取的用户ID：" + JSON.toJSONString(uid));
        LOGGER.info("获取的用户：" + JSON.toJSONString(user));
        long startTime1 = System.currentTimeMillis();
        helper.particiPuzzle(new Person(user.getNickname(), BigInteger.valueOf(uid)));// 实例出合约用户，参与活动
        long times1 = System.currentTimeMillis() - startTime1;
        LOGGER.info("合约particiPuzzleparticiPuzzle加载15093：" + times1);
        //将用户上链记录改为1
        GcJoinactivityrecord gcJoinactivityrecord = new GcJoinactivityrecord();
        gcJoinactivityrecord.setIschain(1);
        gcJoinactivityrecord.setId(gid);
        int updateRow = gcJoinactivityrecordMapper.updateById(gcJoinactivityrecord);
        if(updateRow <= 0){
            GcJoinactivityrecord gjr = new GcJoinactivityrecord();
            gjr.setId(gid);
            gjr.setIschain(2);
            gcJoinactivityrecordMapper.updateById(gjr);
        }
        helper.release();
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
    public boolean addGcJoinactivityRecord(GcJoinactivityrecord gcJoin, GcActivity gcActivity) {
        //查询活动信息获取合约地址参与合约
        String contract = gcActivity.getContract();
        LOGGER.info("MQ获取到的合约地址：" + contract);
        if(StringUtils.isEmpty(contract)){
            return false;
        }
        //获取当前参与人数
        GcJoinactivityrecord gcJoinactivityrecord = new GcJoinactivityrecord();
        gcJoinactivityrecord.setAid(gcJoin.getAid());
        EntityWrapper<GcJoinactivityrecord> entityWrapper = new EntityWrapper<>(gcJoinactivityrecord);
        int count = gcJoinactivityrecordMapper.selectCount(entityWrapper);//当前参与人数
        LOGGER.info("获取的count================：" + count);
        LOGGER.info("获取的NUM================：" + gcActivity.getNum());
        if(count > gcActivity.getNum()){
            return false;
        }
        int row = gcJoinactivityrecordMapper.insert(gcJoin);
        if(row > 0){
            //messageProducer.send(JSON.toJSONString(gcJoin));
            //活动需要人数
            double par = gcActivity.getPar();//活动需要的cnt能量
            //插入用户参与记录/更改用户cnt值/
            boolean flag = addGcJoinactivityrecordAndUpAccount(gcJoin.getAid(), gcJoin, par);
            if (flag) {
                boolean part = participateActivity(contract, gcJoin.getAid(), gcJoin.getUid(), gcJoin.getId());
                if(part){
                    //最后一个用户参与活动
                    if(count == gcActivity.getNum()) {
                        //初始化合约加载合约
                        long startTime = System.currentTimeMillis();
                        boolean changeStage = initAndloadContractAndChangeStage(contract, gcJoin.getAid());
                        long times = System.currentTimeMillis() - startTime;
                        LOGGER.info("============initAndloadContractAndChangeStage加载时间:" + times);
                        LOGGER.info("============initAndloadContractAndChangeStage加载时间:" + changeStage);
                        if (changeStage) {
                            //参与活动添加10飞羽
                            HcPointsRecord hcp = new HcPointsRecord();
                            hcp.setUid(gcJoin.getUid());
                            hcp.setPointsId(PointEnum.PART_ACITIVITY.pointId);
                            hcp.setPointsNum(PointEnum.PART_ACITIVITY.pointNum);
                            boolean result = hcAccountApi.addHcPointsRecord(hcp);
                            LOGGER.info("addHcPointsRecord返回结果：" + result);
                            if(result){
                                return result;
                            }else
                                throw new RuntimeException("参与活动添加10飞羽失败");
                        }else
                            throw new RuntimeException("最后一个用户加载以太坊上的竞锤合约失败");
                    }
                }else
                   throw new RuntimeException("参加活动加载合约失败");
            }else
                throw new RuntimeException("用户参与记录更改用户CNT失败");
            return true;
        }else
            throw new RuntimeException("插入参与记录失败");
    }

}