package com.fmkj.user.dao.mapper;

import com.fmkj.user.dao.domain.PmPart;
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
public interface PmPartMapper extends BaseMapper<PmPart> {

    List queryPartDynamic();

    HashMap queryPartByUid(@Param("uid") Integer uid, @Param("tid") Integer tid);

    HashMap queryAuditByPartId(@Param("id") Integer id);
}
