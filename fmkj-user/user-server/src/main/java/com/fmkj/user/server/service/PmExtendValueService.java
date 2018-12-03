package com.fmkj.user.server.service;

import com.fmkj.common.base.BaseService;
import com.fmkj.user.dao.domain.PmExtendValue;

import java.util.List;

/**
* @Description: PmExtendValue Service接口
* @Author: youxun
* @CreateDate: 2018/11/27.
* @Version: 1.0
**/
public interface PmExtendValueService extends BaseService<PmExtendValue> {

    List queryExtendList(Integer partId);
}