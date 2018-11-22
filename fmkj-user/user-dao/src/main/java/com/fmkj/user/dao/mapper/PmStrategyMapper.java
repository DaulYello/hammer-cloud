package com.fmkj.user.dao.mapper;

import com.fmkj.user.dao.domain.PmStrategy;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author youxun
 * @since 2018-11-21
 */
public interface PmStrategyMapper extends BaseMapper<PmStrategy> {

    List queryStrategyByTid(@Param("tid") Integer tid);

    List queryPromptByTid(@Param("tid") Integer tid);
}
