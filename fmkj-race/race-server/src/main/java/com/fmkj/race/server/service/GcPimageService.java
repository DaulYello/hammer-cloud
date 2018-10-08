package com.fmkj.race.server.service;

import com.fmkj.common.base.BaseService;
import com.fmkj.race.dao.domain.GcPimage;
import com.fmkj.race.dao.queryVo.GcBaseModel;

import java.util.List;

/**
* @Description: GcPimage Service接口
* @Author: yangshengbin
* @CreateDate: 2018/8/30.
* @Version: 1.0
**/
public interface GcPimageService extends BaseService<GcPimage> {


    /**
     * @author yangshengbin
     * @Description：传入活动id查询活动产品的所有图片
     * @date 2018/9/4 0004 16:19
     * @param gcBaseModel
     * @return
    */
    List<GcPimage> queryActivityImageById(GcBaseModel gcBaseModel);
}