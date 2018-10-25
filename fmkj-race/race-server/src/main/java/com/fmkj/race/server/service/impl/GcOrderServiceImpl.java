package com.fmkj.race.server.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.fmkj.common.annotation.BaseService;
import com.fmkj.common.base.BaseServiceImpl;
import com.fmkj.race.dao.domain.*;
import com.fmkj.race.dao.mapper.*;
import com.fmkj.race.server.api.HcAccountApi;
import com.fmkj.race.server.service.GcOrderService;
import com.fmkj.race.server.util.CalendarTime;
import com.fmkj.race.server.util.PoundageUtil;
import com.fmkj.race.server.util.SpringContextHandler;
import com.fmkj.user.dao.domain.HcAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;


/**
* @Description: GcOrder Service实现
* @Author: yangshengbin
* @CreateDate: 2018/8/31.
* @Version: 1.0
**/
@Service
@Transactional
@BaseService
@ComponentScan(basePackages = "com.fmkj.race.dao.domain")
public class GcOrderServiceImpl extends BaseServiceImpl<GcOrderMapper, GcOrder> implements GcOrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GcOrderServiceImpl.class);



    GcOrderMapper gcOrderMapper = SpringContextHandler.getBean(GcOrderMapper.class);



    GcActivityMapper gcActivityMapper = SpringContextHandler.getBean(GcActivityMapper.class);


    @Autowired
    GcActivitytypeMapper gcActivitytypeMapper;  //活动类型

    @Autowired
    GcMessageMapper gcMessageMapper;//信息mapper

    @Autowired
    GcNoticeMapper gcNoticeMapper;//通知mapper


    @Autowired
    private HcAccountApi hcAccountApi;

    @Autowired
    private FmAssetsPoundageMapper gcAssetsMapper;


    /**
     * @author yangshengbin
     * @Description：确认发货
     * @date 2018/8/31 0031 15:41
     * @param
     * @return boolean
    */
    @Override
    public boolean sendGoodsByExample(GcOrder gcOrder) {
        GcOrder one = gcOrderMapper.selectOne(gcOrder);
        if(one!=null) {
            return false;
        }
        Integer row = 0;
        try {
            gcOrderMapper.insert(gcOrder);
            GcActivity ga = new GcActivity();
            ga.setDelivergoodstatus(1);
            EntityWrapper<GcActivity> wra = new EntityWrapper<GcActivity>();
            GcActivity gcActivity = new GcActivity();
            gcActivity.setId(gcOrder.getAid());
            wra.setEntity(gcActivity);
            row = gcActivityMapper.update(ga,wra);
        } catch (Exception e) {
            throw new RuntimeException("修改活动的物流状态异常：" + e.getMessage());
        }
        if(row>0) {
            return true;
        }
        return false;
    }



    /**
     * @author yangshengbin
     * @Description：确认收货
     * @date 2018/8/31 0031 17:20
     * @param order
     * @return boolean
    */
    public boolean collectGoods(GcOrder order) {
        Integer aid = order.getAid();
        GcActivity ga = new GcActivity();
        ga.setId(aid);
        ga.setCollectgoodstatus(1);
        Integer row = gcActivityMapper.updateById(ga);
        if(row>0) {
            GcActivity one = gcActivityMapper.selectOne(ga);//查询活动
            Integer startid = one.getStartid();//获得发起者
            Double price = one.getPrice();//获取产品价格
            Double premium = one.getPremium();//获取产品溢价率

            /************获取手续费***********/
            Integer pnumber = one.getPnumber();//获取产品数量
            Double poundage = PoundageUtil.getpoundage(price, premium,pnumber);
            /************获取手续费***********/

            //发起者获得的奖励
            Double starterCnt = (1+premium)*price*pnumber - poundage;

            /*******根据活动类型id获取活动类型名称*******/
            Integer typeid = one.getTypeid();
            GcActivitytype gaty = new GcActivitytype();
            gaty.setId(typeid);
            GcActivitytype gat = gcActivitytypeMapper.selectOne(gaty);
            String type = gat.getType();
            /*******根据活动类型id获取活动类型名称*******/

            LOGGER.debug("向gc_assets资产表中插入资产记录");
            FmAssetsPoundage assets = new FmAssetsPoundage();
            assets.setAid(one.getId());
            assets.setTotalAssets((1+premium)*price*pnumber);
            assets.setPoundage(poundage);
            assets.setCreateDate(new Date());
            boolean addAssets=gcAssetsMapper.insert(assets)>0 ? true:false;
            if(!addAssets){
                LOGGER.debug("插入资产记录失败！");
                return false;
            }
            //调用户接口发放P能量
            HcAccount hc = new HcAccount();
            hc.setId(startid);
            hc.setCnt(starterCnt);
            boolean cntReturn = hcAccountApi.grantUserP(hc);
            String message = null;
            if(cntReturn){
                message = "您已发起的"+type+"溢价活动已经完成，该活动扣除手续费"+poundage+"，您已获得资产"+starterCnt+"，请注意查收。";
                boolean addReturnByStartID = postMessage(message,startid);
                LOGGER.debug("给发起活动的用户发送信息addReturnByStartID="+addReturnByStartID);
                boolean addReturnByGetID =false;
                if(addReturnByStartID){
                    message = "您收到了"+type+"—"+one.getPname()+"， 锤多宝锤出不一样的美好！！！";
                    addReturnByGetID=postMessage(message,one.getGetid());
                    LOGGER.debug("发给重锤的用户信息是否成功："+addReturnByStartID);
                }
                return addReturnByGetID;
            }else{
                LOGGER.debug("确认收货后，将资产对应的CNT给发起活动的用户时报错！");
                return false;
            }
        }
        return false;
    }

    public boolean postMessage(String message,int uid){

        CalendarTime clt = new CalendarTime();
        Timestamp btime = clt.thisDate();//获取当前时间
        //插入信息
        GcMessage gcMessage = new GcMessage();
        gcMessage.setTime(btime);
        gcMessage.setMessage(message);
        gcMessage.setType(0);
        boolean addReturn = gcMessageMapper.insert(gcMessage)>0?true:false;
        if(addReturn){
            LOGGER.debug("插入信息addReturn成功！");
            //插入通知表
            GcMessage gcMessage1 = gcMessageMapper.selectOne(gcMessage);
            GcNotice gn = new GcNotice();
            gn.setFlag(1);
            gn.setUid(uid);
            gn.setMid(gcMessage1.getId());
            addReturn = gcNoticeMapper.insert(gn)>0?true:false;
        }
        return addReturn;
    }

}