package com.fmkj.user.dao.mapper;

import com.fmkj.user.dao.domain.PmExtendValue;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author youxun
 * @since 2018-11-27
 */
public interface PmExtendValueMapper extends BaseMapper<PmExtendValue> {

    int batchInsert(List<PmExtendValue> batchList);

    List queryExtendList(@Param("pid") Integer pid);
}
