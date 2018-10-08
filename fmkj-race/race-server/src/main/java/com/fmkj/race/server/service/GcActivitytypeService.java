package com.fmkj.race.server.service;

import com.fmkj.common.base.BaseService;
import com.fmkj.race.dao.domain.GcActivitytype;
import com.fmkj.race.dao.domain.GcPimage;

import java.util.List;

/**
* @Description: GcActivitytype Service接口
* @Author: yangshengbin
* @CreateDate: 2018/8/31.
* @Version: 1.0
**/
public interface GcActivitytypeService extends BaseService<GcActivitytype> {


    /**
     * @author yangshengbin
     * @Description：查询所有活动类型
     * @date 2018/9/4 0004 16:34
     * @param
     * @return
    */
    List<GcActivitytype> queryAllActivityType();
}