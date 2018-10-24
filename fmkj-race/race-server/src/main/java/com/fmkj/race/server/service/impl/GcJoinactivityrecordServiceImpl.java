package com.fmkj.race.server.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.fmkj.common.annotation.BaseService;
import com.fmkj.common.base.BaseServiceImpl;
import com.fmkj.common.comenum.PointEnum;
import com.fmkj.common.util.StringUtils;
import com.fmkj.race.dao.domain.*;
import com.fmkj.race.dao.dto.JoinActivityDto;
import com.fmkj.race.dao.mapper.*;
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
import java.util.Date;
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
    private GcActivitytypeMapper gcActivitytypeMapper;

    @Autowired
    private HcAccountApi hcAccountApi;

    @Autowired
    private GcNoticeMapper gcNoticeMapper;

    @Autowired
    private GcMessageMapper gcMessageMapper;

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
        Helper helper = new Helper();
        boolean init = helper.init();
        if (!init) {
            LOGGER.info("【web3j-Failed】合约初始化失败!");
            return false;
        }
        boolean isLoad = helper.loadContract(contract);
        if (!isLoad) {
            LOGGER.info("【web3j-Failed】合约地址加载失败");
            return false;
        }
        /*boolean change = helper.changeStage(State.participate);
        LOGGER.info("【web3j-Failed】改变状态失败" + change);*/
       /* if (!change){
            LOGGER.info("【web3j-Failed】改变状态失败");
            return false;
        }*/
        boolean part = helper.particiPuzzle(new Person(nickname, BigInteger.valueOf(uid)));// 实例出合约用户，参与活动
        LOGGER.info("【web3j-Failed】改变状态失败part" + part);

        /*if(!part){
            LOGGER.info("【web3j】参与活动失败");
            return false;
        }*/
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
    public int lastChangeStage(String contract) {
        //加载合约
        Helper helper = new Helper();
        boolean init = helper.init();
        if (!init) {
            LOGGER.info("【web3j-Failed】合约初始化失败!");
            return -1;
        }
        //load contract
        boolean ifLoad = helper.loadContract(contract);
        if(!ifLoad)
        {
            LOGGER.info("【contract-Failed】合约地址加载失败");
            return -1;
        }

        //通知活动关闭

        boolean stage = helper.changeStage(State.closed);

        LOGGER.info("【web3j-Failed】合约关闭失败stage" + stage);

        if(!stage) {
            LOGGER.error("【web3j-Failed】合约关闭失败");
            return -1;
        }

        boolean result= helper.changeStage(State.notice);
        LOGGER.info("【web3j-Failed】resultresultresultresult" + result);
        if(!result){
            LOGGER.info("【web3j-Failed】活动进入竟锤状态失败！");
            return -1;
        }

        boolean winnerResult= helper.puzzleWinner();
        if(!winnerResult){
            LOGGER.info("【web3j-Failed】获取优胜者失败！");
            return -1;
        }

        boolean isEnd = helper.changeStage(State.end);
        LOGGER.info("【web3j-Failed】获取优胜者失败isEnd！" + isEnd);

        if(!isEnd){
            LOGGER.info("【web3j-Failed】改变合约状态END失败！");
            return -1;
        }
        int hammerId = helper.getWinner().getID().intValue();
        return hammerId;
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
    public boolean  addGcJoinactivityRecord(GcJoinactivityrecord gcJoin, GcActivity gcActivity, String nickname) {
        //查询活动信息获取合约地址参与合约
        String contract = gcActivity.getContract();
        LOGGER.info("活动获取到的合约地址：" + contract);
        if (StringUtils.isEmpty(contract)) {
            return false;
        }
        LOGGER.info("总人数============lastChangeStage:" + gcActivity.getNum());
        //活动需要人数
        double par = gcActivity.getPar();//活动需要的cnt能量
        boolean part = participateActivity(contract,gcJoin.getUid(), nickname);
        if (part) {
            //将用户上链记录改为1
            int insertRow = gcJoinactivityrecordMapper.insert(gcJoin);
            if(insertRow > 0){
                //获取当前参与人数
                GcJoinactivityrecord where = new GcJoinactivityrecord();
                where.setAid(gcJoin.getAid());
                EntityWrapper<GcJoinactivityrecord> entityWrapper = new EntityWrapper<>(where);
                int count = gcJoinactivityrecordMapper.selectCount(entityWrapper);//当前参与人数
                LOGGER.info("当前参与的人数============lastChangeStage:" + count);
                if (count > gcActivity.getNum()) {
                    return false;
                }
                //插入用户参与记录/更改用户cnt值/
                boolean flag = addGcJoinactivityrecordAndUpAccount(gcJoin.getAid(), gcJoin, par);
                LOGGER.info("插入用户参与记录/更改用户cnt值/============lastChangeStage:" + flag);
                if (flag) {

                    //最后一个用户参与活动
                    if (count == gcActivity.getNum()) {
                        //初始化合约加载合约
                        long startTime = System.currentTimeMillis();
                        int winId = lastChangeStage(contract);
                        long times = System.currentTimeMillis() - startTime;
                        LOGGER.info("============lastChangeStage:" + times);
                        LOGGER.info("【web3j-success】获取到活动竟锤优胜者:" + winId);
                        if (winId != -1) {
                            boolean saveNotice = saveNoticeInfo(winId, gcActivity);
                            if (saveNotice) {
                                return true;
                            } else
                                throw new RuntimeException("最后一个参与用户节点更新活动参与状态失败");
                        } else
                            throw new RuntimeException("获取到优胜者失败");
                    }
                    }
                    return true;
                } else
                    throw new RuntimeException("插入记录表失败");
            }else{
              return false;
        }
    }

    private boolean saveNoticeInfo(int winId, GcActivity gcActivity) {
        LOGGER.info("保存优胜者的记录输入参数winId={},typeId={}", winId, gcActivity.getTypeid());
        GcActivitytype gcActivitytype = gcActivitytypeMapper.selectById(gcActivity.getTypeid());
        GcNotice notice = new GcNotice();
        notice.setUid(winId);
        notice.setFlag(1);
        StringBuilder sb = new StringBuilder();
        String head = new String("恭喜您中锤");
        String message = new String(",请在48小时内及时联系客服QQ：2500702820办理资产转移。");
        sb.append(head);
        sb.append(gcActivitytype.getType());
        sb.append("—" + gcActivity.getPname());
        sb.append(message);
        String messageStr =head+gcActivitytype.getType()+"—" + gcActivity.getPname() + message;
        //notice.setMessage(sb.toString());
        GcMessage gcMessage = new GcMessage();
        gcMessage.setMessage(messageStr);
        gcMessage.setType(2);
        gcMessage.setTime(new Date());
        int mid = gcMessageMapper.insert(gcMessage);
        if(mid>0){
            notice.setMid(gcMessage.getId());
            notice.setUid(winId);
            notice.setFlag(1);
            int row = gcNoticeMapper.insert(notice);
            if(row > 0){
                gcActivity.setStatus(3);
                gcActivity.setGetid(winId);
                int updateRow = gcActivityMapper.updateById(gcActivity);
                if(updateRow > 0){
                    return true;
                }
            }
        }
        return false;
    }
}