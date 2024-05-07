package ksmart.mybatis.goods.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import ksmart.mybatis.goods.dto.Goods;

@Mapper
public interface GoodsMapper {

	// 상품상세목록 조회
	List<Goods> getGoodsDetailList();
	
	// 상품목록조회
	List<Goods> getGoodsList();
}
