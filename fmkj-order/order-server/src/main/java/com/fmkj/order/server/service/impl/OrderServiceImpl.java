package com.fmkj.order.server.service.impl;


import com.fmkj.common.annotation.BaseService;
import com.fmkj.common.base.BaseServiceImpl;
import com.fmkj.order.dao.domain.HcAccount;
import com.fmkj.order.dao.domain.OrderInfo;
import com.fmkj.order.dao.domain.ProductInfo;
import com.fmkj.order.dao.dto.OrderDto;
import com.fmkj.order.dao.mapper.HcAccountMapper;
import com.fmkj.order.dao.mapper.OrderMapper;
import com.fmkj.order.dao.mapper.ProductMapper;
import com.fmkj.order.dao.queryVo.OrderQueryVo;
import com.fmkj.order.server.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Description:
 * @Author: youxun
 * @Version: 1.0
 **/
@Service
@Transactional
@BaseService
public class OrderServiceImpl extends BaseServiceImpl<OrderMapper, OrderInfo> implements OrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);


    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private HcAccountMapper hcAccountMapper;

    @Override
    public List<OrderDto> getOrderPage(OrderQueryVo orderQueryVo) {
        return orderMapper.queryOrderPage(orderQueryVo);
    }

    @Override
    public List<OrderDto> getOrderPageBySeller(OrderQueryVo orderQueryVo) {
        return orderMapper.getOrderPageBySeller(orderQueryVo);
    }

    @Override
    public boolean sellPToPublisher(OrderInfo orderInfo) {
        int result = orderMapper.updateById(orderInfo);
        if(result > 0){
            Double tradeNum = orderInfo.getTradeNum();
            // 1、卖方扣除自己的P能量
            HcAccount hcAccount = hcAccountMapper.selectById(orderInfo.getUserId());
            Double myP = hcAccount.getMyP();
            hcAccount.setMyP(myP - tradeNum);
            hcAccountMapper.updateById(hcAccount);
            // 2、收方增加相应的P能量
            HcAccount seller = hcAccountMapper.selectById(orderInfo.getSellerId());
            Double sellerP = seller.getMyP();
            seller.setMyP(sellerP + tradeNum);
            hcAccountMapper.updateById(seller);
            return true;
        }
        return false;
    }

    @Override
    public boolean sellerPayConfirm(OrderInfo orderInfo) {
        int result = orderMapper.updateById(orderInfo);
        if(result > 0){
            HcAccount hcAccount = hcAccountMapper.selectById(orderInfo.getUserId());
            Double myP = hcAccount.getMyP();
            Double tradeNum = orderInfo.getTradeNum();
            hcAccount.setMyP(myP + tradeNum);
            hcAccountMapper.updateById(hcAccount);
            return true;
        }
        return false;
    }

    /**
     *  从商品表的库存减掉
     * @param orderInfo
     * @return
     */
    @Override
    public boolean addOrder(OrderInfo orderInfo) {
       int result = orderMapper.insert(orderInfo);
       if(result > 0){
           ProductInfo productInfo = productMapper.selectById(orderInfo.getProductId());
           Double stock = productInfo.getProductStock();
           Double tradeNum = orderInfo.getTradeNum();
           productInfo.setProductStock(stock - tradeNum);
           productMapper.updateById(productInfo);
           return true;
       }
       return false;
    }

    /**
     * 需要把P能量退回到商品的库存中
     * @param orderInfo
     * @return
     */
    @Override
    public boolean updateOrder(OrderInfo orderInfo) {
        int result = orderMapper.updateById(orderInfo);
        if(result > 0){
            ProductInfo productInfo = productMapper.selectById(orderInfo.getProductId());
            Double stock = productInfo.getProductStock();
            Double tradeNum = orderInfo.getTradeNum();
            productInfo.setProductStock(stock + tradeNum);
            productMapper.updateById(productInfo);
            return true;
        }
        return false;
    }


}