package com.fmkj.race.dao.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.fmkj.race.dao.domain.GcNotice;
import com.fmkj.race.dao.dto.NoticeQueryDto;
import com.fmkj.race.dao.queryVo.NoticeQueryPage;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户消息表 Mapper 接口
 * </p>
 *
 * @author yangshengbin
 * @since 2018-08-30
 */
public interface GcNoticeMapper extends BaseMapper<GcNotice> {

    /**
     * @author yangshengbin
     * @Description：查询用户所有通知信息
     * @date 2018/9/3 0003 16:46
     * @param
     * @return List
    */
    List<NoticeQueryDto> queryGcNoticeByUid(NoticeQueryPage  noticeQueryPage);



    /**
     * @author yangshengbin
     * @Description：查询最新一条中奖用户信息
     * @date 2018/9/4 0004 10:48
     * @param
     * @return
    */
    List<Map<String,Object>> queryOneNewNotice();
}
