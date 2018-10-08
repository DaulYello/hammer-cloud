package com.fmkj.race.dao.mapper;

import com.fmkj.race.dao.domain.GcPremium;
import com.baomidou.mybatisplus.mapper.BaseMapper;


/**
 * <p>
 * 溢价区间表 Mapper 接口
 * </p>
 *
 * @author ru
 * @since 2018-09-03
 */
public interface GcPremiumMapper extends BaseMapper<GcPremium> {
    GcPremium getPremiumByIntegral(Integer integral);

}
