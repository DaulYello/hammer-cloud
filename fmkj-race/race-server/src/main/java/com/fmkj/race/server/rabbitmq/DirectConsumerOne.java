package com.fmkj.race.server.rabbitmq;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.fmkj.common.util.StringUtils;
import com.fmkj.race.dao.domain.GcActivity;
import com.fmkj.race.dao.domain.GcJoinactivityrecord;
import com.fmkj.race.server.service.GcActivityService;
import com.fmkj.race.server.service.GcJoinactivityrecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author 杨胜彬
 * @comments 第一个消费者
 * @time 2018年8月7日 下午7:55:30
 * @developers 费马科技
 */
@Service
@Transactional
@Component
@RabbitListener(queues = "workqueue")
public class DirectConsumerOne  {

	private static final Logger LOGGER = LoggerFactory.getLogger(DirectConsumerOne.class);

	@Autowired
	private GcActivityService gcActivityService;

	@Autowired
	private GcJoinactivityrecordService gcJoinactivityrecordService;


    @RabbitHandler
	public void onMessage(String message) {
        GcJoinactivityrecord joins = JSON.parseObject(message, GcJoinactivityrecord.class);
		Integer aid = joins.getAid();// 获取活动id
		Integer uid = joins.getUid();// 获取用户id
		//是否存在该活动或活动已经结束
		GcActivity gcActivity = gcActivityService.selectById(joins.getAid());
		//获取当前参与人数
		GcJoinactivityrecord gcJoinactivityrecord = new GcJoinactivityrecord();
		gcJoinactivityrecord.setAid(aid);
		EntityWrapper<GcJoinactivityrecord> entityWrapper = new EntityWrapper<GcJoinactivityrecord>();
		entityWrapper.setEntity(gcJoinactivityrecord);
		//活动需要人数
		int num = gcActivity.getNum();
		double par = gcActivity.getPar();//活动需要的cnt能量
		//插入用户参与记录/更改用户cnt值/
		boolean flag=false;
		int count = -1;
		synchronized (this.getClass()) {
			flag = gcJoinactivityrecordService.addGcJoinactivityrecordAndUpAccount(aid, joins,par);
			count = gcJoinactivityrecordService.selectCount(entityWrapper);//当前参与人数
			if (count>=num){
				LOGGER.info("MQ活动人数已满");
				return ;
			}
		}
		if (!flag) {
			return ;
		}

		//查询活动信息获取合约地址参与合约
		String contract = gcJoinactivityrecordService.queryGcActivityByContract(aid);
		if (StringUtils.isNull(contract)) {
			LOGGER.info("MQ用户合约地址获取失败");
			return ;
		}

		//参加活动加载合约
		Integer gid = joins.getId();
    	boolean b = gcJoinactivityrecordService.participateActivity(contract, aid, uid,gid);
    	if(!b) {
			LOGGER.info("MQ用户上链失败");
			return ;
    	}

		//最后一个用户参与活动
		if(count==num) {
        	//初始化合约加载合约
        	boolean result = gcJoinactivityrecordService.initAndloadContractAndChangeStage(contract,aid);
        	if (!result) {
				LOGGER.info("MQ初始化合约或加载合约失败");
        		return;
			}
		}
		return ;
	}

}