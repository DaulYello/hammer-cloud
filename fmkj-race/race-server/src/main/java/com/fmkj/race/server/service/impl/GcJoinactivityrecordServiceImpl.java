package com.fmkj.race.server.service.impl;

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
import com.fmkj.race.server.hammer.contracts.PuzzleHammer.PuzzleHammer;
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

    @Autowired
    private Helper helper;

    /**
     * @author yangshengbin
     * @Description：插入用户参与记录/更改用户p能量值/
     * @date 2018/9/6 0006 10:02
     * @param aid
     * @param joins
     * @param par
     * @return boolean
    */
    public boolean addGcJoinactivityrecordAndUpAccount(Integer aid, GcJoinactivityrecord joins, double par) {
        //更改用户CNT
        HcAccount hc = new HcAccount();
        hc.setId(joins.getUid());
        hc.setCnt(par);
        boolean flag = hcAccountApi.updateUserP(hc);
        LOGGER.info("更改用户CNT返回结果：" + flag);
        if (flag){
            //参与活动添加10飞羽
            HcPointsRecord hcp = new HcPointsRecord();
            hcp.setUid(joins.getUid());
            hcp.setPointsId(PointEnum.PART_ACITIVITY.pointId);
            hcp.setPointsNum(PointEnum.PART_ACITIVITY.pointNum);
            boolean result = hcAccountApi.addHcPointsRecord(hcp);
            if(result){
                return true;
            }
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
     * @param nickname
     * @return boolean
    */
    public boolean participateActivity(String contract,Integer uid, String nickname) {
        PuzzleHammer puzzleHammer = helper.loadContract(contract);
        if (StringUtils.isNull(puzzleHammer)) {
            LOGGER.info("【web3j】合约地址加载失败");
            return false;
        }
        LOGGER.info("【web3j】状态：" + helper.getState(puzzleHammer));

        LOGGER.info("【web3j】状态State.participate：" + State.participate);

        boolean change = helper.changeStage(State.participate, puzzleHammer);
        if (!change){
            LOGGER.info("【web3j】改变状态失败");
            return false;
        }
        boolean part = helper.particiPuzzle(new Person(nickname, BigInteger.valueOf(uid)), puzzleHammer);// 实例出合约用户，参与活动
        if(!part){
            LOGGER.info("【web3j】参与活动失败");
            return false;
        }
        return true;
    }





    /**
     * @author yangshengbin
     * @Description：最后一个用户参与活动
     * @date 2018/9/6 0006 14:12
     * @param contract
     * @param aid
     * @return boolean
    */
    public boolean initAndloadContractAndChangeStage(String contract) {
        //加载合约
        PuzzleHammer puzzleHammer = helper.loadContract(contract);
        if (StringUtils.isNull(puzzleHammer)) {
            LOGGER.error("合约地址加载失败");
            return false;
        }
        //通知活动关闭
        boolean stage = helper.changeStage(State.closed, puzzleHammer);
        if(!stage) {
            LOGGER.error("合约关闭失败");
            return false;
        }
        return true;
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
    public boolean addGcJoinactivityRecord(GcJoinactivityrecord gcJoin, GcActivity gcActivity, String nickname) {
        //查询活动信息获取合约地址参与合约
        String contract = gcActivity.getContract();
        LOGGER.info("活动获取到的合约地址：" + contract);
        if (StringUtils.isEmpty(contract)) {
            return false;
        }
        //获取当前参与人数
        GcJoinactivityrecord gcJoinactivityrecord = new GcJoinactivityrecord();
        gcJoinactivityrecord.setAid(gcJoin.getAid());
        EntityWrapper<GcJoinactivityrecord> entityWrapper = new EntityWrapper<>(gcJoinactivityrecord);
        int count = gcJoinactivityrecordMapper.selectCount(entityWrapper);//当前参与人数
        if (count > gcActivity.getNum()) {
            return false;
        }
        int row = gcJoinactivityrecordMapper.insert(gcJoin);
        if (row > 0) {
            //活动需要人数
            double par = gcActivity.getPar();//活动需要的cnt能量
            boolean part = participateActivity(contract,gcJoin.getUid(), nickname);
            if (part) {
                //将用户上链记录改为1
                gcJoinactivityrecord.setIschain(1);
                int updateRow = gcJoinactivityrecordMapper.updateById(gcJoinactivityrecord);
                if(updateRow <= 0){
                    gcJoinactivityrecord.setIschain(2);
                    gcJoinactivityrecordMapper.updateById(gcJoinactivityrecord);
                    return false;
                }
                //插入用户参与记录/更改用户cnt值/
                boolean flag = addGcJoinactivityrecordAndUpAccount(gcJoin.getAid(), gcJoin, par);
                if (flag) {
                    //最后一个用户参与活动
                    if (count == gcActivity.getNum()) {
                        //初始化合约加载合约
                        long startTime = System.currentTimeMillis();
                        boolean changeStage = initAndloadContractAndChangeStage(contract);
                        long times = System.currentTimeMillis() - startTime;
                        LOGGER.info("============initAndloadContractAndChangeStage加载时间:" + times);
                        LOGGER.info("============initAndloadContractAndChangeStage加载时间:" + changeStage);
                        if (changeStage) {
                            //更新活动状态
                            GcActivity gat = new GcActivity();
                            gat.setId(gcJoin.getAid());
                            gat.setStatus(3);
                            int update = gcActivityMapper.updateById(gat);
                            if (update > 0) {
                                return true;
                            }else
                                throw new RuntimeException("更新活动参与状态失败");
                        } else
                            throw new RuntimeException("最后一个用户加载以太坊上的竞锤合约失败");
                    } else
                        throw new RuntimeException("参加活动加载合约失败");
                } else
                    throw new RuntimeException("用户参与记录更改用户CNT失败");
            } else
                throw new RuntimeException("参与活动加载合约失败");
        }
        return false;
    }
}