package com.fmkj.race.server.controller;

import com.fmkj.common.base.BaseApiService;
import com.fmkj.common.base.BaseController;
import com.fmkj.common.base.BaseResult;
import com.fmkj.common.base.BaseResultEnum;
import com.fmkj.common.constant.LogConstant;
import com.fmkj.race.dao.domain.GcOrder;
import com.fmkj.race.server.annotation.RaceLog;
import com.fmkj.race.server.service.GcOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @ Author     ：yangshengbin
 * @ Date       ：15:03 2018/8/31 0031
 * @ Description：活动物流信息
 */
@RestController
@RequestMapping("/gcorder")
@DependsOn("springContextHandler")
@Api(tags ={ "物流信息服务"},description = "物流信息服务接口-网关路径/api-race")
public class GcOrderController extends BaseController<GcOrder,GcOrderService> implements BaseApiService<GcOrder> {


    //快捷键 Ctrl+Shift+u即可实现大小写的快速切换



    @Autowired
    private GcOrderService gcOrderService;//物流接口

    /**
     * @author yangshengbin
     * @Description：物流确认发货
     * @date 2018/8/31 0031 15:08
     * @param
     * @return
    */

    @ApiOperation(value="确认发货", notes="活动发起者确认发货")
    @RaceLog(module= LogConstant.Gc_Activity, actionDesc = "活动发起者确认发货")
    @PostMapping("/sendGoodsByExample")
    public BaseResult<Map<String,Object>> startActivityByExample(@RequestBody Map<String,Object> map) {

        Integer aid = Integer.parseInt(map.get("aid").toString());//活动id
        String name = map.get("name").toString();//物流名称
        String number = map.get("number").toString();//物流单号
        GcOrder gcOrder = new GcOrder();
        gcOrder.setAid(aid);
        gcOrder.setName(name);
        gcOrder.setNumber(number);
        boolean result = gcOrderService.sendGoodsByExample(gcOrder);
        if(!result) {
            return new BaseResult(BaseResultEnum.ERROR.status, "发货失败,物流信息已存在!",false);
        }
        return new BaseResult(BaseResultEnum.SUCCESS.status, "发货成功!",true);
    }




    /**
     * @author yangshengbin
     * @Description：用户确认收货
     * @date 2018/8/31 0031 17:13
     * @param map
     * @return HashMap<String,Object>
     */
    @PostMapping("/collectGoods")
    @ApiOperation(value="确认收货", notes="活动中锤者确认收货")
    @RaceLog(module= LogConstant.Gc_Activity, actionDesc = "活动中锤者确认收货")
    public BaseResult collectGoods(@RequestBody Map<String, Object> map){

        Integer aid = Integer.parseInt(map.get("aid").toString());//活动id
        GcOrder order = new GcOrder();
        order.setAid(aid);
        boolean result = gcOrderService.collectGoods(order);
        if(!result) {
            return new BaseResult(BaseResultEnum.ERROR.status, "收货失败!",null);
        }
        return new BaseResult(BaseResultEnum.SUCCESS.status, "收货成功!",null);
    }




}
