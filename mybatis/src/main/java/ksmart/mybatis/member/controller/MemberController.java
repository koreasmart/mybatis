package ksmart.mybatis.member.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import ksmart.mybatis.member.dto.Member;
import ksmart.mybatis.member.dto.MemberLevel;
import ksmart.mybatis.member.service.MemberService;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping(value="/member")
@Slf4j
public class MemberController {
	
	private final MemberService memberService;
	
	public MemberController(MemberService memberService) {
		this.memberService = memberService;
	}
	
	@GetMapping("/logout")
	public String actionLogout(HttpSession session) {

		session.invalidate();
		
		return "redirect:/member/login";
	}
	
	@PostMapping("/login")
	public String actionLogin(@RequestParam(value="memberId") String memberId
							 ,@RequestParam(value="memberPw") String memberPw
							 ,HttpSession session
							 ,RedirectAttributes reAttr) {
		String viewName = "redirect:/";
		
		// 회원 비밀번호가 일치하지 않으면 로그인폼 페이지 일치하면 메인페이지로...... 
		Map<String, Object> returnMap = memberService.checkMemberInfo(memberId, memberPw);
		boolean isCheck = (boolean) returnMap.get("isCheck");
		if(isCheck) {
			Member memberInfo = (Member) returnMap.get("memberInfo");
			if(memberInfo != null) {
				int memberLevel = memberInfo.getMemberLevel();
				// 회원정보 세션 객체에 속성 등록
				session.setAttribute("SID", memberId);
				session.setAttribute("SNAME", memberInfo.getMemberName());
				session.setAttribute("SLEVEL", memberLevel);
			}
		}else {
			viewName = "redirect:/member/login";
			String msg = "회원의 정보가 일치하지 않습니다.";
			// /member/login?msg=회원의 정보가 일치하지 않습니다.
			reAttr.addAttribute("msg", msg);
		}
		return viewName;
	}
	
	@GetMapping("/login")
	public String getLoginForm(Model model
							  ,@RequestParam(value="msg", required = false) String msg) {
		
		model.addAttribute("title", "로그인");
		if(msg != null) model.addAttribute("msg", msg);
		
		return "member/loginForm";
	}
	
	// /member/loginHistory?currentPage=2 : currentPage=2
	// /member/loginHistory : currentPage=1
	// /member/loginHistory?currentPage=1 : currentPage=1
	@GetMapping("/loginHistory")
	public String getLoginHistory(Model model
								 ,@RequestParam(value="currentPage", required = false, defaultValue = "1") int currentPage) {
		Map<String, Object> resultMap = memberService.getLoginHistory(currentPage);

		@SuppressWarnings("unchecked")
		List<Map<String, Object>> loginHistory = (List<Map<String, Object>>) resultMap.get("loginHistory");
		int lastPage = (int) resultMap.get("lastPage");
		int startPageNum = (int) resultMap.get("startPageNum");
		int lastPageNum = (int) resultMap.get("lastPageNum");
		
		model.addAttribute("title", "로그인이력조회");
		model.addAttribute("loginHistory", loginHistory);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("lastPage", lastPage);
		model.addAttribute("startPageNum", startPageNum);
		model.addAttribute("lastPageNum", lastPageNum);
		
		return "member/loginHistory";
	}
	
	@GetMapping("/sellerList")
	public String getSellerList(Model model) {
		List<Member> sellerList = memberService.getSellerList();
		
		model.addAttribute("title", "판매자목록");
		model.addAttribute("sellerList", sellerList);
		
		return "member/sellerList";
	}
	
	
	@PostMapping("/removeMember")
	public String removeMember(@RequestParam(value="memberId") String memberId
							  ,@RequestParam(value="memberPw") String memberPw
							  ,RedirectAttributes reAttr) {
		String viewName = "redirect:/member/memberList";
		
		// 실습. 회원 비밀번호가 일치하지 않으면 회원탈퇴폼 페이지 일치하면 회원목록 페이지로...... 
		Map<String, Object> returnMap = memberService.checkMemberInfo(memberId, memberPw);
		boolean isCheck = (boolean) returnMap.get("isCheck");
		if(isCheck) {
			Member memberInfo = (Member) returnMap.get("memberInfo");
			if(memberInfo != null) {
				int memberLevel = memberInfo.getMemberLevel();
				// 회원탈퇴
				memberService.removeMember(memberLevel, memberId);
			}
		}else {
			viewName = "redirect:/member/removeMember";
			String msg = "회원의 정보가 일치하지 않습니다.";
			// /member/removeMember?memberId=id001&msg=회원의 정보가 일치하지 않습니다.
			reAttr.addAttribute("memberId", memberId);
			reAttr.addAttribute("msg", msg);
		}
		
		return viewName;
	}
	
	@GetMapping("/removeMember")
	public String removeMember(@RequestParam(value="memberId") String memberId
							  ,@RequestParam(value="msg", required = false) String msg
							  ,Model model) {
		
		model.addAttribute("title", "회원탈퇴");
		model.addAttribute("memberId", memberId);
		if(msg != null) model.addAttribute("msg", msg);
		
		return "member/removeMember";
	}
	
	@PostMapping("/modifyMember")
	public String modifyMember(Member member) {
		log.info("회원수정: {}", member);
		
		memberService.modifyMember(member);
		
		return "redirect:/member/memberList";
	}
	
	
	@GetMapping("/modifyMember")
	public String modifyMember(@RequestParam(value="memberId") String memberId
							  ,Model model) {
		log.info("수정화면 memberId : {}", memberId);
		Member memberInfo = memberService.getMemberInfoById(memberId);
		List<MemberLevel> memberLevelList = memberService.getMemberLevelList();
		
		model.addAttribute("title", "회원수정");
		model.addAttribute("memberInfo", memberInfo);
		model.addAttribute("memberLevelList", memberLevelList);
		
		return "member/modifyMember";
	}
	
	
	/**
	 * 과제: 2024년 04월 19일 회원가입 완성(회원가입 버튼 type 속성: button으로 변경)
	 * 회원폼안의 요소들 유효성검사 진행 후 submit 처리
	 * 회원가입 처리 후 회원목록페이지로 전환
	 * 커맨드 객체 : 화면 폼양식 data 전송시 name 속성과 DTO의 프로퍼티명과 동일하다면 전송Data 바인딩하는 객체
	 */
	@PostMapping("/addMember")
	public String addMember(Member member) {
		
		System.out.println("회원가입 화면에서 입력받은 data: " + member);
		
		// 회원가입처리 과제
		memberService.addMember(member);
		
		return "redirect:/member/memberList";
	}

	/**
	 * 회원아이디 중복체크
	 * @param memberId
	 * @return boolean  true: 중복 O, false: 중복 X
	 */
	@PostMapping("/memberIdCheck")
	@ResponseBody
	public boolean memberIdCheck(@RequestParam(value="memberId") String memberId) {
		System.out.println("memberId : " + memberId);
		boolean isMember = memberService.memberIdCheck(memberId);
		System.out.println("회원중복여부: " + isMember);
		return isMember;
	}
	
	/**
	 * 회원등록 화면
	 * @param model
	 */
	@GetMapping("/addMember")
	public String addMember(Model model) {
		List<MemberLevel> memberLevelList = memberService.getMemberLevelList();
		System.out.println("memberLevelList: " + memberLevelList);
		
		model.addAttribute("title", "회원등록");
		model.addAttribute("memberLevelList", memberLevelList);
		
		return "member/addMember";
	}
	
	/**
	 * 회원목록 조회
	 * @param model
	 */
	@GetMapping("/memberList")
	public String getMemberList(Model model) {
		
		List<Member> memberList = memberService.getMemberList();
		log.info("memberList: {}", memberList);
		System.out.println("memberList: " + memberList);
		
		model.addAttribute("title", "회원목록");
		model.addAttribute("memberList", memberList);
		
		return "member/memberList";
	}
}














