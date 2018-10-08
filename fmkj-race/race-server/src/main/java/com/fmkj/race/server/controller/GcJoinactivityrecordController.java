package com.fmkj.race.server.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.fmkj.common.base.BaseApiService;
import com.fmkj.common.base.BaseController;
import com.fmkj.common.base.BaseResult;
import com.fmkj.common.base.BaseResultEnum;
import com.fmkj.common.constant.LogConstant;
import com.fmkj.common.util.StringUtils;
import com.fmkj.race.dao.domain.GcActivity;
import com.fmkj.race.dao.domain.GcJoinactivityrecord;
import com.fmkj.race.dao.dto.JoinActivityDto;
import com.fmkj.race.dao.queryVo.JoinActivityPage;
import com.fmkj.race.server.annotation.RaceLog;
import com.fmkj.race.server.service.GcActivityService;
import com.fmkj.race.server.service.GcJoinactivityrecordService;
import com.fmkj.race.server.rabbitmq.MessageProducer;
import com.fmkj.race.server.util.CalendarTime;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

/**
 * @ Author     ：yangshengbin
 * @ Date       ：09:12 2018/9/5 0005
 * @ Description：用户参与活动控制层
 */
@RestController
@RequestMapping("/gcjoinactivityrecord")
@DependsOn("springContextHandler")
@Api(tags ={ "用户参加活动服务"},description = "用户参加活动接口-网关路径/api-race")
public class GcJoinactivityrecordController  extends BaseController<GcJoinactivityrecord,GcJoinactivityrecordService> implements BaseApiService<GcJoinactivityrecord> {

    @Autowired
    private GcActivityService gcActivityService;

    @Autowired
    private MessageProducer messageProducer;

    @Autowired
    private GcJoinactivityrecordService gcJoinactivityrecordService;

    /**
     *  用户参加活动
     */
    @ApiOperation(value="用户参加活动，参数：aid,uid ", notes="用户参加活动")
    @RaceLog(module= LogConstant.Gc_Activity, actionDesc = "用户参加活动")
    @PostMapping("/ActivityRabbitMQ")
    public BaseResult ActivityRabbitMQ(@RequestBody GcJoinactivityrecord gcJoinactivityrecord){

        //是否存在该活动或活动已经结束
        GcActivity gcActivity = gcActivityService.selectById(gcJoinactivityrecord.getAid());

        if (StringUtils.isNull(gcActivity)) {
            System.err.println("没有该活动");
            return new BaseResult(BaseResultEnum.ERROR, "没有该活动");
        }
        if (gcActivity.getStatus().equals(3)||gcActivity.getStatus().equals(4)||gcActivity.getStatus().equals(5)) {
            System.err.println("活动已结束");
            return new BaseResult(BaseResultEnum.ERROR, "活动已结束");
        }

        /*********插入参与记录**********/
        CalendarTime clt = new CalendarTime();
        Timestamp btime = clt.thisDate();//获取当前时间
        gcJoinactivityrecord.setTime(btime);
        gcJoinactivityrecord.setIschain(0);
        boolean flag = false;
        try {
            flag = gcJoinactivityrecordService.insert(gcJoinactivityrecord);
        } catch (Exception e) {
            throw new RuntimeException("插入参与记录异常,"+"活动aid:"+gcJoinactivityrecord.getAid()+",用户:"+gcJoinactivityrecord.getUid()+"" + e.getMessage());
        }
        if (!flag){
            return new BaseResult(BaseResultEnum.ERROR, false);
        }
        /*********插入参与记录**********/


        /********获取用户参与记录的id*******/
        EntityWrapper wrapper = new EntityWrapper();
        wrapper.setEntity(gcJoinactivityrecord);
        GcJoinactivityrecord gcJoin = gcJoinactivityrecordService.selectOne(wrapper);
        /********获取用户参与记录的id*******/

        if (StringUtils.isNull(gcJoin.getId())){
            return new BaseResult(BaseResultEnum.ERROR, false);
        }

        //将活动记录id放入队列中
        gcJoinactivityrecord.setId(gcJoin.getId());

        messageProducer.send(JSON.toJSONString(gcJoinactivityrecord));//生产消息
        return new BaseResult(BaseResultEnum.SUCCESS, true);

    }


    @ApiOperation(value="活动参与记录,参数：aid,uid,pageSize ", notes="活动参与记录")
    @RaceLog(module= LogConstant.Gc_Join_Activity_Record, actionDesc = "活动参与记录")
    @PutMapping("/queryJoinActivityByAid")
    public BaseResult queryJoinActivityByAid(@RequestBody JoinActivityPage joinActivityPage) {

        try {
            if(StringUtils.isNull(joinActivityPage) || StringUtils.isNull(joinActivityPage.getAid())||StringUtils.isNull(joinActivityPage.getUid())){
                return new BaseResult(BaseResultEnum.BLANK.getStatus(), "用户ID或活动ID不能为空", false);
            }
            Page<JoinActivityDto> tPage = buildPage(joinActivityPage);
            List<JoinActivityDto> list =  gcJoinactivityrecordService.queryJoinActivityByAid(tPage, joinActivityPage);
            if(StringUtils.isNotEmpty(list)){
                tPage.setTotal(list.size());
            }
            tPage.setRecords(list);
            return new BaseResult(BaseResultEnum.SUCCESS.getStatus(), "查询成功", tPage);
        } catch (Exception e) {
            throw new RuntimeException("查询活动参与记录异常：" + e.getMessage());
        }

    }

    private Page<JoinActivityDto> buildPage(JoinActivityPage joinActivityPage) {
        Page<JoinActivityDto> tPage =new Page<JoinActivityDto>(joinActivityPage.getPageNo(),joinActivityPage.getPageSize());
        if(StringUtils.isNotEmpty(joinActivityPage.getOrderBy())){
            tPage.setOrderByField(joinActivityPage.getOrderBy());
            tPage.setAsc(false);
        }
        if(StringUtils.isNotEmpty(joinActivityPage.getOrderByAsc())){
            tPage.setOrderByField(joinActivityPage.getOrderByAsc());
            tPage.setAsc(true);
        }
        return tPage;
    }


}
