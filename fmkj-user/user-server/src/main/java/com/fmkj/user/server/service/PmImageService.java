package com.fmkj.user.server.service;

import com.fmkj.common.base.BaseService;
import com.fmkj.user.dao.domain.PmImage;

import java.util.List;

/**
* @Description: PmImage Service接口
* @Author: youxun
* @CreateDate: 2018/11/21.
* @Version: 1.0
**/
public interface PmImageService extends BaseService<PmImage> {

    List<PmImage> selectImageListByPartId(Integer strategyId, Integer imageType);
}