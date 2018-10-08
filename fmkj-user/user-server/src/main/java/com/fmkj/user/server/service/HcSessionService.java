
package com.fmkj.user.server.service;

import com.fmkj.common.base.BaseService;
import com.fmkj.user.dao.domain.HcSession;

/**
* @Description:  session业务接口
* @Param:
* @return:
* @Author: 杨胜彬
* @Date: 2018/8/27 0027
*/

public interface HcSessionService extends BaseService<HcSession> {

/*
    *//**
    * @Description: 判断token状态 
    * @Param:  token
    * @return:  boolean
    * @Author: 杨胜彬 
    * @Date: 2018/8/27 0027 
    */

    boolean userIsExpire(String token);



    /**
    * @Description:  根据uid查询用户session状态
    * @Param:
    * @return:
    * @Author: 杨胜彬
    * @Date: 2018/8/28 0028
    */
    int queryHcSessionByUid(Integer uid);
}

