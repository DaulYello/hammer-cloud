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
import com.fmkj.race.server.util.CalendarTime;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(GcJoinactivityrecordController.class);

    @Autowired
    private GcActivityService gcActivityService;

    @Autowired
    private GcJoinactivityrecordService gcJoinactivityrecordService;

    /**
     *  用户参加活动
     */
    @ApiOperation(value="用户参加活动，参数：aid,uid", notes="用户参加活动")
    @RaceLog(module= LogConstant.Gc_Activity, actionDesc = "用户参加活动")
    @PostMapping("/ActivityRabbitMQ")
    public BaseResult ActivityRabbitMQ(@RequestBody JoinActivityPage joinActivity){
        if (StringUtils.isNull(joinActivity.getAid())) {
            return new BaseResult(BaseResultEnum.ERROR, "活动ID不能为空");
        }
        if (StringUtils.isNull(joinActivity.getUid())) {
            return new BaseResult(BaseResultEnum.ERROR, "用户ID不能为空");
        }
        //是否存在该活动或活动已经结束
        GcActivity gcActivity = gcActivityService.selectById(joinActivity.getAid());
        LOGGER.info("用户参加活动:" + JSON.toJSONString(gcActivity));

        if (StringUtils.isNull(gcActivity)) {
            return new BaseResult(BaseResultEnum.ERROR, "没有该活动");
        }

        GcJoinactivityrecord where = new GcJoinactivityrecord();
        where.setAid(joinActivity.getAid());
        EntityWrapper<GcJoinactivityrecord> entityWrapper = new EntityWrapper<>(where);
        int count = gcJoinactivityrecordService.selectCount(entityWrapper);//当前参与人数
        if(count == gcActivity.getNum()){
            return new BaseResult(BaseResultEnum.ERROR, "参与活动人数已满");
        }
        if (gcActivity.getStatus().equals(3)||gcActivity.getStatus().equals(4)||gcActivity.getStatus().equals(5)) {
            return new BaseResult(BaseResultEnum.ERROR, "活动已结束");
        }
        /*********插入参与记录**********/
        GcJoinactivityrecord gcJoinactivityrecord = new GcJoinactivityrecord();
        gcJoinactivityrecord.setTime(new Date());
        gcJoinactivityrecord.setIschain(0);
        gcJoinactivityrecord.setAid(joinActivity.getAid());
        gcJoinactivityrecord.setUid(joinActivity.getUid());
        boolean result = gcJoinactivityrecordService.addActivity(gcJoinactivityrecord, gcActivity.getPar());
        int joinNum = gcJoinactivityrecordService.selectCount(entityWrapper);//当前参与人数
        if(joinNum == gcActivity.getNum()){
            gcJoinactivityrecord.setIslast(1);
            gcJoinactivityrecordService.updateById(gcJoinactivityrecord);
        }
        if (!result){
            return new BaseResult(BaseResultEnum.ERROR, "插入活动记录表失败");
        }
        return new BaseResult(BaseResultEnum.SUCCESS, gcJoinactivityrecord);

    }


    @ApiOperation(value="活动参与记录,参数：aid,uid,pageSize ", notes="活动参与记录")
    @PutMapping("/queryJoinActivityByAid")
    public BaseResult queryJoinActivityByAid(@RequestBody JoinActivityPage joinActivityPage) {

        try {
            if(StringUtils.isNull(joinActivityPage) || StringUtils.isNull(joinActivityPage.getAid())||StringUtils.isNull(joinActivityPage.getUid())){
                return new BaseResult(BaseResultEnum.BLANK.getStatus(), "用户ID或活动ID不能为空", false);
            }
            Page<JoinActivityDto> tPage = buildPage(joinActivityPage);
            List<JoinActivityDto> list =  gcJoinactivityrecordService.queryJoinActivityByAid(tPage, joinActivityPage);
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


    @PutMapping("/pushNoticeAndActivity")
    public BaseResult pushNoticeAndActivity(@RequestBody JoinActivityDto joinActivityDto){

        LOGGER.info("发消息失败重新调用接口，发送信息");
        LOGGER.info("参数："+JSON.toJSONString(joinActivityDto));
        boolean result = gcJoinactivityrecordService.joinActivityWin(joinActivityDto,joinActivityDto.getPar(),joinActivityDto.getWinId());
        return new BaseResult(BaseResultEnum.SUCCESS.getStatus(), "查询成功", result);
    }
}
