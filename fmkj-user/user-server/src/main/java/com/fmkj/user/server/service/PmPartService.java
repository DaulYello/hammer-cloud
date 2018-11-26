package com.fmkj.user.server.service;

import com.fmkj.common.base.BaseService;
import com.fmkj.user.dao.domain.PmPart;

import java.util.HashMap;
import java.util.List;

/**
* @Description: PmPart Service接口
* @Author: youxun
* @CreateDate: 2018/11/21.
* @Version: 1.0
**/
public interface PmPartService extends BaseService<PmPart> {

    List queryPartDynamic();

    HashMap queryPartByUid(Integer uid, Integer tid);

    HashMap queryAuditByPartId(Integer id);
}