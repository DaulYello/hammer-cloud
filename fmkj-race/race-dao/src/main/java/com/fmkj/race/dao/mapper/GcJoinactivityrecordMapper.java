package com.fmkj.race.dao.mapper;

import com.fmkj.race.dao.domain.GcJoinactivityrecord;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.fmkj.race.dao.dto.JoinActivityDto;
import com.fmkj.race.dao.queryVo.JoinActivityPage;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import java.util.List;

/**
 * <p>
 * 参与活动的记录表 Mapper 接口
 * </p>
 *
 * @author yangshengbin
 * @since 2018-09-05
 */
public interface GcJoinactivityrecordMapper extends BaseMapper<GcJoinactivityrecord> {
    /**
     * 活动参与记录
     * @author ru
     * @param
     * @return
     */
    List<JoinActivityDto>  queryJoinActivityByAid(Pagination page,@Param("gap") JoinActivityPage joinActivityPage);

    List<JoinActivityDto> queryJoinActivityList();
}
