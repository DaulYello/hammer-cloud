package com.fmkj.user.dao.mapper;

import com.fmkj.user.dao.domain.HcRcode;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author youxun
 * @since 2018-09-14
 */
public interface HcRcodeMapper extends BaseMapper<HcRcode> {

    List<HcRcode> selectRecodList();
}
