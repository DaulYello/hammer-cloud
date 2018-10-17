package com.fmkj.user.dao.mapper;

import com.fmkj.user.dao.domain.FmCntInfo;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.fmkj.user.dao.domain.FmIntegralInfo;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author youxun
 * @since 2018-10-16
 */
public interface FmCntInfoMapper extends BaseMapper<FmCntInfo> {

    List<FmIntegralInfo> queryCNTList(HashMap<String, Object> paramMap);
}
