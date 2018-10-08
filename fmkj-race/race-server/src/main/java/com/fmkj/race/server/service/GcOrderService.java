package com.fmkj.race.server.service;

import com.fmkj.common.base.BaseService;
import com.fmkj.race.dao.domain.GcOrder;

/**
* @Description: GcOrder Service接口
* @Author: yangshengbin
* @CreateDate: 2018/8/31.
* @Version: 1.0
**/
public interface GcOrderService extends BaseService<GcOrder> {

    /**
     * @author yangshengbin
     * @Description：确认发货
     * @date 2018/8/31 0031 15:40
     * @param
     * @return
    */
    boolean sendGoodsByExample(GcOrder gcOrder);



    /**
     * @author yangshengbin
     * @Description：确认收货
     * @date 2018/8/31 0031 17:20
     * @param
     * @return
    */
    boolean collectGoods(GcOrder order);
}