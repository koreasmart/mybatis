package ksmart.mybatis.order.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import ksmart.mybatis.order.dto.Order;

@Mapper
public interface OrderMapper {

	// 주문상세목록 현황
	List<Order> getOrderList();
}
