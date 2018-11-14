package com.fmkj.chat.server.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.fmkj.chat.dao.domain.HcAppchat;
import com.fmkj.chat.dao.domain.WebMessage;
import com.fmkj.chat.dao.queryVo.ChatQueryVo;
import com.fmkj.chat.server.service.HcAppchatService;
import com.fmkj.common.base.BaseApiService;
import com.fmkj.common.base.BaseController;
import com.fmkj.common.base.BaseResult;
import com.fmkj.common.base.BaseResultEnum;
import com.fmkj.common.util.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat")
@Api(tags ={ "聊天服务"},description = "聊天服务接口-网关路径/api-chat")
public class HcAppChatController extends BaseController<HcAppchat, HcAppchatService> implements BaseApiService<HcAppchat> {

    @Autowired
    private HcAppchatService hcAppchatService;

    @ApiOperation(value="分页查询聊天列表", notes="分页查询聊天列表")
    @PutMapping("/queryChatPage")
    public BaseResult<Page<WebMessage>> queryChatPage(@RequestBody ChatQueryVo queryVo){
        try {
            if(StringUtils.isNull(queryVo) || queryVo.getUid() == null || queryVo.getChatId() == null){
                return new BaseResult(BaseResultEnum.BLANK.getStatus(), "UID或CHATID不能为空", false);
            }
            Page<WebMessage> tPage = buildPage(queryVo);
            List<WebMessage> list = hcAppchatService.getChatPage(tPage, queryVo);
            tPage.setRecords(list);
            return new BaseResult(BaseResultEnum.SUCCESS.getStatus(), "查询成功", tPage);
        } catch (Exception e) {
            throw new RuntimeException("分页查询聊天列表异常：" + e.getMessage());
        }
    }

    @ApiOperation(value="修改已读状态", notes="修改已读状态， uid, chatId")
    @PostMapping("/updateChatStatus")
    public BaseResult updateChatStatus(@RequestBody ChatQueryVo queryVo){
        try {
            if(StringUtils.isNull(queryVo) || queryVo.getUid() == null || queryVo.getChatId() == null){
                return new BaseResult(BaseResultEnum.BLANK.getStatus(), "UID或CHATID不能为空", false);
            }

            boolean result = hcAppchatService.updateChatStatus(queryVo);

            return new BaseResult(BaseResultEnum.SUCCESS.getStatus(), "修改成功", result);

        } catch (Exception e) {
            throw new RuntimeException("修改异常：" + e.getMessage());
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
