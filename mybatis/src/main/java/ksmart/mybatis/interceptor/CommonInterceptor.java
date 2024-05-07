package ksmart.mybatis.interceptor;

import java.util.Set;
import java.util.StringJoiner;

import org.springframework.boot.logging.LogLevel;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CommonInterceptor implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// localhost?memberId=id001&memberPw=pw002
		// request.getParameter("memberId"); // memberId에 일치하는 값을 반환 id001
		// Map<String, String[]> parameterMap
		// parameterMap.put("memberId","id001")
		// parameterMap.put("memberPw","pw002")
		Set<String> keySet = request.getParameterMap().keySet();
		
		// StringJoiner(", ")
		StringJoiner param = new StringJoiner(", ");
		
		for(String key : keySet) {
			param.add(key + ": " + request.getParameter(key));
		}
		
		// TODO 주소요청시에 어떤 사용자 ip 주소를 요청을 하고 요청 파라미터가 어떻게 되는 지 로그기록
		log.info("ACCESS INFO START =================================");
		log.info("PORT :::: {}", request.getLocalPort());
		log.info("ServerName :::: {}", request.getServerName());
		log.info("Method :::: {}", request.getMethod());
		log.info("URI :::: {}", request.getRequestURI());
		log.info("CLIENT IP :::: {}", request.getRemoteAddr());
		log.info("Parameter :::: {}", param);
		log.info("ACCESS INFO END =================================");
		
		
		return HandlerInterceptor.super.preHandle(request, response, handler);
	}
}














