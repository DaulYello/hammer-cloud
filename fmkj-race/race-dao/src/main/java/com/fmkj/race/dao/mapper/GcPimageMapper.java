package com.fmkj.race.dao.mapper;

import com.fmkj.race.dao.domain.GcPimage;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.fmkj.race.dao.queryVo.GcBaseModel;

import java.util.List;

/**
 * <p>
 * 产品图片表 Mapper 接口
 * </p>
 *
 * @author yangshengbin
 * @since 2018-08-30
 */
public interface GcPimageMapper extends BaseMapper<GcPimage> {


    /**
     * @author yangshengbin
     * @Description：传入活动id查询活动产品的所有图片
     * @date 2018/9/4 0004 16:21
     * @param gcBaseModel
     * @return
    */
    List<GcPimage> queryActivityImageById(GcBaseModel gcBaseModel);
}
