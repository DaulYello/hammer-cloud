package com.fmkj.user.server.service;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.fmkj.common.base.BaseService;
import com.fmkj.user.dao.domain.HcAccount;
import com.fmkj.user.dao.dto.HcAccountDto;

import java.util.List;
import java.util.Map;

/**
* @Description:
* @Author: youxun
* @Version: 1.0
**/
public interface HcAccountService extends BaseService<HcAccount> {


    /**
     * @author yangshengbin
     * @Description：查询最新一条中奖用户信息
     * @date 2018/9/5 0005 10:08
     * @param
     * @return 
    */
    List<Map<String, Object>> queryOneNewNotice();

    boolean bindEmail(HcAccount ha);

    HcAccount queryUserTaskMessage(Integer uid);

    List<HcAccount> queryAllFriends(Pagination pagination, Integer accountId);

    void uploadUserHead(HcAccount hcAccount, String fileName, String path);

    HcAccountDto selectAccountById(Integer id);

    boolean loginByRcodeAndPhone(HcAccount ha, Integer uid);

    boolean loginByTelephone(Integer id);
}