package com.fmkj.user.server.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.fmkj.common.base.BaseApiService;
import com.fmkj.common.base.BaseController;
import com.fmkj.common.base.BaseResult;
import com.fmkj.common.base.BaseResultEnum;
import com.fmkj.common.constant.LogConstant;
import com.fmkj.common.util.PropertiesUtil;
import com.fmkj.common.util.StringUtils;
import com.fmkj.user.dao.domain.HcUserimage;
import com.fmkj.user.server.annotation.UserLog;
import com.fmkj.user.server.enmu.ImageEnum;
import com.fmkj.user.server.service.HcUserimageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
    public BaseResult saveUserRealInfo(@PathParam(value = "id") Integer id,
                                       @PathParam(value = "name") String name,
                                       @PathParam(value = "cardnum") String cardnum) {
        if(StringUtils.isNull(id)){
            return  new BaseResult(BaseResultEnum.BLANK.status, "用户ID不能为空", false);
        }
        if(StringUtils.isNull(name)){
            return  new BaseResult(BaseResultEnum.BLANK.status, "用户姓名不能为空", false);
        }
        if(StringUtils.isNull(cardnum)){
            return  new BaseResult(BaseResultEnum.BLANK.status, "用户证件号码不能为空", false);
        }

        return hcUserimageService.saveUserRealInfo(id,name,cardnum);
    }


    @ApiOperation(value="上传照片身份证" ,notes="参数：uid,file,status 1.正面2.反面")
    @UserLog(module= LogConstant.HC_CERT, actionDesc = "上传身份证照片的正反面")
    @PostMapping("/uploadCardImage")
    public BaseResult uploadCardImage(@PathParam(value = "uid") Integer uid,
                                      @PathParam(value="status") Integer status,
                                      @RequestParam MultipartFile file) {
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
                userimage.setFullPhoto(userCodeImageIpPath + newFileName);
            }else if(status == ImageEnum.TYPE_REVERSE.status){
                userimage.setReversePhoto(userCodeImageIpPath + newFileName);
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
                                      @RequestParam MultipartFile file) {
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

            HcUserimage userimage = new HcUserimage();
            userimage.setUid(uid);

            String newFileName=PropertiesUtil.uploadImage(file,userPayImagePath);
            if(status == ImageEnum.TYPE_WECHAT.status){
                userimage.setWechatPhoto(userPayImageIpPath + newFileName);
            }else if(status == ImageEnum.TYPE_ALIPAY.status){
                userimage.setAlipayPhoto(userPayImageIpPath + newFileName);
            }

            HcUserimage imagePay = new HcUserimage();
            imagePay.setUid(uid);
            EntityWrapper<HcUserimage> wrapper = new EntityWrapper<>(imagePay);
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
                return new BaseResult(BaseResultEnum.ERROR.getStatus(), "上传失败!", false);
            }
        }catch (Exception e){
            return new BaseResult(BaseResultEnum.ERROR.getStatus(), "上传失败!", e.getMessage());
        }
    }


    @ApiOperation(value="支付认证" ,notes="参数：uid,alipayAccount,wechatAccount")
    @UserLog(module= LogConstant.HC_CERT, actionDesc = "保存用户的实名信息")
    @PostMapping("/saveUserAccountInfo")
    public BaseResult saveUserAccountInfo(@PathParam(value = "uid") Integer uid,
                                          @PathParam(value = "alipayAccount") String alipayAccount,
                                          @PathParam(value = "wechatAccount") String wechatAccount) {

        Integer status = 0;

        if(StringUtils.isNull(uid)){
            return  new BaseResult(BaseResultEnum.BLANK.status, "用户ID不能为空", false);
        }
        if((StringUtils.isNull(alipayAccount) && StringUtils.isNull(wechatAccount))){
            return  new BaseResult(BaseResultEnum.BLANK.status, "账号不能为空！", false);
        }
        if((StringUtils.isNotNull(alipayAccount) && StringUtils.isNotNull(wechatAccount))){
            status=ImageEnum.TYPE_ALIPAYANDWECHAT.status;
        }
        if(StringUtils.isNotNull(alipayAccount)){
            status=ImageEnum.TYPE_ALIPAY.status;
        }
        if(StringUtils.isNotNull(wechatAccount)){
            status=ImageEnum.TYPE_WECHAT.status;
        }
        return hcUserimageService.saveUserAccountInfo(uid,alipayAccount,wechatAccount,status);
    }
}
