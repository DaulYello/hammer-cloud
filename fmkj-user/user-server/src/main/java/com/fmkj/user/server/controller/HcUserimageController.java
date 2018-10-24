package com.fmkj.user.server.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.fmkj.common.base.BaseApiService;
import com.fmkj.common.base.BaseController;
import com.fmkj.common.base.BaseResult;
import com.fmkj.common.base.BaseResultEnum;
import com.fmkj.common.constant.LogConstant;
import com.fmkj.common.util.PropertiesUtil;
import com.fmkj.common.util.StringUtils;
import com.fmkj.user.dao.domain.HcAccount;
import com.fmkj.user.dao.domain.HcUserimage;
import com.fmkj.user.server.annotation.UserLog;
import com.fmkj.user.server.enmu.ImageEnum;
import com.fmkj.user.server.service.HcUserimageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.websocket.server.PathParam;

@RestController
@RequestMapping("/hcUserimage")
@DependsOn("springContextUtil")
@Api(tags ={ "用户实名认证"},description = "用户实名认证接口-网关路径/api-user")
public class HcUserimageController extends BaseController<HcUserimage, HcUserimageService> implements BaseApiService<HcUserimage> {

    private static final Logger LOGGER = LoggerFactory.getLogger(HcUserimageController.class);

    @Autowired
    private HcUserimageService hcUserimageService;

    @Value("${userPayImageIpPath}")
    private String userPayImageIpPath;

    @Value("${userPayImagePath}")
    private String userPayImagePath;

    @Value("${userCodeImagePath}")
    private String userCodeImagePath;

    @Value("${userCodeImageIpPath}")
    private String userCodeImageIpPath;


    @ApiOperation(value="实名认证" ,notes="参数：id,cardnum,name")
    @UserLog(module= LogConstant.HC_CERT, actionDesc = "保存用户的实名信息")
    @PostMapping("/saveUserRealInfo")
    public BaseResult saveUserRealInfo(@RequestBody HcAccount account) {
        if(StringUtils.isNull(account.getId())){
            return  new BaseResult(BaseResultEnum.BLANK.status, "用户ID不能为空", false);
        }
        if(StringUtils.isNull(account.getName())){
            return  new BaseResult(BaseResultEnum.BLANK.status, "用户姓名不能为空", false);
        }
        if(StringUtils.isNull(account.getCardnum())){
            return  new BaseResult(BaseResultEnum.BLANK.status, "用户证件号码不能为空", false);
        }

        return hcUserimageService.saveUserRealInfo(account);
    }


    @ApiOperation(value="上传照片身份证" ,notes="参数：uid,file,status 1.正面2.反面")
    @UserLog(module= LogConstant.HC_CERT, actionDesc = "上传身份证照片的正反面")
    @PostMapping("/uploadCardImage")
    public BaseResult uploadCardImage(@PathParam(value = "uid") Integer uid,
                                      @PathParam(value="status") Integer status,
                                      @RequestParam("file") MultipartFile file) {
        try{
            if(StringUtils.isNull(uid)){
                return  new BaseResult(BaseResultEnum.BLANK.status, "用户ID不能为空", false);
            }
            if(StringUtils.isNull(status)){
                return  new BaseResult(BaseResultEnum.BLANK.status, "status不能为空", false);
            }
            HcUserimage userimage = new HcUserimage();
            userimage.setUid(uid);
            userimage.setUrl(userCodeImagePath);
            String newFileName=PropertiesUtil.uploadImage(file,userCodeImagePath);
            if(status == ImageEnum.TYPE_FULL.status){
                userimage.setFullPhoto(newFileName);
            }else if(status == ImageEnum.TYPE_REVERSE.status){
                userimage.setReversePhoto(newFileName);
            }

            HcUserimage hm = new HcUserimage();
            hm.setUid(uid);
            EntityWrapper<HcUserimage> wrapper = new EntityWrapper<>(hm);
            HcUserimage hcUserimage = hcUserimageService.selectOne(wrapper);
            boolean result = false;
            if(StringUtils.isNull(hcUserimage)){
                result = hcUserimageService.insert(userimage);
            }else{
                userimage.setId(hcUserimage.getId());
                result = hcUserimageService.updateById(userimage);
            }
            if(result){
                return new BaseResult(BaseResultEnum.SUCCESS.getStatus(), "上传成功!", true);
            }else {
                return new BaseResult(BaseResultEnum.SUCCESS.getStatus(), "上传失败!", false);
            }
        }catch (Exception e){
            return new BaseResult(BaseResultEnum.ERROR.getStatus(), "上传失败!", e.getMessage());
        }
    }

    @ApiOperation(value="上传支付照片" ,notes="参数：uid,file,status：3.绑定微信4.绑定支付宝")
    @UserLog(module= LogConstant.HC_CERT, actionDesc = "上传微信和支付宝的支付二维码")
    @PostMapping("/uploadPayImage")
    public BaseResult uploadPayImage(@PathParam(value = "uid") Integer uid,
                                      @PathParam(value="status") Integer status,
                                      @RequestParam("file") MultipartFile file) {
        try{
            if(StringUtils.isNull(uid)){
                return  new BaseResult(BaseResultEnum.BLANK.status, "用户ID不能为空", false);
            }
            if(StringUtils.isNull(file)){
                return  new BaseResult(BaseResultEnum.BLANK.status, "没有选择上传的照片", false);
            }
            if(StringUtils.isNull(status)){
                return  new BaseResult(BaseResultEnum.BLANK.getStatus(), "status不能为空", false);
            }
            LOGGER.debug("首先判断用户有没有实名认证-------------------------------------------start");
            HcUserimage imagePay = new HcUserimage();
            imagePay.setUid(uid);
            EntityWrapper<HcUserimage> wrapper = new EntityWrapper<>(imagePay);
            HcUserimage hcUserimage = hcUserimageService.selectOne(wrapper);
            if(StringUtils.isNull(hcUserimage)){
                return new BaseResult(BaseResultEnum.ERROR.getStatus(), "您还没有实名认证，请先实名认证！", false);
            }
            LOGGER.debug("首先判断用户有没有实名认证-------------------------------------------end");

            LOGGER.debug("开始上传照片-------------------------------------------start");
            String newFileName=PropertiesUtil.uploadImage(file,userPayImagePath);
            if(status == ImageEnum.TYPE_WECHAT.status){
                hcUserimage.setWechatPhoto(userPayImageIpPath+newFileName);
            }else if(status == ImageEnum.TYPE_ALIPAY.status){
                hcUserimage.setAlipayPhoto(userPayImageIpPath+newFileName);
            }
            LOGGER.debug("开始上传照片-------------------------------------------end");

            boolean result  = hcUserimageService.updateById(hcUserimage);

            if(result){
                return new BaseResult(BaseResultEnum.SUCCESS.getStatus(), "上传成功!", true);
            }else {
                return new BaseResult(BaseResultEnum.ERROR.getStatus(), "上传失败!", false);
            }
        }catch (Exception e){
            return new BaseResult(BaseResultEnum.ERROR.getStatus(), "上传失败!", e.getMessage());
        }
    }


    @ApiOperation(value="支付认证" ,notes="参数：uid,alipayAccount,wechatAccount")
    @UserLog(module= LogConstant.HC_CERT, actionDesc = "保存用户的实名信息")
    @PostMapping("/saveUserAccountInfo")
    public BaseResult saveUserAccountInfo(@RequestBody HcUserimage userimage) {

        Integer status = 0;

        if(StringUtils.isNull(userimage.getUid())){
            return  new BaseResult(BaseResultEnum.BLANK.status, "用户ID不能为空", false);
        }
        if((StringUtils.isNull(userimage.getAlipayAccount()) && StringUtils.isNull(userimage.getWechatAccount()))){
            return  new BaseResult(BaseResultEnum.BLANK.status, "账号不能为空！", false);
        }
        if((StringUtils.isNotNull(userimage.getAlipayAccount()) && StringUtils.isNotNull(userimage.getWechatAccount()))){
            status=ImageEnum.TYPE_ALIPAYANDWECHAT.status;
        }
        if(StringUtils.isNotNull(userimage.getAlipayAccount())){
            status=ImageEnum.TYPE_ALIPAY.status;
        }
        if(StringUtils.isNotNull(userimage.getWechatAccount())){
            status=ImageEnum.TYPE_WECHAT.status;
        }
        return hcUserimageService.saveUserAccountInfo(userimage,status);
    }


    @ApiOperation(value="支付方式" ,notes="参数：uid")
    @UserLog(module= LogConstant.HC_CERT, actionDesc = "通过用户的id获取绑定支付方式")
    @PutMapping("/getUserPayWay")
    public BaseResult<HcUserimage> getUserPayWay(@RequestBody HcUserimage userimage) {

        LOGGER.debug("通过用户的id获取绑定支付方式"+userimage.getUid());
        try {
            if(StringUtils.isNull(userimage) || StringUtils.isNull(userimage.getUid())){
                return new BaseResult(BaseResultEnum.BLANK.getStatus(), "uid不能为空", false);
            }
            HcUserimage hcUserimage = hcUserimageService.getUserPayWay(userimage);
            return new BaseResult(BaseResultEnum.SUCCESS.getStatus(), "查询成功", hcUserimage);
        } catch (Exception e) {
            throw new RuntimeException("根据ID查询订单异常：" + e.getMessage());
        }
    }
}
