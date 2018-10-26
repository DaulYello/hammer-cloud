package com.fmkj.order.server.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.fmkj.common.base.BaseApiService;
import com.fmkj.common.base.BaseController;
import com.fmkj.common.base.BaseResult;
import com.fmkj.common.base.BaseResultEnum;
import com.fmkj.common.constant.LogConstant;
import com.fmkj.common.util.StringUtils;
import com.fmkj.order.dao.domain.OrderInfo;
import com.fmkj.order.dao.dto.OrderDto;
import com.fmkj.order.dao.queryVo.OrderQueryVo;
import com.fmkj.order.server.annotation.OrderLog;
import com.fmkj.order.server.enmu.OrderEnum;
import com.fmkj.order.server.enmu.PayEnum;
import com.fmkj.order.server.service.OrderService;
import com.fmkj.order.server.util.MakeOrderNumUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 订单服务
 */
@RestController
@RequestMapping("/order")
@DependsOn("springContextHolder")
@Api(tags ={ "订单服务"},description = "商品服务接口-网关路径/api-order")
public class OrderController extends BaseController<OrderInfo, OrderService> implements BaseApiService<OrderInfo> {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @ApiOperation(value="分页查询订单列表", notes="分页查询订单列表")
    @PutMapping("/getOrderPage")
    public BaseResult<Page<OrderDto>> getOrderPage(@RequestBody OrderQueryVo orderQueryVo){
        try {
            Page<OrderDto> tPage = buildPage(orderQueryVo);
            List<OrderDto> list = orderService.getOrderPage(tPage, orderQueryVo);
            tPage.setRecords(list);
            return new BaseResult(BaseResultEnum.SUCCESS.getStatus(), "查询成功", tPage);
        } catch (Exception e) {
            throw new RuntimeException("查询订单列表异常：" + e.getMessage());
        }
    }

    @ApiOperation(value="根据ID查询订单详情", notes="根据ID查询订单详情-入参：ID")
    @PutMapping("/getOrderById")
    public BaseResult<OrderInfo> getOrderById(@RequestBody OrderQueryVo orderQueryVo){
        try {
            if(StringUtils.isNull(orderQueryVo) || StringUtils.isNull(orderQueryVo.getId())){
                return new BaseResult(BaseResultEnum.BLANK.getStatus(), "ID不能为空", false);
            }
            List<OrderDto> orderDtos = orderService.selectDetailsById(orderQueryVo);
            return new BaseResult(BaseResultEnum.SUCCESS.getStatus(), "查询成功", orderDtos);
        } catch (Exception e) {
            throw new RuntimeException("根据ID查询订单异常：" + e.getMessage());
        }
    }

    /*@ApiOperation(value="分页查询商品下的订单列表", notes="分页查询商品下的订单列表--输入参数:sellerId")
    @PutMapping("/getOrderPageBySeller")
    public BaseResult<Page<OrderDto>> getOrderPageBySeller(@RequestBody OrderQueryVo orderQueryVo){
        try {
            if(StringUtils.isNull(orderQueryVo) || StringUtils.isNull(orderQueryVo.getSellerId())){
                return new BaseResult(BaseResultEnum.BLANK.getStatus(), "卖家用户ID不能为空", false);
            }
            Page<OrderDto> tPage = buildPage(orderQueryVo);
            List<OrderDto> list = orderService.getOrderPageBySeller(tPage, orderQueryVo);
            tPage.setRecords(list);
            return new BaseResult(BaseResultEnum.SUCCESS.getStatus(), "查询成功", tPage);
        } catch (Exception e) {
            throw new RuntimeException("查询商品下订单列表异常：" + e.getMessage());
        }
    }*/

    @ApiOperation(value="取消订单", notes="取消订单、需要把P能量退回到商品的库存中--入参为：id, productId, tradeNum")
    @OrderLog(module= LogConstant.HC_ORDER, actionDesc = "取消订单")
    @PostMapping("/cancelOrder")
    public BaseResult cancelOrder(@RequestBody OrderInfo orderInfo){
        try {
            if(StringUtils.isNull(orderInfo) || StringUtils.isNull(orderInfo.getId())){
                return new BaseResult(BaseResultEnum.BLANK.getStatus(), "订单ID不能为空", false);
            }
            if(StringUtils.isNull(orderInfo.getProductId())){
                return new BaseResult(BaseResultEnum.BLANK.getStatus(), "商品ID不能为空", false);
            }
            if(StringUtils.isNull(orderInfo.getTradeNum())){
                return new BaseResult(BaseResultEnum.BLANK.getStatus(), "交易数量不能为空", false);
            }
            orderInfo.setUpdateTime(new Date());
            orderInfo.setOrderStatus(OrderEnum.ORDER_CANCEL.status);
            boolean result = orderService.updateOrder(orderInfo);
            if(result){
                return new BaseResult(BaseResultEnum.SUCCESS.getStatus(), "订单取消成功", "商品ID【" + orderInfo.getProductId() + "】库存增加" + orderInfo.getTradeNum() + "P");
            }else{
                return new BaseResult(BaseResultEnum.ERROR.getStatus(), "取消订单失败", false);
            }
        } catch (Exception e) {
            throw new RuntimeException("取消订单异常：" + e.getMessage());
        }
    }

    @ApiOperation(value="删除订单", notes="根据ID删除订单--传入参数ID，orderStatus, productId, tradeNum")
    @OrderLog(module= LogConstant.HC_ORDER, actionDesc = "删除订单")
    @PostMapping("/deleteOrderById")
    public BaseResult deleteById(@RequestBody OrderInfo orderInfo){
        try {
            if(StringUtils.isNull(orderInfo) || StringUtils.isNull(orderInfo.getId())){
                return new BaseResult(BaseResultEnum.BLANK.getStatus(), "ID不能为空", false);
            }
            if(StringUtils.isNull(orderInfo.getOrderStatus())){
                return new BaseResult(BaseResultEnum.BLANK.getStatus(), "订单状态不能为空", false);
            }
            if(StringUtils.isNull(orderInfo.getProductId())){
                return new BaseResult(BaseResultEnum.BLANK.getStatus(), "商品ID不能为空", false);
            }
            if(StringUtils.isNull(orderInfo.getTradeNum())){
                return new BaseResult(BaseResultEnum.BLANK.getStatus(), "购买数量不能为空", false);
            }
            if(orderInfo.getOrderStatus() == OrderEnum.ORDER_PAY.status){
                return new BaseResult(BaseResultEnum.BLANK.getStatus(), "已付款不能删除", false);
            }

            //新建状态下的删除商品需要返回P能量、订单的删除只作逻辑删除
            orderInfo.setUpdateTime(new Date());
            orderInfo.setOrderStatus(OrderEnum.ORDER_DEL.status);
            if(orderInfo.getOrderStatus() == OrderEnum.ORDER_ADD.status){
                orderService.updateOrder(orderInfo);
            }else{
                orderService.updateById(orderInfo);
            }
            return new BaseResult(BaseResultEnum.SUCCESS.getStatus(), "删除成功", "数据删除成功");
        } catch (Exception e) {
            throw new RuntimeException("删除异常：" + e.getMessage());
        }
    }

    @ApiOperation(value="新建订单", notes="新建订单，需要扣除商品表的库存")
    @OrderLog(module= LogConstant.HC_ORDER, actionDesc = "新建订单")
    @PostMapping("/addOrder")

    public BaseResult addOrder(@RequestBody OrderInfo orderInfo){
        try {
            if(StringUtils.isNull(orderInfo) || StringUtils.isNull(orderInfo.getUserId())){
                return new BaseResult(BaseResultEnum.BLANK.getStatus(), "下单用户ID不能为空", false);
            }
            if(StringUtils.isNull(orderInfo.getProductId())){
                return new BaseResult(BaseResultEnum.BLANK.getStatus(), "商品ID不能为空", false);
            }
            if(StringUtils.isNull(orderInfo.getSellerId())){
                return new BaseResult(BaseResultEnum.BLANK.getStatus(), "卖家用户ID不能为空", false);
            }
            orderInfo.setOrderStatus(OrderEnum.ORDER_ADD.status);
            orderInfo.setOrderNo(MakeOrderNumUtils.createOrderNum());
            orderInfo.setCreateTime(new Date());
            boolean result = orderService.addOrder(orderInfo);
            if(result){
                return new BaseResult(BaseResultEnum.SUCCESS.getStatus(), "新建订单成功", "从商品ID【" + orderInfo.getProductId() + "】库存中减掉" + orderInfo.getTradeNum() + "P");
            }else{
                return new BaseResult(BaseResultEnum.ERROR.getStatus(), "新建订单失败", false);
            }

        } catch (Exception e) {
            throw new RuntimeException("新增异常：" + e.getMessage());
        }
    }

    @ApiOperation(value="修改订单", notes="根据ID修改订单")
    @OrderLog(module= LogConstant.HC_ORDER, actionDesc = "修改订单")
    @PostMapping("/updateOrder")
    public BaseResult updateProduct(@RequestBody OrderInfo orderInfo){
        try {
            if(StringUtils.isNull(orderInfo) || orderInfo.getId() == null){
                return new BaseResult(BaseResultEnum.BLANK.getStatus(), "ID不能为空", false);
            }
            orderInfo.setUpdateTime(new Date());
            return super.updateById(orderInfo);
        } catch (Exception e) {
            throw new RuntimeException("修改异常：" + e.getMessage());
        }
    }

    @ApiOperation(value="买方支付确认", notes="作为买方购买P能量、确认已支付金额--入参为：id, paymentType")
    @OrderLog(module= LogConstant.HC_ORDER, actionDesc = "买方支付确认")
    @PostMapping("/buyerPayConfirm")
    public BaseResult buyerPayConfirm(@RequestBody OrderInfo orderInfo){
        try {
            if(StringUtils.isNull(orderInfo) || StringUtils.isNull(orderInfo.getId())){
                return new BaseResult(BaseResultEnum.BLANK.getStatus(), "订单ID不能为空", false);
            }
            if(StringUtils.isNull(orderInfo.getPaymentType())){
                return new BaseResult(BaseResultEnum.BLANK.getStatus(), "支付类型不能为空", false);
            }
            orderInfo.setUpdateTime(new Date());
            orderInfo.setPaymentTime(new Date());
            orderInfo.setIsPay(PayEnum.PAY_BUYER_PAY.status);
            orderInfo.setPaymentType(orderInfo.getPaymentType());
            orderInfo.setOrderStatus(OrderEnum.ORDER_PAY.status);
            if (orderService.updateById(orderInfo))return new BaseResult(BaseResultEnum.SUCCESS.getStatus(), "支付成功","买方支付金额成功");
            else return new BaseResult(BaseResultEnum.ERROR.getStatus(),"支付失败","买方支付金额失败");
        } catch (Exception e) {
            throw new RuntimeException("买方支付金额异常：" + e.getMessage());
        }
    }

    @ApiOperation(value="卖方支付确认", notes="卖方出售P能量、收到买方支付金额后确认放出P能量--入参为：id, productId, userId, tradeNum")
    @OrderLog(module= LogConstant.HC_ORDER, actionDesc = "卖方支付确认")
    @PostMapping("/sellerPayConfirm")
    public BaseResult sellerPayConfirm(@RequestBody OrderInfo orderInfo){
        try {
            if(StringUtils.isNull(orderInfo) || StringUtils.isNull(orderInfo.getId())){
                return new BaseResult(BaseResultEnum.BLANK.getStatus(), "订单ID不能为空", false);
            }
            if(StringUtils.isNull(orderInfo.getUserId())){
                return new BaseResult(BaseResultEnum.BLANK.getStatus(), "userId不能为空", false);
            }
            if(StringUtils.isNull(orderInfo.getTradeNum())){
                return new BaseResult(BaseResultEnum.BLANK.getStatus(), "交易数量不能为空", false);
            }
            if(StringUtils.isNull(orderInfo.getProductId())){
                return new BaseResult(BaseResultEnum.BLANK.getStatus(), "商品ID不能为空", false);
            }
            orderInfo.setUpdateTime(new Date());
            orderInfo.setEndTime(new Date());
            orderInfo.setIsPay(PayEnum.PAY_SELLER_PAY.status);
            orderInfo.setOrderStatus(OrderEnum.ORDER_SUCCESS.status);
            boolean result = orderService.sellerPayConfirm(orderInfo);
            if(result){
                return new BaseResult(BaseResultEnum.SUCCESS.getStatus(),"支付成功","卖方支付"+orderInfo.getTradeNum()+"P");
            }else{
                return new BaseResult(BaseResultEnum.ERROR.getStatus(),"支付失败","卖方支付P能量失败");
            }
        } catch (Exception e) {
            throw new RuntimeException("卖方支付确认异常：" + e.getMessage());
        }
    }

    /**
     *  1、卖方扣除自己的P能量
     *  2、收P的人增加相应的P能量
     * @param orderInfo
     * @return
     */
    @ApiOperation(value="卖出P能量确认", notes="将自己的P能量卖给别人--入参为：userId, sellerId, tradeNum")
    @OrderLog(module= LogConstant.HC_ORDER, actionDesc = "卖方支付确认")
    @PostMapping("/sellPToPublisher")
    public BaseResult sellPToPublisher(@RequestBody OrderInfo orderInfo){
        try {
            if(StringUtils.isNull(orderInfo) || StringUtils.isNull(orderInfo.getId())){
                return new BaseResult(BaseResultEnum.BLANK.getStatus(), "订单ID不能为空", false);
            }
            if(StringUtils.isNull(orderInfo.getSellerId())){
                return new BaseResult(BaseResultEnum.BLANK.getStatus(), "sellerId不能为空", false);
            }
            if(StringUtils.isNull(orderInfo.getUserId())){
                return new BaseResult(BaseResultEnum.BLANK.getStatus(), "userId不能为空", false);
            }
            if(StringUtils.isNull(orderInfo.getTradeNum())){
                return new BaseResult(BaseResultEnum.BLANK.getStatus(), "交易数量不能为空", false);
            }
            orderInfo.setUpdateTime(new Date());
            orderInfo.setPaymentTime(new Date());
            orderInfo.setOrderStatus(OrderEnum.ORDER_PAY.status);
            boolean result = orderService.sellPToPublisher(orderInfo);
            if(result){
                return new BaseResult(BaseResultEnum.SUCCESS.getStatus(),"成功卖出","成功卖出"+orderInfo.getTradeNum()+"P");
            }else{
                return new BaseResult(BaseResultEnum.ERROR.getStatus(),"卖出失败",false);
            }
        } catch (Exception e) {
            throw new RuntimeException("卖出P能量异常：" + e.getMessage());
        }
    }


    @ApiOperation(value="收P能量支付确认", notes="收P能量的人作支付金额确认、向出售P能量的人支付金额-入参为：id, paymentType")
    @OrderLog(module= LogConstant.HC_ORDER, actionDesc = "卖方支付确认")
    @PostMapping("/merchantPayConfirm")
    public BaseResult merchantPayConfirm(@RequestBody OrderInfo orderInfo){
        try {
            if(StringUtils.isNull(orderInfo) || StringUtils.isNull(orderInfo.getId())){
                return new BaseResult(BaseResultEnum.BLANK.getStatus(), "订单ID不能为空", false);
            }
            if(StringUtils.isNull(orderInfo.getPaymentType())){
                return new BaseResult(BaseResultEnum.BLANK.getStatus(), "支付类型不能为空", false);
            }
            orderInfo.setUpdateTime(new Date());
            orderInfo.setEndTime(new Date());
            orderInfo.setIsPay(PayEnum.PAY_BUYER_PAY.status);
            orderInfo.setPaymentType(orderInfo.getPaymentType());
            orderInfo.setOrderStatus(OrderEnum.ORDER_SUCCESS.status);
            if (orderService.updateById(orderInfo))return new BaseResult(BaseResultEnum.SUCCESS.getStatus(), "支付成功",true);
            else return new BaseResult(BaseResultEnum.ERROR.getStatus(),"支付失败",false);
        } catch (Exception e) {
            throw new RuntimeException("支付金额确认异常：" + e.getMessage());
        }
    }


    /**
     * 构建查询条件
     */
    private EntityWrapper<OrderInfo> transWrapper(OrderQueryVo orderQueryVo) {
        EntityWrapper<OrderInfo> entityWrapper = new EntityWrapper<OrderInfo>();
        if(StringUtils.isNotEmpty(orderQueryVo.getOrderNo())){
            entityWrapper.eq("order_no", orderQueryVo.getOrderNo());
        }
        if(StringUtils.isNotNull(orderQueryVo.getPaymentType())){
            entityWrapper.eq("payment_type", orderQueryVo.getPaymentType());
        }
        if(StringUtils.isNotNull(orderQueryVo.getOrderStatus())){
            entityWrapper.eq("order_status", orderQueryVo.getOrderStatus());
        }
        if(StringUtils.isNotNull(orderQueryVo.getIsPay())){
            entityWrapper.eq("is_pay", orderQueryVo.getIsPay());
        }
        if(StringUtils.isNotNull(orderQueryVo.getUserId())){
            entityWrapper.eq("userId", orderQueryVo.getUserId());
        }
        if(StringUtils.isNotNull(orderQueryVo.getSellerId())){
            entityWrapper.eq("sellerId", orderQueryVo.getSellerId());
        }
        // 降序字段
        if(StringUtils.isNotEmpty(orderQueryVo.getOrderBy())) {
            entityWrapper.orderBy(orderQueryVo.getOrderBy(),false);
        }
        // 升序字段
        if(StringUtils.isNotEmpty(orderQueryVo.getOrderByAsc())) {
            entityWrapper.orderBy(orderQueryVo.getOrderByAsc(),true);
        }

        return entityWrapper;

    }

    private Page<OrderDto> buildPage(OrderQueryVo orderQueryVo) {
        Page<OrderDto> tPage =new Page<OrderDto>(orderQueryVo.getPageNo(),orderQueryVo.getPageSize());
        if(StringUtils.isNotEmpty(orderQueryVo.getOrderBy())){
            tPage.setOrderByField(orderQueryVo.getOrderBy());
            tPage.setAsc(false);
        }
        if(StringUtils.isNotEmpty(orderQueryVo.getOrderByAsc())){
            tPage.setOrderByField(orderQueryVo.getOrderByAsc());
            tPage.setAsc(true);
        }
        return tPage;
    }



}
