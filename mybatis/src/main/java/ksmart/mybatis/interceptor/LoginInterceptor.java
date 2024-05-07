package ksmart.mybatis.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor{
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		log.info("preHandle() 메소드 호출");
		HttpSession session = request.getSession();
		String sessionId = (String) session.getAttribute("SID");
		
		if(sessionId == null) {
			response.sendRedirect("/member/login");
			return false;
		}
		
		int memberLevel = (int) session.getAttribute("SLEVEL");
		
		// 판매자, 구매자, 일반회원
		if(memberLevel > 1) {
			String uri = request.getRequestURI();
			if( 	uri.indexOf("/member/memberList") 	> -1
				||	uri.indexOf("/member/modify") 	  	> -1	
				||	uri.indexOf("/member/remove") 	  	> -1	
				||	uri.indexOf("/member/seller") 	  	> -1	
				||	uri.indexOf("/member/order") 	  	> -1	
				||	uri.indexOf("/member/loginHistory") > -1	) {
				
				response.sendRedirect("/");
				return false;
			}
		}
		
		return HandlerInterceptor.super.preHandle(request, response, handler);
	}
	
}
