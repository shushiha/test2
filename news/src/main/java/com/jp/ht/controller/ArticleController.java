package com.jp.ht.controller;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jp.ht.pojo.Result;
import com.jp.ht.utils.JwtUtil;

@RestController
@RequestMapping("/article")
public class ArticleController {
	@GetMapping("/list")
	public Result<String> list(/*
								 * @RequestHeader(name ="Authorization") String token, HttpServletResponse
								 * response
								 */){
//		//验证token
//		try {
//			Map<String,Object> claims = JwtUtil.parseToken(token);
//			return Result.success("すべての内容表示");
//		} catch (Exception e) {
//			response.setStatus(401);
//			return Result.error("未登録");
//		}
		return Result.success("すべての内容表示");
	}
}
