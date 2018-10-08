package com.fmkj.race.server.service.impl;

import com.fmkj.common.annotation.BaseService;
import com.fmkj.common.base.BaseServiceImpl;
import com.fmkj.race.dao.mapper.GcPimageMapper;
import com.fmkj.race.dao.domain.GcPimage;
import com.fmkj.race.dao.queryVo.GcBaseModel;
import com.fmkj.race.server.service.GcPimageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* @Description: GcPimage Service实现
* @Author: yangshengbin
* @CreateDate: 2018/8/30.
* @Version: 1.0
**/
@Service
@Transactional
@BaseService
public class GcPimageServiceImpl extends BaseServiceImpl<GcPimageMapper, GcPimage> implements GcPimageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GcPimageServiceImpl.class);

    @Autowired
    private  GcPimageMapper gcPimageMapper;

    /**
     * @author yangshengbin
     * @Description：传入活动id查询活动产品的所有图片
     * @date 2018/9/4 0004 16:19
     * @param gcBaseModel
     * @return java.util.List<com.fmkj.race.dao.domain.GcPimage>
    */
    public List<GcPimage> queryActivityImageById(GcBaseModel gcBaseModel) {
        return gcPimageMapper.queryActivityImageById(gcBaseModel);
    }
}