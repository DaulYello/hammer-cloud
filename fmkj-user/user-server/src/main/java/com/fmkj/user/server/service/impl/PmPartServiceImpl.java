package com.fmkj.user.server.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fmkj.common.annotation.BaseService;
import com.fmkj.common.base.BaseServiceImpl;
import com.fmkj.user.dao.domain.PmExtend;
import com.fmkj.user.dao.domain.PmExtendValue;
import com.fmkj.user.dao.domain.PmPart;
import com.fmkj.user.dao.mapper.PmExtendValueMapper;
import com.fmkj.user.dao.mapper.PmPartMapper;
import com.fmkj.user.server.service.PmPartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
* @Description: PmPart Service实现
* @Author: youxun
* @CreateDate: 2018/11/21.
* @Version: 1.0
**/
@Service
@Transactional
@BaseService
public class PmPartServiceImpl extends BaseServiceImpl<PmPartMapper, PmPart> implements PmPartService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PmPartServiceImpl.class);

    @Autowired
    private PmPartMapper pmPartMapper;

    @Autowired
    private PmExtendValueMapper pmExtendValueMapper;

    @Override
    public List queryPartDynamic() {
        return pmPartMapper.queryPartDynamic();
    }

    @Override
    public HashMap queryPartByUid(Integer uid, Integer tid) {
        return pmPartMapper.queryPartByUid(uid, tid);
    }

    @Override
    public HashMap queryAuditByPartId(Integer id) {
        return pmPartMapper.queryAuditByPartId(id);
    }

    @Override
    public boolean partImmediately(PmPart pmPart, List<PmExtend> pmExtendList) {
        int row = pmPartMapper.insert(pmPart);
        if(row > 0){
            List<PmExtendValue> batchList = new ArrayList<>();
            for(PmExtend pm : pmExtendList){
                PmExtendValue pmExtendValue = new PmExtendValue();
                pmExtendValue.setPid(pmPart.getId());
                pmExtendValue.setXid(pm.getId());
                pmExtendValue.setCreateDate(new Date());
                batchList.add(pmExtendValue);
            }
            int batchRow = pmExtendValueMapper.batchInsert(batchList);
            if(batchRow > 0){
                return true;
            }else{
                throw new RuntimeException("立即参与失败");
            }
        }
        return false;
    }

    @Override
    public boolean submitAudit(PmPart pmPart, String extendJsonStr) {
        LOGGER.info("提交审核参数：" + extendJsonStr);
        int row = pmPartMapper.updateById(pmPart);
        if(row > 0){
            List extendList = pmExtendValueMapper.queryExtendList(pmPart.getId());
            JSONObject jsonObject = JSON.parseObject(extendJsonStr);
            for(int i = 0; i < extendList.size(); i++){
                HashMap<String, Object> exMap = (HashMap<String, Object>) extendList.get(i);
                String clounmKey = (String) exMap.get("clounmKey");
                String clounmValue = (String) jsonObject.get(clounmKey);
                int id = (Integer)exMap.get("id");
                PmExtendValue pmExtendValue = pmExtendValueMapper.selectById(id);
                pmExtendValue.setXvalue(clounmValue);
                int update = pmExtendValueMapper.updateById(pmExtendValue);
                LOGGER.info("提交审核保存字段名：" + clounmKey);
                LOGGER.info("提交审核保存返回结果：" + update);
            }
            return true;
        }
        return false;
    }


}