package com.fmkj.user.server.service;

import com.fmkj.common.base.BaseService;
import com.fmkj.user.dao.domain.HcRcode;

import java.util.List;

/**
* @Description: HcRcode Service接口
* @Author: youxun
* @CreateDate: 2018/9/14.
* @Version: 1.0
**/
public interface HcRcodeService extends BaseService<HcRcode> {

    List<HcRcode> selectRecodList();
}