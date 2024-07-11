package com.jp.ht.interceptors;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.jp.ht.utils.JwtUtil;
import com.jp.ht.utils.ThreadLocalUtil;
@Component
public class LoginInterceptors implements HandlerInterceptor{
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//令牌验证
		String token = request.getHeader("Authorization");
		//验证token
				try {
					Map<String,Object> claims = JwtUtil.parseToken(token);
					
					ThreadLocalUtil.set(claims);
					return true;
				} catch (Exception e) {
					response.setStatus(401);
					return false;
				}
	}
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		ThreadLocalUtil.remove();
	}
}
