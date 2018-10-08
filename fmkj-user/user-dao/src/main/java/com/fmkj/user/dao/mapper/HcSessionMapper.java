
package com.fmkj.user.dao.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.fmkj.user.dao.domain.HcSession;

/**
* @Description:  session持久层接口
* @Param:
* @return:
* @Author: 杨胜彬
* @Date: 2018/8/27 0027
*/

public interface HcSessionMapper extends BaseMapper<HcSession> {

    /**
    * @Description:  根据uid查询用户session状态
    * @Param:  uid
    * @return:   int
    * @Author: 杨胜彬
    * @Date: 2018/8/28 0028
    */

    int queryHcSessionByUid(Integer uid);



    /**
    * @Description:  更新session表
    * @Param:  hcSession
    * @return:  int
    * @Author: 杨胜彬
    * @Date: 2018/8/29 0029
    */
    int updateHcSession(HcSession hcSession);
}

