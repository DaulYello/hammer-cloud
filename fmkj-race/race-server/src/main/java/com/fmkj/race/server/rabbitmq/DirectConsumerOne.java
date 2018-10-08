package com.fmkj.race.server.rabbitmq;
 

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.fmkj.common.util.StringUtils;
import com.fmkj.race.dao.domain.GcActivity;
import com.fmkj.race.dao.domain.GcJoinactivityrecord;
import com.fmkj.race.server.service.GcActivityService;
import com.fmkj.race.server.service.GcJoinactivityrecordService;
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
public class DirectConsumerOne  {   //implements MessageListener{

//, containerFactory = "rabbitListenerContainerFactory"
	@Autowired
	private GcActivityService gcActivityService;

	@Autowired
	private GcJoinactivityrecordService gcJoinactivityrecordService;


    @RabbitHandler
	public void onMessage(String message) {


        GcJoinactivityrecord joins = JSON.parseObject(message, GcJoinactivityrecord.class);


		
		Integer aid = joins.getAid();// 获取活动id
		Integer uid = joins.getUid();// 获取用户id

		GcActivity gcActivity = new GcActivity();
		//是否存在该活动或活动已经结束
		GcActivity gcActivity1 = gcActivityService.selectById(joins.getAid());


		//获取当前参与人数
		GcJoinactivityrecord gcJoinactivityrecord = new GcJoinactivityrecord();
		gcJoinactivityrecord.setAid(aid);
		EntityWrapper<GcJoinactivityrecord> entityWrapper = new EntityWrapper<GcJoinactivityrecord>();
		entityWrapper.setEntity(gcJoinactivityrecord);


		//活动需要人数
		int num = gcActivity1.getNum();

		double par = gcActivity1.getPar();//活动需要的P能量

		//插入用户参与记录/更改用户p能量值/
		boolean flag1=false;
		int count = -1;
		synchronized (this.getClass()) {
			flag1 = gcJoinactivityrecordService.addGcJoinactivityrecordAndUpAccount(aid, joins,par);
			count =  gcJoinactivityrecordService.selectCount(entityWrapper);//当前参与人数
			if (count>=num){
				System.err.println("活动人数已满");
				return ;
			}
		}
		if (flag1==false) {
			return ;
		}

		//查询活动信息获取合约地址参与合约
		String contract = gcJoinactivityrecordService.queryGcActivityByContract(aid);
		if (StringUtils.isNull(contract)) {
			System.err.println("用户合约地址获取失败");
			return ;
		}

		//参加活动加载合约
		Integer gid = joins.getId();
    	boolean b = gcJoinactivityrecordService.participateActivity(contract, aid, uid,gid);
    	if(!b) {
			System.err.println("用户上链失败");
			return ;
    	}

		//最后一个用户参与活动
		if(count==num) {
        	//初始化合约加载合约
        	boolean flag = gcJoinactivityrecordService.initAndloadContractAndChangeStage(contract,aid);
        	if (flag==false) {
				System.err.println("初始化合约或加载合约失败");
        		return;
			}
		}
		return ;
	}

}