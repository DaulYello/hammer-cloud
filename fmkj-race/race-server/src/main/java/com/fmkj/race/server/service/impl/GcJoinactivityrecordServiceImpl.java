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
import com.fmkj.race.server.hammer.contracts.PuzzleHammer.puzzle.Helper;
import com.fmkj.race.server.hammer.contracts.PuzzleHammer.puzzle.Person;
import com.fmkj.race.server.hammer.contracts.PuzzleHammer.puzzle.State;
import com.fmkj.race.server.service.GcActivityService;
import com.fmkj.race.server.service.GcJoinactivityrecordService;
import com.fmkj.user.dao.domain.FmRecyleLog;
import com.fmkj.user.dao.domain.HcAccount;
import com.fmkj.user.dao.domain.HcPointsRecord;
import com.fmkj.user.dao.mapper.HcAccountMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
     * @param joins
     * @return boolean
    */
    public boolean updateCntAndPiont(int uid, Double par) {
        //更改用户CNT
        HcAccount hc = new HcAccount();
        hc.setId(uid);
        hc.setCnt(par);
        boolean flag = hcAccountApi.updateUserP(hc);
        if (flag){
            //参与活动添加10飞羽
            HcPointsRecord hcp = new HcPointsRecord();
            hcp.setUid(uid);
            hcp.setTime(new Date());
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
        if(!part){
            LOGGER.info("【web3j-Failed】参与活动加载失败");
            return false;
        }
        return true;
    }

    /**
     * @author yangshengbin
     * @Description：最后一个用户参与活动
     * @date 2018/9/6 0006 14:12
     * @param aid
     * @param contract
     * @param gcActivity
     * @return boolean
    */
    @Override
    public int lastChangeStage(String contract,int uid,String nickname) {
        //加载合约
        Helper helper = new Helper();
        boolean init = helper.init();
        if (!init) {
            LOGGER.info("【web3j-Failed】合约初始化失败!");
            return -1;
        }
        //load contract
        boolean ifLoad = helper.loadContract(contract);
        if(!ifLoad) {
            LOGGER.info("【web3j-Failed】合约地址加载失败");
            return -1;
        }

        boolean part = helper.particiPuzzle(new Person(nickname, BigInteger.valueOf(uid)));// 实例出合约用户，参与活动
        if(!part){
            LOGGER.info("【web3j-Failed】上链失载败");
            return -1;
        }

        //通知活动关闭
        boolean stage = helper.changeStage(State.closed);
        LOGGER.info("【web3j-Log】合约更改状态为close返回结果:" + stage);
        if(!stage) {
            return -1;
        }

        boolean result= helper.changeStage(State.notice);
        LOGGER.info("【web3j-Log】合约更改状态为notice返回结果:" + result);
        if(!result){
            return -1;
        }

        boolean winnerResult= helper.puzzleWinner();
        LOGGER.info("【web3j-Log】竞锤是否产生优胜者结果:" + winnerResult);
        if(!winnerResult){
            return -1;
        }

        boolean isEnd = helper.changeStage(State.end);
        LOGGER.info("【web3j-Log】合约更改状态为end返回结果:" + isEnd);
        if(!isEnd){
            return -1;
        }
        int hammerId = helper.getWinner().getID().intValue();
        LOGGER.info("【web3j-Log】竞锤获取优胜者用户:" + hammerId);
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
    public boolean onChain(JoinActivityDto joinActivityDto) {
        //查询活动信息获取合约地址参与合约
        String contract = joinActivityDto.getContract();
        LOGGER.info("活动获取到的合约地址：" + contract);
        if (StringUtils.isEmpty(contract)) {
            return false;
        }
        LOGGER.info("参与活动总人数:" + joinActivityDto.getNum());
        //活动需要人数
        double par = joinActivityDto.getPar();//活动需要的cnt能量
        //插入用户参与记录/更改用户cnt值/
        boolean flag = updateCntAndPiont(joinActivityDto.getUid(), par);
        LOGGER.info("更改参加用户CNT值与添加飞羽返回结果:" + flag);
        if (flag) {
            //最后一个用户参与活动
            if (joinActivityDto.getIslast() == 1) {
                //初始化合约加载合约
                int winId = lastChangeStage(contract,joinActivityDto.getUid(), joinActivityDto.getNickname());
                if(winId == -1){
                    throw new RuntimeException("最后一个用户参与活动上链更新合约状态失败");
                }
                boolean saveNotice = saveNoticeInfo(winId, joinActivityDto);
                if(!saveNotice){
                    throw new RuntimeException("插入通知表，保存优胜者的记录执行失败");
                }
                GcJoinactivityrecord gcJoinactivityrecord = new GcJoinactivityrecord();
                gcJoinactivityrecord.setAid(joinActivityDto.getAid());
                EntityWrapper<GcJoinactivityrecord> wrapper = new EntityWrapper<>(gcJoinactivityrecord);
                List<GcJoinactivityrecord> joinactivityrecords= gcJoinactivityrecordMapper.selectList(wrapper);
                List<Integer> uids = new ArrayList<>();
                for(GcJoinactivityrecord joinactivityrecord : joinactivityrecords){
                    if(joinactivityrecord.getUid() != winId){
                        uids.add(joinactivityrecord.getUid());
                    }
                }
                boolean rusult = hcAccountApi.grantCredits(par,uids);
                LOGGER.info("竞锤成功后给用户发R积分:" + rusult);
                return true;
            }else {
                boolean part = participateActivity(contract,joinActivityDto.getUid(), joinActivityDto.getNickname());
                if(!part){
                    throw new RuntimeException("参加活动上链失败");
                }else{
                    GcJoinactivityrecord gcJoinactivityrecord = gcJoinactivityrecordMapper.selectById(joinActivityDto.getId());
                    gcJoinactivityrecord.setIschain(1);
                    int updateChain = gcJoinactivityrecordMapper.updateById(gcJoinactivityrecord);
                    if(updateChain > 0){
                        return true;
                    }else{
                        throw new RuntimeException("更改上链状态失败！");
                    }
                }
            }
        }else {
            throw new RuntimeException("更改参加用户CNT值与添加飞羽失败");
        }
    }

    private boolean saveNoticeInfo(int winId, JoinActivityDto joinActivityDto) {
        LOGGER.info("保存优胜者的记录输入参数winId={},typeId={}", winId, joinActivityDto.getTypeid());
        GcActivitytype gcActivitytype = gcActivitytypeMapper.selectById(joinActivityDto.getTypeid());
        GcNotice notice = new GcNotice();
        notice.setUid(winId);
        notice.setFlag(1);
        StringBuilder sb = new StringBuilder();
        String head = new String("恭喜您");
        String message = new String(",请在48小时内及时联系客服QQ：2500702820办理资产转移。");
        sb.append(head);
        sb.append(gcActivitytype.getType());
        sb.append("—" + joinActivityDto.getPname());
        sb.append(message);
        String messageStr =head+gcActivitytype.getType()+"—" + joinActivityDto.getPname() + message;
        GcMessage gcMessage = new GcMessage();
        gcMessage.setMessage(messageStr);
        gcMessage.setType(0);
        gcMessage.setTime(new Date());
        int mid = gcMessageMapper.insert(gcMessage);
        LOGGER.info("发给用户中锤的消息"+mid);
        GcMessage gcMes = new GcMessage();
        gcMes.setMessage("恭喜"+joinActivityDto.getNickname()+"获得活动商品 — "+joinActivityDto.getPname());
        gcMes.setType(2);
        gcMes.setTime(new Date());
        int rows= gcMessageMapper.insert(gcMes);
        LOGGER.info("插入用户中锤的公告消息"+rows);
        if(mid > 0 && rows>0){
            notice.setMid(gcMessage.getId());
            notice.setUid(winId);
            int row = gcNoticeMapper.insert(notice);
            LOGGER.info("发给用户中锤的消息"+row);
            GcNotice gcNotice = new GcNotice();
            gcNotice.setFlag(1);
            gcNotice.setMid(gcMes.getId());
            int row2 = gcNoticeMapper.insert(gcNotice);
            LOGGER.info("插入用户中锤的公告消息"+row2);
            if(row > 0 && row2>0){
                GcActivity gcActivity = gcActivityMapper.selectById(joinActivityDto.getAid());
                gcActivity.setStatus(3);
                gcActivity.setGetid(winId);
                gcActivity.setEndtime(new Date());
                int updateRow = gcActivityMapper.updateById(gcActivity);
                if(updateRow > 0){
                    GcJoinactivityrecord gcJoinactivityrecord = gcJoinactivityrecordMapper.selectById(joinActivityDto.getId());
                    gcJoinactivityrecord.setIschain(1);
                    int updateChain = gcJoinactivityrecordMapper.updateById(gcJoinactivityrecord);
                    if(updateChain > 0){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public List<JoinActivityDto> queryJoinActivityList() {
        return gcJoinactivityrecordMapper.queryJoinActivityList();
    }
}