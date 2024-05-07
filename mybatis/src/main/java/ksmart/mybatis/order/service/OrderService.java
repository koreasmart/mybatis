package ksmart.mybatis.order.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ksmart.mybatis.order.dto.Order;
import ksmart.mybatis.order.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

	private final OrderMapper orderMapper;
	
	/**
	 * 주문상세현황 목록
	 * @return List<Order> 
	 */
	public List<Order> getOrderList(){
		return orderMapper.getOrderList();
	}
}















