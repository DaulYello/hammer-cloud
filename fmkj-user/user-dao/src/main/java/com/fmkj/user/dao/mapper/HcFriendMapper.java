package com.fmkj.user.dao.mapper;

import com.fmkj.user.dao.domain.HcFriend;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author youxun
 * @since 2018-09-19
 */
public interface HcFriendMapper extends BaseMapper<HcFriend> {

    Integer passFReq(HcFriend hcFriend);
}
