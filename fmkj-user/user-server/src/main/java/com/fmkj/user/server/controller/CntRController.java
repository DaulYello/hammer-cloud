package com.fmkj.user.server.controller;

import com.fmkj.common.base.BaseResult;
import com.fmkj.common.base.BaseResultEnum;
import com.fmkj.common.constant.LogConstant;
import com.fmkj.common.util.StringUtils;
import com.fmkj.user.dao.domain.FmCntInfo;
import com.fmkj.user.dao.domain.FmIntegralInfo;
import com.fmkj.user.dao.dto.CntRDto;
import com.fmkj.user.dao.queryVo.CntRVo;
import com.fmkj.user.server.annotation.UserLog;
import com.fmkj.user.server.service.FmCntInfoService;
import com.fmkj.user.server.service.FmIntegralInfoService;
import com.fmkj.user.server.service.FmRecyleLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 *  CNT, R积分服务
 */
@RestController
@RequestMapping("/gral")
@DependsOn("springContextUtil")
@Api(tags ={ "CNT与R积分服务"},description = "积分接口-网关路径/api-user")
public class CntRController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CntRController.class);

    @Autowired
    private FmIntegralInfoService fmIntegralInfoService;

    @Autowired
    private FmCntInfoService fmCntInfoService;

    @Autowired
    private FmRecyleLogService fmRecyleLogService;

    /**
     * 查询用户当天获取的CNT与R积分
     *
     * @return
     */
    @ApiOperation(value="查询用户当天获取的CNT与R积分", notes="参数：uid")
    @PutMapping("/queryCNTRbyId")
    public BaseResult queryCNTRbyId(@RequestBody CntRVo cntRVo) {
        if (StringUtils.isNull(cntRVo.getUid())) {
            return new BaseResult(BaseResultEnum.BLANK.getStatus(), "用户ID不能为空!", false);
        }
        CntRDto cntRDto = fmIntegralInfoService.queryCNTRbyId(cntRVo.getUid());
        return new BaseResult(BaseResultEnum.SUCCESS.getStatus(), "查询成功!", cntRDto);
    }

    /**
     * 查询CNT动态
     *
     * @return
     */
    @ApiOperation(value="查询当天R积分收取排行榜", notes="参数：无参数")
    @PutMapping("/queryRankList")
    public BaseResult queryRankList() {
        List rankList = fmRecyleLogService.queryRankList();
        return new BaseResult(BaseResultEnum.SUCCESS.getStatus(), "查询成功!", rankList);
    }


    @ApiOperation(value="查询R积分与CNT动态", notes="参数：uid")
    @PutMapping("/queryDynamic")
    public BaseResult queryDynamic(@RequestBody CntRVo cntRVo) {
        if (StringUtils.isNull(cntRVo.getUid())) {
            return new BaseResult(BaseResultEnum.BLANK.getStatus(), "用户ID不能为空!", false);
        }
        List dynamicList = fmRecyleLogService.queryCntDynamic(cntRVo.getUid());
        return new BaseResult(BaseResultEnum.SUCCESS.getStatus(), "查询成功!", dynamicList);
    }

    @ApiOperation(value="用户收取CNT", notes="参数：uid, cntId")
    @UserLog(module= LogConstant.HC_ACCOUNT, actionDesc = "收取CNT")
    @PostMapping("/getCntByUid")
    public BaseResult getCntByUid(@RequestBody CntRVo cntRVo) {
        if (StringUtils.isNull(cntRVo.getUid())) {
            return new BaseResult(BaseResultEnum.BLANK.getStatus(), "用户ID不能为空!", false);
        }
        if (StringUtils.isNull(cntRVo.getCntId())) {
            return new BaseResult(BaseResultEnum.BLANK.getStatus(), "cntId不能为空!", false);
        }
        HashMap<String, Object> result = new HashMap<>();
        FmCntInfo cntInfo = fmCntInfoService.getCntByUid(cntRVo);
        if(StringUtils.isNotNull(cntInfo)) {
            result.put("cntNum", cntInfo.getCntNum());
            result.put("cntTime", cntInfo.getUpdateDate());
        }
        return new BaseResult(BaseResultEnum.SUCCESS.getStatus(), "成功收取CNT!", result);
    }

    @ApiOperation(value="用户收取R积分", notes="参数：uid, rId")
    @UserLog(module= LogConstant.HC_ACCOUNT, actionDesc = "收取R积分")
    @PostMapping("/getRIntegral")
    public BaseResult getRIntegral(@RequestBody CntRVo cntRVo) {
        if (StringUtils.isNull(cntRVo.getUid())) {
            return new BaseResult(BaseResultEnum.BLANK.getStatus(), "用户ID不能为空!", false);
        }
        if (StringUtils.isNull(cntRVo.getrId())) {
            return new BaseResult(BaseResultEnum.BLANK.getStatus(), "积分ID不能为空!", false);
        }
        HashMap<String, Object> result = new HashMap<>();
        FmIntegralInfo rIntegral = fmIntegralInfoService.getRIntegral(cntRVo);
        if(StringUtils.isNotNull(rIntegral)) {
            result.put("gralNum", rIntegral.getIntegralNum());
            result.put("gralTime", rIntegral.getUpdateDate());
        }
        return new BaseResult(BaseResultEnum.SUCCESS.getStatus(), "成功收取R积分!", result);
    }

    @ApiOperation(value="偷取好友R积分", notes="参数：uid, rId, friendId")
    @UserLog(module= LogConstant.HC_ACCOUNT, actionDesc = "收取R积分")
    @PostMapping("/stealRIntegral")
    public BaseResult stealRIntegral(@RequestBody CntRVo cntRVo) {
        if (StringUtils.isNull(cntRVo.getUid())) {
            return new BaseResult(BaseResultEnum.BLANK.getStatus(), "用户ID不能为空!", false);
        }
        if (StringUtils.isNull(cntRVo.getrId())) {
            return new BaseResult(BaseResultEnum.BLANK.getStatus(), "积分ID不能为空!", false);
        }
        if (StringUtils.isNull(cntRVo.getFriendId())) {
            return new BaseResult(BaseResultEnum.BLANK.getStatus(), "好友ID不能为空!", false);
        }
        HashMap<String, Object> result = new HashMap<>();
        //判断是不是被偷过
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("status", -1); //被偷取过的
        paramMap.put("uid", cntRVo.getFriendId());
        List<FmIntegralInfo> list = fmIntegralInfoService.queryRList(paramMap);
        if(StringUtils.isNotEmpty(list) && list.size() > 0){
            result.put("stealStatus", -1);
            return new BaseResult(BaseResultEnum.SUCCESS.getStatus(), "已经被偷取过!", result);
        }
        FmIntegralInfo rIntegral = fmIntegralInfoService.stealRIntegral(cntRVo);
        if(StringUtils.isNotNull(rIntegral)){
            result.put("stealNum", rIntegral.getIntegralNum());
            result.put("stealTime", rIntegral.getUpdateDate());
        }
        return new BaseResult(BaseResultEnum.SUCCESS.getStatus(), "成功偷取R积分!", result);
    }


    @ApiOperation(value="查询用户可收取的R积分（当前时间起前2天之内）", notes="参数：uid")
    @PutMapping("/queryRList")
    public BaseResult queryRList(@RequestBody CntRVo cntRVo) {
        if (StringUtils.isNull(cntRVo.getUid())) {
            return new BaseResult(BaseResultEnum.BLANK.getStatus(), "用户ID不能为空!", false);
        }
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("status", 0); //未被收取过的
        paramMap.put("uid", cntRVo.getUid());
        List<FmIntegralInfo> list = fmIntegralInfoService.queryRList(paramMap);
        return new BaseResult(BaseResultEnum.SUCCESS.getStatus(), "查询成功!", list);
    }

    @ApiOperation(value="查询用户可收取的CNT（当前时间起前1天之内）", notes="参数：uid")
    @PutMapping("/queryCNTList")
    public BaseResult queryCNTList(@RequestBody CntRVo cntRVo) {
        if (StringUtils.isNull(cntRVo.getUid())) {
            return new BaseResult(BaseResultEnum.BLANK.getStatus(), "用户ID不能为空!", false);
        }
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("status", 0); //未被收取过的
        paramMap.put("uid", cntRVo.getUid());
        List<FmIntegralInfo> list = fmCntInfoService.queryCNTList(paramMap);
        return new BaseResult(BaseResultEnum.SUCCESS.getStatus(), "查询成功!", list);
    }

}
