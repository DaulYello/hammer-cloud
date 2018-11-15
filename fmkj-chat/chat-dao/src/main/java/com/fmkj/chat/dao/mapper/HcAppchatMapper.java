package com.fmkj.chat.dao.mapper;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.fmkj.chat.dao.domain.HcAppchat;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.fmkj.chat.dao.domain.User;
import com.fmkj.chat.dao.domain.WebMessage;
import com.fmkj.chat.dao.queryVo.ChatQueryVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author youxun
 * @since 2018-11-13
 */
public interface HcAppchatMapper extends BaseMapper<HcAppchat> {

    User queryUserInfo(@Param("uid") Integer sendId);

    List<WebMessage> queryChatPage(Pagination pagination, @Param("chat") ChatQueryVo queryVo);

    boolean updateChatStatus(@Param("chat") ChatQueryVo queryVo);

    List queryNewsCaht(@Param("chat") ChatQueryVo queryVo);

    List queryNewActivity();

    List queryNewsRaceInfo(@Param("chat") ChatQueryVo queryVo);

    List queryNewsOrder(@Param("chat") ChatQueryVo queryVo);

    List queryApplyList(@Param("chat") ChatQueryVo queryVo);

    List queryRefuseList(@Param("chat") ChatQueryVo queryVo);
}
