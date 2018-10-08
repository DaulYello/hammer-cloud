package com.fmkj.race.dao.mapper;

import com.fmkj.race.dao.domain.GcActivitytype;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 活动（竟锤）类型表 Mapper 接口
 * </p>
 *
 * @author 杨胜彬
 * @since 2018-09-04
 */
public interface GcActivitytypeMapper extends BaseMapper<GcActivitytype> {


    /**
     * @author yangshengbin
     * @Description：查询所有活动类型
     * @date 2018/9/4 0004 16:38
     * @param
     * @return
    */
    List<GcActivitytype> queryAllActivityType();
}
