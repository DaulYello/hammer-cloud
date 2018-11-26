package com.fmkj.user.server.service.impl;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.fmkj.common.annotation.BaseService;
import com.fmkj.common.base.BaseServiceImpl;
import com.fmkj.user.dao.mapper.PmTaskMapper;
import com.fmkj.user.dao.domain.PmTask;
import com.fmkj.user.server.service.PmTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

/**
* @Description: PmTask Service实现
* @Author: youxun
* @CreateDate: 2018/11/21.
* @Version: 1.0
**/
@Service
@Transactional
@BaseService
public class PmTaskServiceImpl extends BaseServiceImpl<PmTaskMapper, PmTask> implements PmTaskService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PmTaskServiceImpl.class);

    @Autowired
    private PmTaskMapper pmTaskMapper;

    @Override
    public List queryHotTaskPage(Pagination tPage) {
        return pmTaskMapper.queryHotTaskPage(tPage);
    }

    @Override
    public List queryNewTaskPage(Pagination tPage) {
        return pmTaskMapper.queryNewTaskPage(tPage);
    }

    @Override
    public HashMap queryTaskById(Integer id) {
        return pmTaskMapper.queryTaskById(id);
    }

}