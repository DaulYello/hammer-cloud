package com.fmkj.user.server.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.fmkj.common.annotation.BaseService;
import com.fmkj.common.base.BaseResult;
import com.fmkj.common.base.BaseResultEnum;
import com.fmkj.common.base.BaseServiceImpl;
import com.fmkj.common.comenum.PointEnum;
import com.fmkj.common.util.PropertiesUtil;
import com.fmkj.common.util.StringUtils;
import com.fmkj.user.dao.domain.HcAccount;
import com.fmkj.user.dao.domain.HcPointsRecord;
import com.fmkj.user.dao.domain.HcUserimage;
import com.fmkj.user.dao.domain.UserRealInfo;
import com.fmkj.user.dao.mapper.HcAccountMapper;
import com.fmkj.user.dao.mapper.HcPointsRecordMapper;
import com.fmkj.user.dao.mapper.HcUserimageMapper;
import com.fmkj.user.server.service.HcAccountService;
import com.fmkj.user.server.service.HcUserimageService;
import com.fmkj.user.server.util.JDWXUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

/**
* @Description: HcUserimage Service实现
* @Author: youxun
* @CreateDate: 2018/9/18.
* @Version: 1.0
**/
@Service
@Transactional
@BaseService
public class HcUserimageServiceImpl extends BaseServiceImpl<HcUserimageMapper, HcUserimage> implements HcUserimageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HcUserimageServiceImpl.class);

    @Autowired
    private HcUserimageMapper hcUserimageMapper;
    @Autowired
    private HcAccountMapper hcAccountMapper;
    @Autowired
    private HcPointsRecordMapper pointsRecordMapper;

    @Autowired
    private JDWXUtil jdwxUtil;

    public BaseResult saveUserRealInfo(HcAccount account){
        //1.更新hc_account
        try {
            account.setCardStatus(1);
            //1.1 验证用户名和省份证号码是否正确
            boolean result = jdwxUtil.cardRealName(account);
            LOGGER.debug("验证用户名和省份证号码是否正确:"+result);
            if(result) {
                boolean updateResult =hcAccountMapper.updateById(account)>0?true:false;
                if(updateResult){
                    return new BaseResult(BaseResultEnum.SUCCESS.getStatus(), "保存用户的实名信息成功!", true);
                }else{
                    return new BaseResult(BaseResultEnum.ERROR.getStatus(), "更新用户的姓名、身份证号以及状态失败!", false);
                }
            }else{
                return new BaseResult(BaseResultEnum.ERROR.getStatus(), "验证用户名和省份证号码失败!", false);
            }
        }catch (Exception e){
            throw new RuntimeException();
        }
    }

    public BaseResult saveUserAccountInfo(HcUserimage userimage,Integer type){

        try{
            HcUserimage imagePay = new HcUserimage();
            imagePay.setUid(userimage.getUid());
            HcUserimage hcUserimage = hcUserimageMapper.selectOne(imagePay);
            boolean result = false;
            if(StringUtils.isNull(hcUserimage)){
                return new BaseResult(BaseResultEnum.ERROR.getStatus(), "用户还没有实名认证，请先实名认证！", false);
            }else{
                hcUserimage.setAlipayAccount(userimage.getAlipayAccount());
                hcUserimage.setWechatAccount(userimage.getAlipayAccount());
                hcUserimage.setPayCertTime(new Date());
                hcUserimage.setStatus(1);
                result = hcUserimageMapper.updateById(hcUserimage) >0 ?true:false;
            }
            if(result){
                HcPointsRecord pointsRecord = new HcPointsRecord();
                pointsRecord.setUid(userimage.getUid());
                pointsRecord.setTime(new Date());
                switch(type){
                    case 3:
                        pointsRecord.setPointsId(PointEnum.BIND_WEBCHAT_PAY.pointId);
                        pointsRecord.setPointsNum(PointEnum.BIND_WEBCHAT_PAY.pointNum);
                        pointsRecordMapper.insert(pointsRecord);
                        break;
                    case 4:
                        pointsRecord.setPointsId(PointEnum.BIND_ALIPAY.pointId);
                        pointsRecord.setPointsNum(PointEnum.BIND_ALIPAY.pointNum);
                        pointsRecordMapper.insert(pointsRecord);
                        break;
                    case 5:
                        pointsRecord.setPointsId(PointEnum.BIND_WEBCHAT_PAY.pointId);
                        pointsRecord.setPointsNum(PointEnum.BIND_WEBCHAT_PAY.pointNum);
                        pointsRecordMapper.insert(pointsRecord);
                        pointsRecord.setPointsId(PointEnum.BIND_ALIPAY.pointId);
                        pointsRecord.setPointsNum(PointEnum.BIND_ALIPAY.pointNum);
                        pointsRecordMapper.insert(pointsRecord);
                        break;
                    default :
                }
                return new BaseResult(BaseResultEnum.SUCCESS.getStatus(), "绑定成功！", false);
            }else{
                return new BaseResult(BaseResultEnum.ERROR.getStatus(), "更新支付信息失败！", false);
            }
        }catch (Exception e){
            return new BaseResult(BaseResultEnum.ERROR.getStatus(), e.getMessage(), false);
        }
    }
}