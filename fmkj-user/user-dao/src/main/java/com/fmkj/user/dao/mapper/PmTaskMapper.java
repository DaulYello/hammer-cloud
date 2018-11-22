package com.fmkj.user.dao.mapper;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.fmkj.user.dao.domain.PmTask;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author youxun
 * @since 2018-11-21
 */
public interface PmTaskMapper extends BaseMapper<PmTask> {

    List queryHotTaskPage(Pagination tPage);

    List queryNewTaskPage(Pagination tPage);

    HashMap queryTaskById(@Param("id") Integer id);
}
