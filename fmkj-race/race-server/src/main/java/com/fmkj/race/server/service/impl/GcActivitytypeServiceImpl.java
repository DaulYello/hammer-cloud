package com.fmkj.race.server.service.impl;

import com.fmkj.common.annotation.BaseService;
import com.fmkj.common.base.BaseServiceImpl;
import com.fmkj.race.dao.mapper.GcActivitytypeMapper;
import com.fmkj.race.dao.domain.GcActivitytype;
import com.fmkj.race.server.service.GcActivitytypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* @Description: GcActivitytype Service实现
* @Author: yangshengbin
* @CreateDate: 2018/8/31.
* @Version: 1.0
**/
@Service
@Transactional
@BaseService
public class GcActivitytypeServiceImpl extends BaseServiceImpl<GcActivitytypeMapper, GcActivitytype> implements GcActivitytypeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GcActivitytypeServiceImpl.class);

    @Autowired
    private GcActivitytypeMapper gcActivitytypeMapper;



    /**
     * @author yangshengbin
     * @Description：查询所有活动类型
     * @date 2018/9/4 0004 16:37
     * @param
     * @return java.util.List<com.fmkj.race.dao.domain.GcActivitytype>
    */
    public List<GcActivitytype> queryAllActivityType() {
        return gcActivitytypeMapper.queryAllActivityType();
    }
}