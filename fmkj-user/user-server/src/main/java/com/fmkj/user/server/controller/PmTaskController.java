package com.fmkj.user.server.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.fmkj.common.base.BaseResult;
import com.fmkj.common.base.BaseResultEnum;
import com.fmkj.common.constant.LogConstant;
import com.fmkj.common.util.PropertiesUtil;
import com.fmkj.common.util.StringUtils;
import com.fmkj.user.dao.domain.PmExtend;
import com.fmkj.user.dao.domain.PmImage;
import com.fmkj.user.dao.domain.PmPart;
import com.fmkj.user.dao.queryVo.TaskQueryVo;
import com.fmkj.user.server.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.websocket.server.PathParam;
import java.io.IOException;
import java.util.*;

/**
 *  任务中心
 */
@RestController
@RequestMapping("/task")
@Api(tags ={ "任务中心"},description = "任务中心接口-网关路径/api-user")
public class PmTaskController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PmTaskController.class);

    @Autowired
    private PmPartService pmPartService;

    @Autowired
    private PmTaskService pmTaskService;

    @Autowired
    private PmStrategyService pmStrategyService;

    @Autowired
    private PmImageService pmImageService;

    @Autowired
    private PmExtendService pmExtendService;

    @Autowired
    private PmExtendValueService pmExtendValueService;


    @Value("${auditImageIpPath}")
    private String auditImageIpPath;

    @Value("${auditImagePath}")
    private String auditImagePath;

    @Value("${auditdefuatFileName}")
    private String auditdefuatFileName;

    //查询滚动动态信息
    @ApiOperation(value="查询滚动动态信息", notes="参数：无")
    @PutMapping("/queryPartDynamic")
    public BaseResult queryPartDynamic(@RequestBody TaskQueryVo queryVo) {
        List partList  = pmPartService.queryPartDynamic();
        return new BaseResult(BaseResultEnum.SUCCESS.status,"查询成功!",partList);
    }


    @ApiOperation(value="分页获取任务热门列表", notes="参数：pageNo, pageSize 无需传排序字段")
    @PutMapping("/queryHotTaskPage")
    public BaseResult queryHotTaskPage(@RequestBody TaskQueryVo queryVo) {
        Page tPage = buildPage(queryVo);
        List hotTaskList = pmTaskService.queryHotTaskPage(tPage);
        tPage.setRecords(hotTaskList);
        return new BaseResult(BaseResultEnum.SUCCESS.getStatus(), "查询成功", tPage);
    }

    @ApiOperation(value="分页获取最新任务列表", notes="参数：pageNo, pageSize 无需传排序字段")
    @PutMapping("/queryNewTaskPage")
    public BaseResult queryNewTaskPage(@RequestBody TaskQueryVo queryVo) {
        Page tPage = buildPage(queryVo);
        List newTaskList = pmTaskService.queryNewTaskPage(tPage);
        tPage.setRecords(newTaskList);
        return new BaseResult(BaseResultEnum.SUCCESS.getStatus(), "查询成功", tPage);
    }


    @ApiOperation(value="根据任务ID获取详情", notes="参数：id, uid")
    @PutMapping("/queryTaskDetail")
    public BaseResult queryTaskById(@RequestBody TaskQueryVo queryVo) {
        if(StringUtils.isNull(queryVo) || StringUtils.isNull(queryVo.getId())){
            return new BaseResult(BaseResultEnum.BLANK.getStatus(), "任务ID不能为空", false);
        }
        if(StringUtils.isNull(queryVo.getUid())){
            return new BaseResult(BaseResultEnum.BLANK.getStatus(), "uid不能为空", false);
        }
        HashMap<String, Object> resultMap = new HashMap<>();
        HashMap taskMap = pmTaskService.queryTaskById(queryVo.getId());
        HashMap partMap = pmPartService.queryPartByUid(queryVo.getUid(), queryVo.getId());
        if(StringUtils.isNotEmpty(partMap)){
            taskMap.putAll(partMap);
        }
        resultMap.put("taskInfo", taskMap);
        List strategyList = pmStrategyService.queryStrategyByTid(queryVo.getId());
        HashMap<String, Object> strategyData = new HashMap<>();
        if(StringUtils.isNotEmpty(strategyList)){
            strategyData.put("totalSteps", strategyList.size());
            List<HashMap<String, Object>> mapList = new ArrayList();
            for(int i = 0; i < strategyList.size(); i++){
                HashMap<String, Object> strategyMap = (HashMap<String, Object>) strategyList.get(i);
                strategyMap.put("step", i+1);
                Integer strategyId = (Integer) strategyMap.get("id");
                List<PmImage> imageList = pmImageService.selectImageListByPartId(strategyId);
                for(PmImage image : imageList){
                    HashMap<String, Object> copyMap = new HashMap<>();
                    copyMap.putAll(strategyMap);
                    copyMap.put("strategyImage", image.getImageUrl());
                    mapList.add(copyMap);
                }

            }
            strategyData.put("strategyList", mapList);
        }
        resultMap.put("strategyData", strategyData);
        List promptList = pmStrategyService.queryPromptByTid(queryVo.getId());
        resultMap.put("promptList", promptList);
        return new BaseResult(BaseResultEnum.SUCCESS.getStatus(), "查询成功", resultMap);
    }

    @ApiOperation(value="查询参与记录详情", notes="参数：partId")
    @PutMapping("/queryAuditByPartId")
    public BaseResult queryAuditByPartId(@RequestBody TaskQueryVo queryVo) {
        if(StringUtils.isNull(queryVo) || StringUtils.isNull(queryVo.getPartId())){
            return new BaseResult(BaseResultEnum.BLANK.getStatus(), "partId不能为空", false);
        }
        List extendList = pmExtendValueService.queryExtendList(queryVo.getPartId());
        if(StringUtils.isEmpty(extendList)){
            return new BaseResult(BaseResultEnum.ERROR.getStatus(), "任务未初始化提交信息字段相关内容，不能参与！", false);
        }
        HashMap resultMap = pmPartService.queryAuditByPartId(queryVo.getPartId());
        //imageUrl
        String imageUrl = (String) resultMap.get("imageUrl");
        if(StringUtils.isNotEmpty(imageUrl)){
            resultMap.remove("imageUrl");
            String [] imageArry = imageUrl.split(",");
            List<String> imageList = Arrays.asList(imageArry);
            resultMap.put("imageList", imageList);
        }
        resultMap.put("extendList", extendList);
        return new BaseResult(BaseResultEnum.SUCCESS.getStatus(), "查询成功", resultMap);
    }

    @ApiOperation(value="立即参与", notes="参数:tid, uid")
    @PostMapping(value = "/partImmediately")
    public  BaseResult partImmediately(@RequestBody PmPart pmPart){
        if(StringUtils.isNull(pmPart) || StringUtils.isNull(pmPart.getTid())){
            return new BaseResult(BaseResultEnum.BLANK.getStatus(), "任务ID不能为空", false);
        }
        if(StringUtils.isNull(pmPart.getUid())){
            return new BaseResult(BaseResultEnum.BLANK.getStatus(), "uid不能为空", false);
        }
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("uid", pmPart.getUid());
        paramMap.put("tid", pmPart.getTid());
        List<PmPart> partList = pmPartService.selectByMap(paramMap);
        if(StringUtils.isNotEmpty(partList)){
            return new BaseResult(BaseResultEnum.SUCCESS.getStatus(), "你已经参与过了", true);
        }
        pmPart.setCreateDate(new Date());
        HashMap<String, Object> paramExtend = new HashMap<>();
        paramExtend.put("tid", pmPart.getTid());
        List<PmExtend> pmExtendList = pmExtendService.selectByMap(paramExtend);
        if(StringUtils.isEmpty(pmExtendList)){
            return new BaseResult(BaseResultEnum.ERROR.getStatus(), "任务未初始化提交信息，不能参与！", false);
        }
        boolean result = pmPartService.partImmediately(pmPart, pmExtendList);
        if(result){
            return new BaseResult(BaseResultEnum.SUCCESS.getStatus(), "参与成功", pmPart);
        }
        return new BaseResult(BaseResultEnum.ERROR.getStatus(), "参与失败", false);
    }


    @ApiOperation(value="提交审核", notes="参数:extendJsonStr, partId")
    @PostMapping(value = "/submitAudit")
    public  BaseResult submitAudit(@PathParam(value = "extendJsonStr") String extendJsonStr,
                                   @PathParam(value = "partId") Integer partId,
                                   @RequestParam MultipartFile[] file){

        if(StringUtils.isEmpty(extendJsonStr)){
            return new BaseResult(BaseResultEnum.BLANK.getStatus(), "提交信息不能为空", false);
        }
        if(StringUtils.isNull(partId)){
            return new BaseResult(BaseResultEnum.BLANK.getStatus(), "partId不能为空", false);
        }

       PmPart pmPart = new PmPart();
       pmPart.setAuditStatus(1);
       pmPart.setUpdateDate(new Date());
       pmPart.setId(partId);
       boolean result = pmPartService.submitAudit(pmPart, extendJsonStr);
       if(result){
           excuteUpload(partId, file);
           return new BaseResult(BaseResultEnum.SUCCESS.getStatus(), "提交审核成功", true);
       }else{
           return new BaseResult(BaseResultEnum.ERROR.getStatus(), "提交审核失败", false);
       }
    }

    @Async
    public void excuteUpload(Integer partId, MultipartFile[] file){
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("part_id", partId);
        boolean delete = pmImageService.deleteByMap(paramMap);
        LOGGER.info("上传时清除历史图片返回结果：" + delete);
        int num = 0;
        if(delete){
            for (MultipartFile multipartFile : file) {
                num = num + 1;
                String fileName = null;
                try {
                    fileName = PropertiesUtil.uploadImage(multipartFile, auditImagePath);
                } catch (IOException e) {
                    LOGGER.info("上传审核图片异常：" + e.getMessage());
                    e.printStackTrace();
                }
                PmImage image = new PmImage();
                image.setCreateDate(new Date());
                image.setPartId(partId);
                image.setImageType(1);
                image.setPath(auditImagePath);
                if(fileName == null){
                    image.setImageUrl(auditImageIpPath + auditdefuatFileName);
                }else{
                    image.setImageUrl(auditImageIpPath + fileName);
                }
                boolean result = pmImageService.insert(image);
                LOGGER.info("上传审核图片【"+num+"】返回结果：" + result);
            }
        }
    }

    private Page buildPage(TaskQueryVo friendQueryVo) {
        Page tPage =new Page(friendQueryVo.getPageNo(),friendQueryVo.getPageSize());
        if(StringUtils.isNotEmpty(friendQueryVo.getOrderBy())){
            tPage.setOrderByField(friendQueryVo.getOrderBy());
            tPage.setAsc(false);
        }
        if(StringUtils.isNotEmpty(friendQueryVo.getOrderByAsc())){
            tPage.setOrderByField(friendQueryVo.getOrderByAsc());
            tPage.setAsc(true);
        }
        return tPage;
    }
}
