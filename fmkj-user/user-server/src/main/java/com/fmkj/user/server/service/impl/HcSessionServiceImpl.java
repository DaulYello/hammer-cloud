
package com.fmkj.user.server.service.impl;

/**
 * @program: fmkj
 * @description: session业务接口实现类
 * @author: 杨胜彬
 * @create: 2018-08-27 15:15
 **/

import com.fmkj.common.base.BaseServiceImpl;
import com.fmkj.user.dao.domain.HcSession;
import com.fmkj.user.dao.mapper.HcSessionMapper;
import com.fmkj.user.server.service.HcSessionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public  class HcSessionServiceImpl extends BaseServiceImpl<HcSessionMapper, HcSession> implements HcSessionService {

    //日志
    private static final Logger LOGGER = LoggerFactory.getLogger(HcSessionServiceImpl.class);

    @Autowired
    private HcSessionMapper hcSessionMapper;


/**
    * @Description: 判断token状态
     * @Param:  token
    * @return:  boolean
    * @Author: 杨胜彬
    * @Date: 2018/8/27 0027
    */


    @Override
    public boolean userIsExpire(String token) {
        return false;
    }





/**
    * @Description: 根据uid查询用户session状态
    * @Param:
    * @return:
    * @Author: 杨胜彬
    * @Date: 2018/8/28 0028
    */

    @Override
    public int queryHcSessionByUid(Integer uid) {
        int result;
        try {
            result = hcSessionMapper.queryHcSessionByUid(uid);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        return result;
    }



}

