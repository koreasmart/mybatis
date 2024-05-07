package ksmart.mybatis.goods.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ksmart.mybatis.goods.dto.Goods;
import ksmart.mybatis.goods.service.GoodsService;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping(value="/goods")
@Slf4j
public class GoodsController {
	
	private final GoodsService goodsService;
	
	public GoodsController(GoodsService goodsService) {
		this.goodsService = goodsService;
	}

	@GetMapping("/goodsDetailList")
	public String getGoodsDetailList(Model model) {
		List<Goods> goodsDetailList = goodsService.getGoodsDetailList();
		log.info("goodsDetailList: {}", goodsDetailList);
		
		model.addAttribute("title", "상품상세목록조회");
		model.addAttribute("goodsDetailList", goodsDetailList);
		
		return "goods/goodsDetailList";
	}
	
	@GetMapping("/goodsList")
	public String getGoodsList(Model model) {
		List<Goods> goodsList = goodsService.getGoodsList();
		System.out.println("goodsList: " + goodsList);
		
		model.addAttribute("title", "상품목록조회");
		model.addAttribute("goodsList", goodsList);
		
		return "goods/goodsList";
	}
}









