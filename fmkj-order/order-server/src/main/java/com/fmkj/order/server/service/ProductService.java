package com.fmkj.order.server.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.fmkj.common.base.BaseService;
import com.fmkj.order.dao.domain.ProductInfo;
import com.fmkj.order.dao.dto.ProductDto;
import com.fmkj.order.dao.queryVo.ProductQueryVo;

import java.util.List;

/**
* @Description:
* @Author: youxun
* @Version: 1.0
**/
public interface ProductService extends BaseService<ProductInfo> {

    List<ProductDto> getProductPage(Pagination page, ProductQueryVo productQueryVo);

    boolean publishProduct(ProductInfo productInfo);

    boolean unLineProduct(ProductInfo productInfo);

    List<ProductDto> getMyProductPage(Page<ProductDto> tPage, ProductQueryVo productQueryVo);
}