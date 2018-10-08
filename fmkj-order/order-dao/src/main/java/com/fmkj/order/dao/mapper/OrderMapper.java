package com.fmkj.order.dao.mapper;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.fmkj.order.dao.domain.OrderInfo;
import com.fmkj.order.dao.dto.OrderDto;
import com.fmkj.order.dao.queryVo.OrderQueryVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderMapper extends BaseMapper<OrderInfo> {

    List<OrderDto> queryOrderPage(@Param("order") OrderQueryVo orderQueryVo);

    List<OrderDto> getOrderPageBySeller(@Param("order") OrderQueryVo orderQueryVo);
}