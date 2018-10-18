package com.fmkj.order.server.service.impl;


import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.fmkj.common.annotation.BaseService;
import com.fmkj.common.base.BaseServiceImpl;
import com.fmkj.order.dao.domain.HcAccount;
import com.fmkj.order.dao.domain.ProductInfo;
import com.fmkj.order.dao.dto.ProductDto;
import com.fmkj.order.dao.mapper.HcAccountMapper;
import com.fmkj.order.dao.mapper.ProductMapper;
import com.fmkj.order.dao.queryVo.ProductQueryVo;
import com.fmkj.order.server.enmu.ProductTypeEnum;
import com.fmkj.order.server.service.ProductService;
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
public class ProductServiceImpl extends BaseServiceImpl<ProductMapper, ProductInfo> implements ProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private HcAccountMapper hcAccountMapper;

    @Override
    public List<ProductDto> getProductPage(Pagination pagination, ProductQueryVo productQueryVo) {
        List<ProductDto> result = productMapper.queryProductPage(pagination, productQueryVo);
        return result;
    }

    /**
     *   发布商品：商品类型1、卖出;2、买入
     * //第一步：更新商品表状态
     * //第二步：扣除P能量
     *
     *  买入不需要扣除能量
     * @param productInfo
     * @return
     */
    @Override
    public boolean publishProduct(ProductInfo productInfo) {
        int result = productMapper.updateById(productInfo);
        if (result > 0){
            //买入不需要扣除CNT
            if(productInfo.getProductType() == ProductTypeEnum.BUY_TYPE.status){
                return true;
            }
            HcAccount hcAccount = hcAccountMapper.selectById(productInfo.getUserId());
            Double productSum = productInfo.getProductSum();
            Double myCnt = hcAccount.getCnt();
            hcAccount.setCnt(myCnt - productSum);
            hcAccountMapper.updateById(hcAccount);
            return true;
        }
        return false;
    }

    /**
     * 下架
     * @param productInfo
     * @return
     */
    @Override
    public boolean unLineProduct(ProductInfo productInfo) {
        int result = productMapper.updateById(productInfo);
        if(result > 0){
            if (productInfo.getProductStock() > 0){
                HcAccount hcAccount = hcAccountMapper.selectById(productInfo.getUserId());
                Double myCnt = hcAccount.getCnt();
                hcAccount.setCnt(myCnt + productInfo.getProductStock());
                hcAccountMapper.updateById(hcAccount);
            }
            return true;
        }
        return false;
    }
}