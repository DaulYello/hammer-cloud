package com.fmkj.user.server.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.fmkj.common.base.BaseResult;
import com.fmkj.common.base.BaseResultEnum;
import com.fmkj.common.constant.LogConstant;
import com.fmkj.common.util.StringUtils;

import com.fmkj.user.dao.domain.HcApkversion;
import com.fmkj.user.dao.domain.HcFeedback;
import com.fmkj.user.dao.domain.HcNotices;


import com.fmkj.user.dao.queryVo.NoticeQueryVo;
import com.fmkj.user.server.annotation.UserLog;
import com.fmkj.user.server.service.HcApkversionService;
import com.fmkj.user.server.service.HcFeedbackService;
import com.fmkj.user.server.service.HcNoticesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.bind.annotation.*;


import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/apkNoticeFeeback")
@DependsOn("springContextUtil")
@Api(tags = {"公告和版本信息"},description = "公告和版本信息-网关路径/api-user")
public class ApkNoticeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApkNoticeController.class);

    @Autowired
    private HcNoticesService hcNoticesService;
    @Autowired
    private HcFeedbackService hcFeedbackService;
    @Autowired
    private HcApkversionService hcApkversionService;

    @ApiOperation(value="最新公告信息",notes="查询最新的公告消息")
    @PutMapping("/queryNewestNotice")
    public BaseResult queryNewestNotice (){

        LOGGER.debug("查询最新一条");
        HcNotices notices = new HcNotices();
        notices.setPass(1);
        EntityWrapper<HcNotices> wrapper = new EntityWrapper<>(notices);
        wrapper.orderBy("time",false);
        HcNotices hcNotices = hcNoticesService.selectOne(wrapper);

        return  new BaseResult(BaseResultEnum.SUCCESS.status, "查询成功", hcNotices);
    }


    @ApiOperation(value="所有公告信息",notes="查询所有的公告消息")
    @PutMapping("/queryNoticesPage")
    public BaseResult queryNoticesPage (@RequestBody NoticeQueryVo queryVo){

        LOGGER.debug("查询所有");

        Page<HcNotices> tPage = buildPage(queryVo);
        HcNotices notices = new HcNotices();
        notices.setPass(1);
        EntityWrapper<HcNotices> wrapper = new EntityWrapper<>(notices);
        tPage = hcNoticesService.selectPage(tPage, wrapper);
        return new BaseResult(BaseResultEnum.SUCCESS.getStatus(), "查询成功", tPage);
    }

    @ApiOperation(value="发布反馈消息",notes="发布反馈消息,参数：uid,msg")
    @UserLog(module= LogConstant.FEEDBACK_INFO, actionDesc = "发布反馈消息")
    @PostMapping("/addfeedbackInfo")
    public BaseResult addfeedbackInfo (@RequestBody HcFeedback feedback){

        LOGGER.debug("发布反馈消息的参数："+ JSON.toJSONString(feedback));

        if(StringUtils.isNull(feedback.getUid())){
            return new BaseResult(BaseResultEnum.ERROR.getStatus(),"发布者的id不能为空",false);
        }
        if(StringUtils.isNull(feedback.getMsg())){
            return new BaseResult(BaseResultEnum.ERROR.getStatus(),"发布内容不能为空",false);
        }
        feedback.setTime(new Date());
        boolean result = hcFeedbackService.insert(feedback);

        return new BaseResult(BaseResultEnum.SUCCESS.getStatus(), "发布反馈消息成功", result);
    }


    @ApiOperation(value="查询发布反馈消息",notes="查询自己发布反馈消息,参数：uid")
    @PutMapping("/queryfeedbackInfo")
    public BaseResult queryfeedbackInfo (@RequestBody HcFeedback fk){

        LOGGER.debug("查询自己发布反馈消息");

        if(StringUtils.isNull(fk.getUid())){
            return new BaseResult(BaseResultEnum.ERROR.getStatus(),"用户的id不能为空",false);
        }
        HcFeedback feedback = new HcFeedback();
        feedback.setUid(fk.getUid());
        EntityWrapper<HcFeedback> wrapper = new EntityWrapper<>();
        wrapper.orderBy("time",false);
        List<HcFeedback> feedbacks = hcFeedbackService.selectList(wrapper);
        return new BaseResult(BaseResultEnum.SUCCESS.getStatus(), "查询成功", feedbacks);
    }


    @ApiOperation(value="查询最新的版本号",notes="查询最新的版本号")
    @PutMapping("/queryNewestApkverion")
    public BaseResult queryNewestApkverion (){

        LOGGER.debug("查询最新的版本号");
        HcApkversion apkversion = new HcApkversion();
        EntityWrapper<HcApkversion> wrapper = new EntityWrapper<>();
        wrapper.orderBy("time",false);
        HcApkversion hcApkversion = hcApkversionService.selectOne(wrapper);

        return  new BaseResult(BaseResultEnum.SUCCESS.status, "查询成功", hcApkversion);
    }


    private Page<HcNotices> buildPage(NoticeQueryVo noticeQueryVo) {
        Page<HcNotices> tPage =new Page<HcNotices>(noticeQueryVo.getPageNo(),noticeQueryVo.getPageSize());
        if(StringUtils.isNotEmpty(noticeQueryVo.getOrderBy())){
            tPage.setOrderByField(noticeQueryVo.getOrderBy());
            tPage.setAsc(false);
        }
        if(StringUtils.isNotEmpty(noticeQueryVo.getOrderByAsc())){
            tPage.setOrderByField(noticeQueryVo.getOrderByAsc());
            tPage.setAsc(true);
        }
        return tPage;
    }
}
