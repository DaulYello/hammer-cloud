package com.fmkj.race.server.service;

import com.fmkj.common.base.BaseService;
import com.fmkj.race.dao.domain.GcAddress;
import org.web3j.abi.datatypes.Bool;

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
}