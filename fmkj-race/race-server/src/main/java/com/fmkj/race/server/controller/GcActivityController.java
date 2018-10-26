package com.fmkj.race.server.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.fmkj.common.base.BaseApiService;
import com.fmkj.common.base.BaseController;
import com.fmkj.common.base.BaseResult;
import com.fmkj.common.base.BaseResultEnum;
import com.fmkj.common.constant.LogConstant;
import com.fmkj.common.util.StringUtils;
import com.fmkj.race.dao.domain.GcActivity;
import com.fmkj.race.dao.domain.GcPimage;
import com.fmkj.race.dao.dto.GcActivityDto;
import com.fmkj.race.dao.queryVo.GcBaseModel;
import com.fmkj.race.server.annotation.RaceLog;
import com.fmkj.race.server.api.BmListApi;
import com.fmkj.race.server.async.RaceAsyncFactory;
import com.fmkj.race.server.async.RaceAsyncManager;
import com.fmkj.race.server.service.GcActivityService;
import com.fmkj.race.server.service.GcPimageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.websocket.server.PathParam;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @ Author     ：yangshengbin
 * @ Date       ：16:28 2018/8/29 0029
 * @ Description：活动控制层
 */
@RestController
@RequestMapping("/gcactivity")
@DependsOn("springContextHandler")
@Api(tags ={ "活动服务"},description = "活动服务接口-网关路径/api-race")
public class GcActivityController extends BaseController<GcActivity,GcActivityService> implements BaseApiService<GcActivity>{

    @Autowired
    private GcActivityService gcActivityService;//活动接口

    //用户黑白名单接口
    @Autowired
    private BmListApi bmListApi;

    @Autowired
    private GcPimageService gcPimageService;//活动图片接口

    @Value("${activityImagePath}")
    private String activityImagePath;

    @Value("${activityImageIpPath}")
    private String activityImageIpPath;

    @Value("${defuatFileName}")
    private String defuatFileName;

    @ApiOperation(value="发起活动", notes="用户发起活动")
    @RaceLog(module= LogConstant.Gc_Activity, actionDesc = "用户发起活动")
    @PostMapping(value = "/startActivityByExample")
    public  BaseResult startActivityByExample(@PathParam(value = "name") String name,
                  @PathParam(value = "startid") Integer startid,
                  @PathParam(value = "typeid") Integer typeid,
                  @PathParam(value = "pnumber") Integer pnumber,
                  @PathParam(value = "pname") String pname,
                  @PathParam(value = "price") Double price,
                  @PathParam(value = "premium") Double premium,
                  @PathParam(value = "pdescribe") String pdescribe,
                  @PathParam(value = "num") Integer num,
                  @PathParam(value = "type") String type,
                  @PathParam(value = "par") Double par,
                  @RequestParam MultipartFile[] file){
        if(StringUtils.isEmpty(name)){
            return new BaseResult(BaseResultEnum.BLANK.getStatus(), "name不能为空", false);
        }
        if(StringUtils.isNull(startid)){
            return new BaseResult(BaseResultEnum.BLANK.getStatus(), "startid不能为空", false);
        }
        if(StringUtils.isNull(typeid)){
            return new BaseResult(BaseResultEnum.BLANK.getStatus(), "typeid不能为空", false);
        }
        //判断用户是否黑名单
        HashMap<String, Object> params = new HashMap<>();
        params.put("uid", startid);
        params.put("status", 2);
        boolean isBlack = bmListApi.isActivityBlack(params);
        if(isBlack) {
            return new BaseResult(BaseResultEnum.ERROR.status, "您已被拉入黑名单,黑名单用户不可发起活动!",false);
        }

        /*//判断活动名称是否包含敏感词汇
        if(StringUtils.isNotEmpty(name)) {
            String wordIsOk= SensitiveWordUtil.replaceBadWord(name.trim(),2,"*");
            if(!name.trim().equals(wordIsOk.trim())) {
                return new BaseResult(BaseResultEnum.ERROR.status, "活动名称含有敏感词汇，请重新写一个吧!",false);
            }
        }

        //判断产品的描述详情是否包含敏感词汇
        if(StringUtils.isNotEmpty(pdescribe)) {
            String wordIsOk= SensitiveWordUtil.replaceBadWord(pdescribe.trim(),2,"*");
            if(!pdescribe.equals(wordIsOk.trim())) {
                return new BaseResult(BaseResultEnum.ERROR.status, "产品的描述详情含有敏感词汇，请重新写一个吧!",false);
            }
        }

        //判断产品名称是否包含敏感词汇
        if(StringUtils.isNotEmpty(pname)) {
            String wordIsOk= SensitiveWordUtil.replaceBadWord(pname.trim(),2,"*");
            if(!pname.equals(wordIsOk.trim())) {
                return new BaseResult(BaseResultEnum.ERROR.status, "产品名称含有敏感词汇，请重新写一个吧!",false);
            }
        }*/

        GcActivity ga = new GcActivity();
        ga.setStartid(startid);
        ga.setName(name);
        ga.setPname(pname);
        ga.setTypeid(typeid);
        ga.setPnumber(pnumber);
        ga.setPrice(price);
        ga.setPremium(premium);
        EntityWrapper wrapper = new EntityWrapper();
        wrapper.setEntity(ga);
        GcActivity gcActivity = gcActivityService.selectOne(wrapper);//判断相同活动是否存在
        if(StringUtils.isNotNull(gcActivity)) {
            return new BaseResult(BaseResultEnum.ERROR.status, "发起活动失败，该活动已存在!",false);
        }
        ga.setPdescribe(pdescribe);
        ga.setTime(new Date());
        ga.setStatus(0);
        ga.setDelivergoodstatus(0);
        ga.setNum(num);
        ga.setCollectgoodstatus(0);
        ga.setPar(par);
        boolean result = gcActivityService.addGcActivity(ga);
        if(result){
            if(file != null){
                RaceAsyncManager.me().execute(RaceAsyncFactory.uploadImage(ga.getId(), file, activityImagePath, activityImageIpPath, defuatFileName));
            }
            return new BaseResult(BaseResultEnum.SUCCESS.status, "活动发起成功!",true);
        }
        return new BaseResult(BaseResultEnum.ERROR.status, "活动发起失败!",false);

    }

    //  活动广场分页查询所有活动,只查询活动中(status=2)
    @ApiOperation(value="活动广场分页查询所有活动", notes="活动广场分页查询所有活动")
    @RaceLog(module= LogConstant.Gc_Activity, actionDesc = "活动广场分页查询所有活动")
    @PutMapping("/queryAllActivityByPage")
    public BaseResult queryAllActivityByPage(@RequestBody GcBaseModel gcBaseModel){
        try {
            if(StringUtils.isNull(gcBaseModel) || StringUtils.isNull(gcBaseModel.getUid())){
                return new BaseResult(BaseResultEnum.BLANK.getStatus(), "用户ID不能为空", false);
            }
            Page<GcActivityDto> tPage = buildPage(gcBaseModel);
            List<GcActivityDto> list = gcActivityService.queryAllActivityByPage(tPage, gcBaseModel);
            tPage.setRecords(list);
            return new BaseResult(BaseResultEnum.SUCCESS.getStatus(), "查询成功", tPage);
        } catch (Exception e) {
            throw new RuntimeException("活动广场分页查询所有活动异常：" + e.getMessage());
        }
    }




    @ApiOperation(value="传入活动id查询活动详情", notes="传入活动id查询活动详情")
    @RaceLog(module= LogConstant.Gc_Activity, actionDesc = "传入活动id查询活动详情")
    @PutMapping("/queryActivityById")
    public BaseResult<HashMap<String,Object>> queryActivityById(@RequestBody GcBaseModel gcBaseModel){

        HashMap<String, Object> map = gcActivityService.queryActivityById(gcBaseModel);
        return new BaseResult(BaseResultEnum.SUCCESS,map);
    }




    @ApiOperation(value="传入活动id查询活动产品的所有图片", notes="传入活动id查询活动产品的所有图片")
    @RaceLog(module= LogConstant.Gc_Activity, actionDesc = "传入活动id查询活动产品的所有图片")
    @PutMapping("/queryActivityImageById")
    public BaseResult queryActivityImageById(@RequestBody GcBaseModel gcBaseModel){

        List<GcPimage> list = gcPimageService.queryActivityImageById(gcBaseModel);
        return new BaseResult(BaseResultEnum.SUCCESS,list);

    }




    //传入uid查询用户参与的活动    status：1进行中  2已锤到的  3.已结束
    @ApiOperation(value="查询用户参与的活动，参数：pageSize,status（二级菜单编号）,uid", notes="查询用户参与的活动")
    @RaceLog(module= LogConstant.Gc_Activity, actionDesc = "查询用户参与的活动")
    @PutMapping("/queryMyJoinActivityByUid")
    public BaseResult queryMyJoinActivityByUid(@RequestBody GcBaseModel gcBaseModel){
        try {
            if(StringUtils.isNull(gcBaseModel) || StringUtils.isNull(gcBaseModel.getUid())){
                return new BaseResult(BaseResultEnum.BLANK.getStatus(), "用户ID不能为空", false);
            }
            Page<GcActivityDto> tPage = buildPage(gcBaseModel);
            List<GcActivityDto> list = gcActivityService.queryMyJoinActivityByUid(tPage, gcBaseModel);
            tPage.setRecords(list);
            return new BaseResult(BaseResultEnum.SUCCESS.getStatus(), "查询成功", tPage);
        } catch (Exception e) {
            throw new RuntimeException("查询用户参与的活动异常：" + e.getMessage());
        }

    }




    /**
     *  传入uid查询用户发起的活动    status：0:待审核 1:驳回 2:活动中 3：已结束 4：活动异常 5：活动失败
     */
    @ApiOperation(value="传入uid查询用户发起的活动，参数：pageSize，status，uid", notes="传入uid查询用户发起的活动 ")
    @RaceLog(module= LogConstant.Gc_Activity, actionDesc = "传入uid查询用户发起的活动 ")
    @PutMapping("/queryMyStartActivityByUid")
    public BaseResult queryMyStartActivityByUid(@RequestBody GcBaseModel gcBaseModel){
        try {
            if(StringUtils.isNull(gcBaseModel) || StringUtils.isNull(gcBaseModel.getUid())){
                return new BaseResult(BaseResultEnum.BLANK.getStatus(), "用户ID不能为空", false);
            }
            Page<GcActivityDto> tPage = buildPage(gcBaseModel);
            List<GcActivityDto> list = gcActivityService.queryMyStartActivityByUid(tPage, gcBaseModel);
            tPage.setRecords(list);
            return new BaseResult(BaseResultEnum.SUCCESS.getStatus(), "查询成功", tPage);
        } catch (Exception e) {
            throw new RuntimeException("查询用户发起的活动活动异常：" + e.getMessage());
        }

    }

    @ApiOperation(value="根据活动id查询活动中奖人,参数：id", notes="根据活动id查询活动中奖人")
    @RaceLog(module= LogConstant.Gc_Activity, actionDesc = "根据活动id查询活动中奖人")
    @PutMapping("/queryActivityByUserId")
    public BaseResult<HashMap<String,Object>> queryActivityByUserId(@RequestBody GcActivity gcActivity){
        if(StringUtils.isNull(gcActivity) || StringUtils.isNull(gcActivity.getId())){
            return new BaseResult(BaseResultEnum.BLANK.getStatus(), "活动ID不能为空", false);
        }
        HashMap<String, Object> map = gcActivityService.queryActivityByUserId(gcActivity);
        return new BaseResult(BaseResultEnum.SUCCESS,map);
    }


    /**
     *  传入uid查询用户发起的活动    status：0:待审核 1:驳回 2:活动中 3：已结束 4：活动异常 5：活动失败
     */
    @ApiOperation(value="传入uid查询用户未处理的活动，参数：pageSize，uid", notes="传入uid查询用户未处理的活动")
    @RaceLog(module= LogConstant.Gc_Activity, actionDesc = "传入uid查询用户未处理的活动，")
    @PutMapping("/queryMyUntreatedActivityByUid")
    public BaseResult queryMyUntreatedActivityByUid(@RequestBody GcBaseModel gcBaseModel){
        try {
            if(StringUtils.isNull(gcBaseModel) || StringUtils.isNull(gcBaseModel.getUid())){
                return new BaseResult(BaseResultEnum.BLANK.getStatus(), "用户ID不能为空", false);
            }
            Page<GcActivityDto> tPage = buildPage(gcBaseModel);
            List<GcActivityDto> list = gcActivityService.queryMyUntreatedActivityByUid(tPage, gcBaseModel);
            tPage.setRecords(list);
            return new BaseResult(BaseResultEnum.SUCCESS.getStatus(), "查询成功", tPage);
        } catch (Exception e) {
            throw new RuntimeException("查询用户未处理的活动：" + e.getMessage());
        }

    }


    private Page<GcActivityDto> buildPage(GcBaseModel gcBaseModel) {
        Page<GcActivityDto> tPage =new Page<GcActivityDto>(gcBaseModel.getPageNo(),gcBaseModel.getPageSize());
        if(StringUtils.isNotEmpty(gcBaseModel.getOrderBy())){
            tPage.setOrderByField(gcBaseModel.getOrderBy());
            tPage.setAsc(false);
        }
        if(StringUtils.isNotEmpty(gcBaseModel.getOrderByAsc())){
            tPage.setOrderByField(gcBaseModel.getOrderByAsc());
            tPage.setAsc(true);
        }
        return tPage;
    }




}
