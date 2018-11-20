package com.fmkj.race.dao.mapper;

import com.fmkj.race.dao.domain.GcAddress;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 用户地址表 Mapper 接口
 * </p>
 *
 * @author yangshengbin
 * @since 2018-09-03
 */
public interface GcAddressMapper extends BaseMapper<GcAddress> {

    List<GcAddress> selectListByTimeOrder(GcAddress address);
}
