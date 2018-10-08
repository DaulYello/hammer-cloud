package com.fmkj.user.server.service;

import com.fmkj.common.base.BaseService;
import com.fmkj.user.dao.domain.HcAssets;

/**
* @Description: HcAssets Service接口
* @Author: youxun
* @CreateDate: 2018/9/21.
* @Version: 1.0
**/
public interface HcAssetsService extends BaseService<HcAssets> {


    boolean updateAssetsContent(HcAssets assets);

}