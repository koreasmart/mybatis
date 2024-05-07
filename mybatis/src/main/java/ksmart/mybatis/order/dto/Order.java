package ksmart.mybatis.order.dto;

import ksmart.mybatis.goods.dto.Goods;
import ksmart.mybatis.member.dto.Member;
import lombok.Data;

@Data
public class Order {
	
	private String orderNum;
	private String orderId;
	private String orderGoodsCode;
	private int orderAmount;
	private int orderTotal;
	private String orderRegDate;
	
	private Goods goodsInfo;
	private Member memberInfo;
}
