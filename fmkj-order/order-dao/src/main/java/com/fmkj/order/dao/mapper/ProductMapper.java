package com.fmkj.order.dao.mapper;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.fmkj.order.dao.domain.ProductInfo;
import com.fmkj.order.dao.dto.ProductDto;
import com.fmkj.order.dao.queryVo.ProductQueryVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductMapper extends BaseMapper<ProductInfo> {


    List<ProductDto> queryProductPage(@Param("product") ProductQueryVo productQueryVo);
}