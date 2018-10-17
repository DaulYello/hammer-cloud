package com.fmkj.user.dao.mapper;

import com.fmkj.user.dao.domain.FmIntegralInfo;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.fmkj.user.dao.dto.CntRDto;

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
public interface FmIntegralInfoMapper extends BaseMapper<FmIntegralInfo> {

    List<FmIntegralInfo> queryRList(HashMap<String, Object> paramMap);

}
