package com.fmkj.user.server.service;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.fmkj.common.base.BaseService;
import com.fmkj.user.dao.domain.HcAccount;
import com.fmkj.user.dao.dto.HcAccountDto;

import java.util.HashMap;
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
    HashMap<String, Object> queryOneNewNotice();

    boolean bindEmail(HcAccount ha);

    HcAccount queryUserTaskMessage(Integer uid);

    List<HcAccount> queryAllFriends(Pagination pagination, Integer accountId);

    boolean uploadUserHead(HcAccount hcAccount, String fileName, String path);

    HcAccountDto selectAccountById(Integer id);

    int loginByRcodeAndPhone(HcAccount ha, Integer uid, String token);

    boolean loginByTelephone(Integer id, String token);

    boolean updateUserP(HcAccount account, double par);

    Boolean grantUserP(HcAccount account, Double starterCnt);

    int queryActivitNum(Integer id);

    boolean granCredites(Double par, List<Integer> uids);

    List<HcAccount> searchAccount(HashMap<String, Object> params);

    HashMap<String,Object> getAccountInfoByUid(Integer id);
}