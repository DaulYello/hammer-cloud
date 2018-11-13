package com.fmkj.chat.server.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.fmkj.chat.dao.domain.User;
import com.fmkj.chat.dao.domain.WebMessage;
import com.fmkj.chat.dao.queryVo.ChatQueryVo;
import com.fmkj.chat.server.service.HcAppchatService;
import com.fmkj.common.base.BaseResult;
import com.fmkj.common.base.BaseResultEnum;
import com.fmkj.common.util.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/chat")
@Api(tags ={ "聊天服务"},description = "聊天服务接口-网关路径/api-chat")
public class HcAppChatController {

    @Autowired
    private HcAppchatService hcAppchatService;

    @ApiOperation(value="分页查询聊天列表", notes="分页查询聊天列表")
    @PutMapping("/getChatPage")
    public BaseResult<Page<WebMessage>> getChatPage(@RequestBody ChatQueryVo queryVo){
        try {
            Page<WebMessage> tPage = buildPage(queryVo);
            List<WebMessage> list = hcAppchatService.getChatPage(tPage, queryVo);
            tPage.setRecords(list);
            return new BaseResult(BaseResultEnum.SUCCESS.getStatus(), "查询成功", tPage);
        } catch (Exception e) {
            throw new RuntimeException("分页查询聊天列表异常：" + e.getMessage());
        }
    }

    private Page<WebMessage> buildPage(ChatQueryVo queryVo) {
        Page<WebMessage> tPage =new Page<WebMessage>(queryVo.getPageNo(),queryVo.getPageSize());
        if(StringUtils.isNotEmpty(queryVo.getOrderBy())){
            tPage.setOrderByField(queryVo.getOrderBy());
            tPage.setAsc(false);
        }
        if(StringUtils.isNotEmpty(queryVo.getOrderByAsc())){
            tPage.setOrderByField(queryVo.getOrderByAsc());
            tPage.setAsc(true);
        }
        return tPage;
    }

}
