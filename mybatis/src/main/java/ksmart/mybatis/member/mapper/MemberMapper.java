package ksmart.mybatis.member.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import ksmart.mybatis.member.dto.Member;
import ksmart.mybatis.member.dto.MemberLevel;

@Mapper
public interface MemberMapper {
	// 로그인테이블의 총 행의 갯수
	int getLoginRowCnt();
	
	// 로그인이력 조회
	List<Map<String, Object>> getLoginHistory(Map<String, Object> paramMap);
	
	// 판매자현황 조회
	List<Member> getSellerList();
	
	// 모든회원: 회원탈퇴
	int removeMemberById(String memberId);
	
	// 모든 회원: 로그인이력삭제
	int removeLoginHistoryById(String memberId);
	
	// 구매자: 주문이력삭제(구매자아이디)
	int removeOrderById(String orderId);
	
	// 판매자: 상품삭제
	int removeGoodsById(String sellerId);
	
	// 판매자: 주문이력삭제(상품코드)
	int removeOrderByCd(String sellerId);
	
	// 회원정보수정
	int modifyMember(Member member);
	
	// 회원정보조회
	Member getMemberInfoById(String memberId);
	
	// 회원등록
	int addMember(Member member);
	
	// 중복아이디체크
	boolean memberIdCheck(String memberId);
	
	// 회원등급조회
	List<MemberLevel> getMemberLevelList();
	
	// 회원목록조회
	List<Member> getMemberList();
}
