package ksmart.mybatis.goods.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ksmart.mybatis.goods.dto.Goods;
import ksmart.mybatis.goods.mapper.GoodsMapper;

@Service
@Transactional
public class GoodsService {

	private final GoodsMapper goodsMapper;
	
	public GoodsService(GoodsMapper goodsMapper) {
		this.goodsMapper = goodsMapper;
	}
	
	/**
	 * 상품상세목록 조회
	 * @return List<Goods> 
	 */
	public List<Goods> getGoodsDetailList(){
		return goodsMapper.getGoodsDetailList();
	}
	
	/**
	 * 상품목록 조회
	 * @return List<Goods>
	 */
	public List<Goods> getGoodsList(){
		
		List<Goods> goodsList = goodsMapper.getGoodsList();
		
		return goodsList; 
	}
}















