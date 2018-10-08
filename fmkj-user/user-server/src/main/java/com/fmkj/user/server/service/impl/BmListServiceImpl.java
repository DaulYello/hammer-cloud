package com.fmkj.user.server.service.impl;

import com.fmkj.common.base.BaseService;
import com.fmkj.common.base.BaseServiceImpl;
import com.fmkj.user.dao.domain.BmList;
import com.fmkj.user.dao.mapper.BmListMapper;
import com.fmkj.user.server.service.BmListService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ Author     ：yangshengbin
 * @ Date       ：17:33 2018/8/29 0029
 * @ Description：用户黑白名单业务接口实现类
 */
@Service
@Transactional
public class BmListServiceImpl extends BaseServiceImpl<BmListMapper, BmList> implements BmListService {

    //日志
    private static final Logger LOGGER = LoggerFactory.getLogger(BmListServiceImpl.class);
}
