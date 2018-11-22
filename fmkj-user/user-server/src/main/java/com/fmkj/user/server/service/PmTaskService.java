package com.fmkj.user.server.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.fmkj.common.base.BaseService;
import com.fmkj.user.dao.domain.PmTask;

import java.util.HashMap;
import java.util.List;

/**
* @Description: PmTask Service接口
* @Author: youxun
* @CreateDate: 2018/11/21.
* @Version: 1.0
**/
public interface PmTaskService extends BaseService<PmTask> {

    List queryHotTaskPage(Pagination tPage);

    List queryNewTaskPage(Pagination tPage);

    HashMap queryTaskById(Integer id);

}