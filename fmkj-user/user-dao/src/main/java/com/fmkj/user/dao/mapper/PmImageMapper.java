package com.fmkj.user.dao.mapper;

import com.fmkj.user.dao.domain.PmImage;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author youxun
 * @since 2018-11-21
 */
public interface PmImageMapper extends BaseMapper<PmImage> {

    List<PmImage> selectImageListByPartId(@Param("partId") Integer partId);
}
