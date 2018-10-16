package com.fmkj.user.server.service;

import com.fmkj.common.base.BaseResult;
import com.fmkj.common.base.BaseService;
import com.fmkj.user.dao.domain.HcAccount;
import com.fmkj.user.dao.domain.HcUserimage;
import org.springframework.web.multipart.MultipartFile;

/**
* @Description: HcUserimage Service接口
* @Author: youxun
* @CreateDate: 2018/9/18.
* @Version: 1.0
**/
public interface HcUserimageService extends BaseService<HcUserimage> {


    /**
     *
     * @param account
     * @return
     */
    BaseResult saveUserRealInfo(HcAccount account);

    /**
     * @param userimage
     * @param type
     * @return
     */
    BaseResult saveUserAccountInfo(HcUserimage userimage,Integer type);


    HcUserimage getUserPayWay(HcUserimage userimage);

}