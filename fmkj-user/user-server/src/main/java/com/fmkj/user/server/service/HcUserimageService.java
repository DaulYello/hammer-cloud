package com.fmkj.user.server.service;

import com.fmkj.common.base.BaseResult;
import com.fmkj.common.base.BaseService;
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
     * @param id
     * @param name
     * @param cardnum
     * @return
     */
    BaseResult saveUserRealInfo(Integer id, String name, String cardnum);

    /**
     *
     * @param uid
     * @param alipayAccount
     * @param wechatAccount
     * @param type
     * @return
     */
    BaseResult saveUserAccountInfo(Integer uid, String alipayAccount, String wechatAccount,Integer type);

}