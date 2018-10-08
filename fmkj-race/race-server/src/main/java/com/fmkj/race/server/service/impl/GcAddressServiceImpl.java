package com.fmkj.race.server.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.fmkj.common.annotation.BaseService;
import com.fmkj.common.base.BaseServiceImpl;
import com.fmkj.race.dao.mapper.GcAddressMapper;
import com.fmkj.race.dao.domain.GcAddress;
import com.fmkj.race.server.service.GcAddressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* @Description: GcAddress Service实现
* @Author: yangshengbin
* @CreateDate: 2018/9/3.
* @Version: 1.0
**/
@Service
@Transactional
@BaseService
public class GcAddressServiceImpl extends BaseServiceImpl<GcAddressMapper, GcAddress> implements GcAddressService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GcAddressServiceImpl.class);

    @Autowired
    private GcAddressMapper gcAddressMapper;

    /**
     * 修改默认地址，参数：id,uid
     * @param gcAddress
     * @return
     */
    @Override
    public Boolean updateAddressByStatus(GcAddress gcAddress) {

        /***********将用户所有地址重置为0************/
        GcAddress gcAddress1 = new GcAddress();
        gcAddress1.setStatus(0);
        EntityWrapper<GcAddress> wrapper = new EntityWrapper<GcAddress>();
        GcAddress gcAddress2 = new GcAddress();
        gcAddress2.setUid(gcAddress.getUid());
        wrapper.setEntity(gcAddress2);
        try {
            gcAddressMapper.update(gcAddress1,wrapper);
        } catch (Exception e) {
            throw new RuntimeException("修改异常：" + e.getMessage());
        }
        /***********将用户所有地址重置为0************/

        //修改默认地址为1
        gcAddress.setStatus(1);
        try {
            gcAddressMapper.updateById(gcAddress);
        } catch (Exception e) {
            throw new RuntimeException("修改异常：" + e.getMessage());
        }
        return  true;
    }
}