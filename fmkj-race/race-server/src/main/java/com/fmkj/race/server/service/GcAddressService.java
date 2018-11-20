package com.fmkj.race.server.service;

import com.fmkj.common.base.BaseService;
import com.fmkj.race.dao.domain.GcAddress;
import org.web3j.abi.datatypes.Bool;

import java.util.List;

/**
* @Description: GcAddress Service接口
* @Author: yangshengbin
* @CreateDate: 2018/9/3.
* @Version: 1.0
**/
public interface GcAddressService extends BaseService<GcAddress> {


    /**
     * 修改默认地址，参数：id,uid
     * @param gcAddress
     */
    Boolean updateAddressByStatus(GcAddress gcAddress);

    /**
     * 通过用户id查询用户的收货地址，重点是这里需要一个按时间降序排列的一个集合
     * @param address
     * @return
     */
    List<GcAddress> selectListByTimeOrder(GcAddress address);
}