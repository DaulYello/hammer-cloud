package com.fmkj.user.server.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.fmkj.common.base.BaseResult;
import com.fmkj.common.base.BaseResultEnum;
import com.fmkj.common.constant.LogConstant;
import com.fmkj.common.util.StringUtils;
import com.fmkj.user.dao.domain.HcAccount;
import com.fmkj.user.dao.domain.HcFriend;
import com.fmkj.user.dao.queryVo.FriendQueryVo;
import com.fmkj.user.server.annotation.UserLog;
import com.fmkj.user.server.service.HcAccountService;
import com.fmkj.user.server.service.HcFriendService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  好友
 */
@RestController
@RequestMapping("/friend")
@DependsOn("springContextUtil")
@Api(tags ={ "好友接口"},description = "好友接口-网关路径/api-user")
public class FriendController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FriendController.class);


    @Autowired
    private HcAccountService hcAccountService;

    @Autowired
    private HcFriendService hcFriendService;


    // 发起好友请求
    @ApiOperation(value="发起好友请求", notes="参数：accountId, friendId")
    @UserLog(module= LogConstant.HC_FRIEND, actionDesc = "发起好友请求")
    @PostMapping("/addFriend")
    public BaseResult addFriend(@RequestBody HcFriend hcFriend) {
       if(StringUtils.isNull(hcFriend.getAccountId()) || StringUtils.isNull(hcFriend.getFriendId())){
           return new BaseResult(BaseResultEnum.BLANK.status,"accountId或friendId不能为空!",false);
       }
        if (hcFriend.getAccountId().equals(hcFriend.getFriendId())) {
            return new BaseResult(BaseResultEnum.BLANK.status,"你不能添加自己为好友!",false);
        }
        //发起好友请求之前先判断是不是已经好友
        HcFriend hf = new HcFriend();
        hf.setAccountId(hcFriend.getAccountId());
        hf.setFriendId(hcFriend.getFriendId());
        hf.setPass(1);
        EntityWrapper<HcFriend> wrapper = new EntityWrapper<>(hf);
        HcFriend hcf = hcFriendService.selectOne(wrapper);
        if(StringUtils.isNotNull(hcf)){
            return new BaseResult(BaseResultEnum.SUCCESS.status,"你们已经是好友了!",false);
        }

        //添加好友之前先判断是否已经添加过、对方还未同意的状态
        hf.setPass(0);
        EntityWrapper<HcFriend> hcwrapper = new EntityWrapper<>(hf);
        HcFriend hcw = hcFriendService.selectOne(hcwrapper);
        if(StringUtils.isNotNull(hcw)){
            return new BaseResult(BaseResultEnum.SUCCESS.status,"已经添加过了、请等待对方同意!",true);
        }
        // 发起好友请求
        hcFriendService.addFriend(hcFriend);
        return new BaseResult(BaseResultEnum.SUCCESS.status,"发送成功，请等待对方同意!",true);

    }


    // 查看是否有未读的好友请求
    @ApiOperation(value="查看是否有未读的好友请求", notes="参数：accountId")
    @PutMapping("/queryIsHaveFriendReq")
    public BaseResult queryIsHaveFriendReq(@RequestBody HcFriend hcFriend) {
        if(StringUtils.isNull(hcFriend.getAccountId())){
            return new BaseResult(BaseResultEnum.BLANK.status,"accountId不能为空!",false);
        }
        // 查询出所有好友请求
        HcFriend hc = new HcFriend();
        hc.setFriendId(hcFriend.getAccountId());
        hc.setPass(0);
        EntityWrapper<HcFriend> wrapper = new EntityWrapper<>(hc);
        List<HcFriend> hcFriendList = hcFriendService.selectList(wrapper);
        if (StringUtils.isEmpty(hcFriendList)) {
            return new BaseResult(BaseResultEnum.BLANK.status,"当前没有好友请求!",false);
        }

        ArrayList<HashMap<String, Object>> friendReqInfoList = new ArrayList<HashMap<String, Object>>();
        for (HcFriend h : hcFriendList) {
            HashMap<String, Object> friendReqInfo = new HashMap<String, Object>();
            HcAccount hcAccount = hcAccountService.selectById(h.getAccountId());
            friendReqInfo.put("accountId", hcAccount.getId());
            friendReqInfo.put("msg", h.getMsg());
            friendReqInfo.put("logo", hcAccount.getLogo());
            friendReqInfo.put("nickname", hcAccount.getNickname());
            friendReqInfo.put("gradeId", hcAccount.getGradeId());
            friendReqInfo.put("score", hcAccount.getScore());
            friendReqInfoList.add(friendReqInfo);
        }
        return new BaseResult(BaseResultEnum.SUCCESS.status,"查询成功!",friendReqInfoList);
    }

    // 是否通过好友请求
    @ApiOperation(value="是否通过好友请求", notes="参数：accountId, friendId, pass")
    @UserLog(module= LogConstant.HC_FRIEND, actionDesc = "发起好友请求")
    @PostMapping("/isPassFReq")
    public BaseResult isPassFReq(@RequestBody HcFriend hcFriend) {
        if(StringUtils.isNull(hcFriend.getAccountId())){
            return new BaseResult(BaseResultEnum.BLANK.status,"accountId不能为空!",false);
        }
        if(StringUtils.isNull(hcFriend.getFriendId())){
            return new BaseResult(BaseResultEnum.BLANK.status,"firendId不能为空!",false);
        }
        if(StringUtils.isNull(hcFriend.getPass())){
            return new BaseResult(BaseResultEnum.BLANK.status,"pass不能为空!",false);
        }
        HcFriend hf = new HcFriend();
        hf.setAccountId(hcFriend.getAccountId());
        hf.setFriendId(hcFriend.getFriendId());
        hf.setPass(1);
        EntityWrapper<HcFriend> wrapper = new EntityWrapper<>(hf);
        HcFriend hcf = hcFriendService.selectOne(wrapper);
        if(StringUtils.isNotNull(hcf)){
            return new BaseResult(BaseResultEnum.BLANK.status,"你们已经是好友了!",false);
        }
        hcFriendService.passFReq(hcFriend);
        if(hcFriend.getPass() == -1){
            return new BaseResult(BaseResultEnum.SUCCESS.status,"已拒绝!",true);
        }else{
            return new BaseResult(BaseResultEnum.SUCCESS.status,"已同意!",true);
        }
    }

    // 搜索陌生人
    @ApiOperation(value="搜索陌生人", notes="参数：nickName")
    @PutMapping("/searchStrangers")
    public BaseResult searchStrangers(@RequestBody FriendQueryVo queryVo) {
        if(StringUtils.isEmpty(queryVo.getNickName())){
            return new BaseResult(BaseResultEnum.BLANK.status,"昵称不能为空!",false);
        }
        // 查询出来陌生人
        HcAccount hcAccount = new HcAccount();
        hcAccount.setNickname(queryVo.getNickName());
        EntityWrapper<HcAccount> wrapper = new EntityWrapper<>(hcAccount);
        List<HcAccount> hcAccountList = hcAccountService.selectList(wrapper);
        return new BaseResult(BaseResultEnum.SUCCESS.status,"查询成功!",hcAccountList);
    }


    // 获取好友的列表
    @ApiOperation(value="获取好友的列表", notes="参数：accountId")
    @PutMapping("/queryAllFriends")
    public BaseResult queryAllFriends(@RequestBody FriendQueryVo queryVo) {
        if(StringUtils.isNull(queryVo.getAccountId())){
            return new BaseResult(BaseResultEnum.BLANK.status,"accountId不能为空!",false);
        }
        Page<HcAccount> tPage = buildPage(queryVo);
        List<HcAccount> hcAccountList = hcAccountService.queryAllFriends(tPage, queryVo.getAccountId());
        tPage.setRecords(hcAccountList);
        return new BaseResult(BaseResultEnum.SUCCESS.getStatus(), "查询成功", tPage);
    }



    /**
     * 传入accountId与friendId删除好友
     * @param hf
     * @return
     */
    @ApiOperation(value="删除好友", notes="参数：accountId, friendId")
    @UserLog(module= LogConstant.HC_FRIEND, actionDesc = "删除好友")
    @PostMapping("/deleteFriend")
    public BaseResult deleteFriend(@RequestBody HcFriend hf) {
        if(StringUtils.isNull(hf.getAccountId())){
            return new BaseResult(BaseResultEnum.BLANK.status,"accountId不能为空!",false);
        }
        if(StringUtils.isNull(hf.getFriendId())){
            return new BaseResult(BaseResultEnum.BLANK.status,"friendId不能为空!",false);
        }
        EntityWrapper<HcFriend> wrapper = new EntityWrapper<>(hf);
        boolean result = hcFriendService.delete(wrapper);
        if(!result){
            return new BaseResult(BaseResultEnum.BLANK.getStatus(), "删除失败", result);
        }
        return new BaseResult(BaseResultEnum.SUCCESS.getStatus(), "删除成功", result);
    }



    private Page<HcAccount> buildPage(FriendQueryVo friendQueryVo) {
        Page<HcAccount> tPage =new Page<HcAccount>(friendQueryVo.getPageNo(),friendQueryVo.getPageSize());
        if(StringUtils.isNotEmpty(friendQueryVo.getOrderBy())){
            tPage.setOrderByField(friendQueryVo.getOrderBy());
            tPage.setAsc(false);
        }
        if(StringUtils.isNotEmpty(friendQueryVo.getOrderByAsc())){
            tPage.setOrderByField(friendQueryVo.getOrderByAsc());
            tPage.setAsc(true);
        }
        return tPage;
    }

}
