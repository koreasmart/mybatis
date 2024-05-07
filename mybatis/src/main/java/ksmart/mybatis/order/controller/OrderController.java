package ksmart.mybatis.order.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ksmart.mybatis.order.dto.Order;
import ksmart.mybatis.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
@Slf4j
public class OrderController {
	
	private final OrderService orderService;

	@GetMapping("/orderList")
	public String getOrderList(Model model) {
		
		List<Order> orderList = orderService.getOrderList();
		log.info("orderList: {}", orderList);
		
		model.addAttribute("title", "주문상세목록현황");
		model.addAttribute("orderList", orderList);
		
		return "order/orderList";
	}
}




