package com.fmkj.race.server.service.impl;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.fmkj.common.annotation.BaseService;
import com.fmkj.common.base.BaseServiceImpl;
import com.fmkj.race.dao.domain.GcNotice;
import com.fmkj.race.dao.dto.NoticeQueryDto;
import com.fmkj.race.dao.mapper.GcNoticeMapper;
import com.fmkj.race.dao.queryVo.NoticeQueryPage;
import com.fmkj.race.server.service.GcNoticeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
* @Description: GcNotice Service实现
* @Author: yangshengbin
* @CreateDate: 2018/8/30.
* @Version: 1.0
**/
@Service
@Transactional
@BaseService
public class GcNoticeServiceImpl extends BaseServiceImpl<GcNoticeMapper, GcNotice> implements GcNoticeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GcNoticeServiceImpl.class);


    @Autowired
    private  GcNoticeMapper gcNoticeMapper;


    /**
     * @author yangshengbin
     * @Description：查询用户所有通知信息
     * @date 2018/9/3 0003 16:44
     * @param
     * @return java.util.List
    */
    public  List<NoticeQueryDto> queryGcNoticeByUid(Pagination pagination, NoticeQueryPage noticeQueryPage) {
        return gcNoticeMapper.queryGcNoticeByUid(pagination, noticeQueryPage);
    }




    /**查询最新一条中奖用户信息
     * @author yangshengbin
     * @Description：
     * @date 2018/9/4 0004 10:48
     * @param
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
    */
    public List<Map<String, Object>> queryOneNewNotice() {

        return gcNoticeMapper.queryOneNewNotice();
    }
}