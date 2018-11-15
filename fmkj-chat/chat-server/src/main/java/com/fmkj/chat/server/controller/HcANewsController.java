package com.fmkj.chat.server.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.fmkj.chat.dao.domain.FmNews;
import com.fmkj.chat.dao.dto.FmNewsDto;
import com.fmkj.chat.dao.queryVo.ChatQueryVo;
import com.fmkj.chat.server.service.FmNewsService;
import com.fmkj.chat.server.service.HcAppchatService;
import com.fmkj.common.base.BaseApiService;
import com.fmkj.common.base.BaseController;
import com.fmkj.common.base.BaseResult;
import com.fmkj.common.base.BaseResultEnum;
import com.fmkj.common.util.DateUtil;
import com.fmkj.common.util.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/news")
@Api(tags ={ "消息服务"},description = "消息服务接口-网关路径/api-chat")
public class HcANewsController extends BaseController<FmNews, FmNewsService> implements BaseApiService<FmNews> {

    @Autowired
    private FmNewsService fmNewsService;

    @Autowired
    private HcAppchatService hcAppchatService;

    /**
     * 1、活动;2、竞锤;3、聊天; 4、c2c交易; 5、好友
     * @param queryVo
     * @return
     */
    @ApiOperation(value="首页消息通知列表", notes="首页消息通知列表，uid")
    @PutMapping("/queryNewsInfo")
    public BaseResult queryNewsInfo(@RequestBody ChatQueryVo queryVo){
        if(StringUtils.isNull(queryVo) || queryVo.getUid() == null){
            return new BaseResult(BaseResultEnum.BLANK.getStatus(), "UID不能为空", false);
        }
        List<FmNewsDto> resultList = new ArrayList<>();
        List<FmNews> newsList = fmNewsService.selectList(new EntityWrapper<>());
        if(StringUtils.isNotEmpty(newsList)){
            for(FmNews news : newsList){
                switch (news.getType()){
                    case 1:
                        List activityList = hcAppchatService.queryNewActivity();
                        if(StringUtils.isNotEmpty(activityList)){
                            for(int i = 0; i < activityList.size(); i ++){
                                HashMap activityMap = (HashMap) activityList.get(i);
                                FmNewsDto fmNewsDto = new FmNewsDto();
                                fmNewsDto.setId(news.getId());
                                fmNewsDto.setTitle(news.getName());
                                fmNewsDto.setNewsNum(1);
                                fmNewsDto.setNewsType(news.getType());
                                fmNewsDto.setNickname((String) activityMap.get("nickname"));
                                Long userId = (Long) activityMap.get("userId");
                                fmNewsDto.setUserId(userId.intValue());
                                fmNewsDto.setAvatar((String) activityMap.get("avatar"));
                                fmNewsDto.setPname((String) activityMap.get("pname"));
                                fmNewsDto.setActivityName((String) activityMap.get("name"));
                                Date createDate = (Date) activityMap.get("createDate");
                                fmNewsDto.setCreateDate(DateUtil.dateStr(createDate, "yyyy-MM-dd HH:mm:ss"));
                                fmNewsDto.setPrimaryKey((Integer) activityMap.get("id"));
                                fmNewsDto.setMessage((String) activityMap.get("message"));
                                resultList.add(fmNewsDto);
                            }
                        }
                        break;
                    case 2:
                        List raceList = hcAppchatService.queryNewsRaceInfo(queryVo);
                        if(StringUtils.isNotEmpty(raceList)){
                            for(int i =0; i < raceList.size(); i++){
                                HashMap raceMap = (HashMap) raceList.get(i);
                                FmNewsDto fmNewsDto = new FmNewsDto();
                                fmNewsDto.setId(news.getId());
                                fmNewsDto.setTitle(news.getName());
                                fmNewsDto.setNewsNum(1);
                                fmNewsDto.setNewsType(news.getType());
                                fmNewsDto.setNickname((String) raceMap.get("nickname"));
                                fmNewsDto.setUserId((Integer) raceMap.get("userId"));
                                fmNewsDto.setAvatar((String) raceMap.get("avatar"));
                                fmNewsDto.setMessage((String) raceMap.get("text"));
                                Date createDate = (Date) raceMap.get("createDate");
                                fmNewsDto.setCreateDate(DateUtil.dateStr(createDate, "yyyy-MM-dd HH:mm:ss"));
                                fmNewsDto.setPrimaryKey((Integer) raceMap.get("id"));
                                resultList.add(fmNewsDto);
                            }
                        }
                        break;
                    case 3:
                        List appchatList = hcAppchatService.queryNewsCaht(queryVo);
                        if(StringUtils.isNotEmpty(appchatList)){
                            for(int i =0; i < appchatList.size(); i++){
                                HashMap map = (HashMap) appchatList.get(i);
                                FmNewsDto fmNewsDto = new FmNewsDto();
                                fmNewsDto.setId(news.getId());
                                fmNewsDto.setTitle(news.getName());
                                Long newsNum = (Long) map.get("newsNum");
                                fmNewsDto.setNewsNum(newsNum.intValue());
                                fmNewsDto.setNewsType(news.getType());
                                fmNewsDto.setNickname((String) map.get("nickname"));
                                Long userId = (Long) map.get("userId");
                                fmNewsDto.setUserId(userId.intValue());
                                fmNewsDto.setAvatar((String) map.get("avatar"));
                                fmNewsDto.setMessage((String) map.get("text"));
                                Date createDate = (Date) map.get("createDate");
                                fmNewsDto.setCreateDate(DateUtil.dateStr(createDate, "yyyy-MM-dd HH:mm:ss"));
                                resultList.add(fmNewsDto);
                            }
                        }
                        break;
                    case 4:
                        List orderList = hcAppchatService.queryNewsOrder(queryVo);
                        if(StringUtils.isNotEmpty(orderList)){
                            for(int i =0; i < orderList.size(); i++){
                                HashMap orderMap = (HashMap) orderList.get(i);
                                FmNewsDto fmNewsDto = new FmNewsDto();
                                fmNewsDto.setId(news.getId());
                                fmNewsDto.setTitle(news.getName());
                                fmNewsDto.setNewsNum(orderList.size());
                                fmNewsDto.setNewsType(news.getType());
                                fmNewsDto.setNickname((String) orderMap.get("nickname"));
                                Long userId = (Long) orderMap.get("userId");
                                fmNewsDto.setUserId(userId.intValue());
                                fmNewsDto.setAvatar((String) orderMap.get("avatar"));
                                fmNewsDto.setMessage((String) orderMap.get("message"));
                                Date createDate = (Date) orderMap.get("createDate");
                                fmNewsDto.setCreateDate(DateUtil.dateStr(createDate, "yyyy-MM-dd HH:mm:ss"));
                                Long id = (Long) orderMap.get("id");
                                fmNewsDto.setPrimaryKey(id.intValue());
                                resultList.add(fmNewsDto);
                                break;
                            }
                        }
                        break;
                    case 5:
                        List applyList = hcAppchatService.queryApplyList(queryVo);
                        if(StringUtils.isNotEmpty(applyList)){
                            for(int i =0; i < applyList.size(); i++){
                                HashMap applyMap = (HashMap) applyList.get(i);
                                FmNewsDto fmNewsDto = new FmNewsDto();
                                fmNewsDto.setId(news.getId());
                                fmNewsDto.setTitle(news.getName());
                                fmNewsDto.setNewsNum(applyMap.size());
                                fmNewsDto.setNewsType(news.getType());
                                fmNewsDto.setNickname((String) applyMap.get("nickname"));
                                Long userId = (Long) applyMap.get("userId");
                                fmNewsDto.setUserId(userId.intValue());
                                fmNewsDto.setAvatar((String) applyMap.get("avatar"));
                                Date createDate = (Date) applyMap.get("createDate");
                                fmNewsDto.setCreateDate(DateUtil.dateStr(createDate, "yyyy-MM-dd HH:mm:ss"));
                                Long id = (Long) applyMap.get("id");
                                fmNewsDto.setPrimaryKey(id.intValue());
                                fmNewsDto.setMessage((String) applyMap.get("message"));
                                fmNewsDto.setOperateType(1);
                                resultList.add(fmNewsDto);
                                break;
                            }
                        }
                        List refuseList = hcAppchatService.queryRefuseList(queryVo);
                        if(StringUtils.isNotEmpty(refuseList)){
                            for(int i =0; i < refuseList.size(); i++){
                                HashMap refuseMap = (HashMap) refuseList.get(i);
                                FmNewsDto fmNewsDto = new FmNewsDto();
                                fmNewsDto.setId(news.getId());
                                fmNewsDto.setTitle(news.getName());
                                fmNewsDto.setNewsNum(1);
                                fmNewsDto.setNewsType(news.getType());
                                fmNewsDto.setNickname((String) refuseMap.get("nickname"));
                                Long userId = (Long) refuseMap.get("userId");
                                fmNewsDto.setUserId(userId.intValue());
                                fmNewsDto.setAvatar((String) refuseMap.get("avatar"));
                                Date createDate = (Date) refuseMap.get("createDate");
                                fmNewsDto.setCreateDate(DateUtil.dateStr(createDate, "yyyy-MM-dd HH:mm:ss"));
                                Long id = (Long) refuseMap.get("id");
                                fmNewsDto.setPrimaryKey(id.intValue());
                                fmNewsDto.setMessage((String) refuseMap.get("message"));
                                fmNewsDto.setOperateType(2);
                                resultList.add(fmNewsDto);
                            }
                        }

                        break;
                    default:
                        break;
                }

            }

        }
        //按时间重新排序
        if(StringUtils.isNotEmpty(resultList)){
            Collections.sort(resultList, new Comparator<FmNewsDto>() {
                @Override
                public int compare(FmNewsDto o1, FmNewsDto o2) {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    try {
                        Date dt1 = format.parse(o1.getCreateDate());
                        Date dt2 = format.parse(o2.getCreateDate());
                        if (dt1.getTime() > dt2.getTime()) {
                            return -1;
                        } else if (dt1.getTime() < dt2.getTime()) {
                            return 1;
                        } else {
                            return 0;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return 0;
                }
            });
        }
        return new BaseResult(BaseResultEnum.SUCCESS, resultList);

    }


    @ApiOperation(value="查询是否有待处理交易订单", notes="查询是否有待处理交易订单，uid")
    @PutMapping("/queryToProcessed")
    public BaseResult queryToProcessed(@RequestBody ChatQueryVo queryVo){
        if(StringUtils.isNull(queryVo) || queryVo.getUid() == null){
            return new BaseResult(BaseResultEnum.BLANK.getStatus(), "UID不能为空", false);
        }
        HashMap<String, Object> resultMap = new HashMap<>();
        List orderList = hcAppchatService.queryNewsOrder(queryVo);
        if(StringUtils.isNotEmpty(orderList)){
            resultMap.put("processNum", orderList.size());
        }else {
            resultMap.put("processNum", 0);
        }
        return new BaseResult(BaseResultEnum.SUCCESS.status, "查询成功", resultMap);
    }

}
