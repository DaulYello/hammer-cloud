package com.fmkj.user.dao.mapper;

import com.fmkj.user.dao.domain.FmRecyleLog;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author youxun
 * @since 2018-10-16
 */
public interface FmRecyleLogMapper extends BaseMapper<FmRecyleLog> {

    List queryCntDynamic(Integer uid);

    List queryRankList();

    Double queryRNumDay(Integer uid);

    Double queryCntNumDay(Integer uid);

    void batchAddRecyleLog(List<FmRecyleLog> recyleLogs);

    Double queryInviteRankCnt(Integer uid);
}
