package ksmart.mybatis.member.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ksmart.mybatis.member.dto.Member;
import ksmart.mybatis.member.dto.MemberLevel;
import ksmart.mybatis.member.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MemberService {

	private final MemberMapper memberMapper;
	
	/**
	 * 로그인이력 paging
	 * @param currentPage
	 * @return
	 */
	public Map<String, Object> getLoginHistory(int currentPage){
		
		// 1. 보여줄 행의 갯수
		int rowPerPage = 10;
		
		// 2. 행의 시작 번호
		int startRow = (currentPage - 1) * rowPerPage;
		
		// 3. 보여질 시작페이지, 마지막페이지 번호 설정
		int startPageNum = 1;
		int lastPageNum = 10;
		
		
		// 로그인이력 조회의 파라미터 설정
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("startRow", startRow);
		paramMap.put("rowPerPage", rowPerPage);
		
		List<Map<String, Object>> loginHistory = memberMapper.getLoginHistory(paramMap);
		double rowCnt = memberMapper.getLoginRowCnt();
		int lastPage = (int) Math.ceil(rowCnt/rowPerPage);
		
		// 마지막 번호 페이지 설정 : 마지막페이지가 10보다 작다면 마지막페이지로 설정
		lastPageNum = lastPage < 10 ? lastPage : lastPageNum;
		
		// 동적 페이지 설정
		if(currentPage > 6 && lastPage > 9) {
			startPageNum = currentPage - 5;
			lastPageNum = currentPage + 4;
			if(lastPageNum >= lastPage) {
				startPageNum = lastPage - 9;
				lastPageNum = lastPage;
			}
		}
		
		paramMap.clear();
		paramMap.put("loginHistory", loginHistory);
		paramMap.put("lastPage", lastPage);
		paramMap.put("startPageNum", startPageNum);
		paramMap.put("lastPageNum", lastPageNum);
		
		return paramMap;
	}
	
	public List<Member> getSellerList(){
		List<Member> sellerList = memberMapper.getSellerList();
		if(sellerList != null) log.info("sellerList size:{}", sellerList.size());
		log.info("sellerList: {}", sellerList);
		return sellerList;
	}
	
	/**
	 * 회원탈퇴
	 * @param memberLevel, memberId
	 */
	public void removeMember(int memberLevel, String memberId) {
		// 권한별 회원탈퇴
		// 1:관리자, 2:판매자, 3:구매자, 4:일반회원
		switch (memberLevel) {
			case 2 -> {
				memberMapper.removeOrderByCd(memberId);
				memberMapper.removeGoodsById(memberId);
			}
			case 3 -> {
				memberMapper.removeOrderById(memberId);
			}
		}
		memberMapper.removeLoginHistoryById(memberId);
		memberMapper.removeMemberById(memberId);
	}
	
	/**
	 * 회원정보 일치확인
	 * @param memberId, memberPw
	 * @return Map<String, Object> boolean true: 일치, false: 불일치, 일치 시 memberInfo
	 */
	public Map<String, Object> checkMemberInfo(String memberId, String memberPw) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		boolean isCheck = false;
		
		//Member memberInfo = getMemberInfoById(memberId);
		Member memberInfo = memberMapper.getMemberInfoById(memberId);
		
		if(memberInfo != null) {
			String checkPw = memberInfo.getMemberPw();
			if(checkPw != null && checkPw.equals(memberPw)) {
				isCheck = true;
				returnMap.put("memberInfo", memberInfo);
			}
		}
		
		returnMap.put("isCheck", isCheck);
		
		return returnMap;
	}
	
	/**
	 * 회원정보 수정
	 * @param member
	 */
	public void modifyMember(Member member) {
		memberMapper.modifyMember(member);
	}
	
	/**
	 * 회원정보조회
	 * @param 회원아이디 memberId
	 * @return Member
	 */
	public Member getMemberInfoById(String memberId) {
		Member memberInfo = memberMapper.getMemberInfoById(memberId);
		return memberInfo;
	}
	
	/**
	 * 회원등록
	 */
	public void addMember(Member member) {
		memberMapper.addMember(member);
	}
	
	/**
	 * 회원등급조회
	 * @return List<MemberLevel>
	 */
	public List<MemberLevel> getMemberLevelList(){
		return memberMapper.getMemberLevelList();
	}
	/**
	 * 중복아이디 체크
	 * @return boolean true: 중복 O, false: 중복 X
	 */
	public boolean memberIdCheck(String memberId) {
		return memberMapper.memberIdCheck(memberId);
	}
		
	/**
	 * 회원목록 조회
	 * @return List<Member>
	 */
	public List<Member> getMemberList(){
		List<Member> memberList = memberMapper.getMemberList();
		
		if(memberList != null) {
			memberList.forEach(member -> {
				int memberLevel = member.getMemberLevel();
				String memberLevelName;
				switch (memberLevel) {
					case 1 -> memberLevelName = "관리자";
					case 2 -> memberLevelName = "판매자";
					case 3 -> memberLevelName = "구매자";		
					default -> memberLevelName = "일반회원";
				}
				member.setMemberLevelName(memberLevelName);
			});
		}
		
		return memberList;
	}
}















