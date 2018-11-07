package com.fmkj.user.dao.mapper;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.fmkj.user.dao.domain.HcAccount;
import com.fmkj.user.dao.dto.HcAccountDto;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface HcAccountMapper extends BaseMapper<HcAccount> {



    /**
     * @author yangshengbin`
     * @Description：查询最新一条中奖用户信息
     * @date 2018/9/4 0004 12:08
     * @param
     * @return
    */
    HashMap<String, Object> queryOneNewNotice();

    HcAccount queryUserTaskMessage(@Param("uid") Integer uid);

    List<HcAccount> selectAllFriends(Pagination pagination, @Param("uid") Integer uid);

    HcAccountDto selectAccountById(@Param("uid") Integer id);

    List<HcAccount> searchAccount(HashMap<String, Object> params);

    HashMap<String,Object> getAccountInfoByUid(@Param("uid") Integer uid);
}