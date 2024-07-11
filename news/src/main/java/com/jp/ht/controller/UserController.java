package com.jp.ht.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jp.ht.pojo.Result;
import com.jp.ht.pojo.User;
import com.jp.ht.service.UserService;
import com.jp.ht.utils.JwtUtil;
import com.jp.ht.utils.Md5Util;
import com.jp.ht.utils.ThreadLocalUtil;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {
	@Autowired
	private UserService userService;

	@PostMapping("/register")
	// spring Validation通常用于验证输入数据是否合法
	public Result register(@Pattern(regexp = "^\\S{5,16}$") String username,
			@Pattern(regexp = "^\\S{5,16}$") String password) {
		User u = userService.findByUserName(username);
		if (u == null) {
			userService.register(username, password);
			return Result.success();
		} else {
			return Result.error("ユーザーが存在している");
		}
	}

	@PostMapping("/login")
	public Result<String> login(@Pattern(regexp = "^\\S{5,16}$") String username,
			@Pattern(regexp = "^\\S{5,16}$") String password) {
		User loginUser = userService.findByUserName(username);
		if (loginUser == null) {
			return Result.error("ユーザー名が間違っています");
		}
		if (Md5Util.getMD5String(password).equals(loginUser.getPassword())) {
			Map<String, Object> claims = new HashMap<>();
			claims.put("id", loginUser.getId());
			claims.put("username", loginUser.getUsername());
			String token = JwtUtil.genToken(claims);
			return Result.success(token);
		}
		return Result.error("パスワードが間違ってます");
	}

	@GetMapping("/userInfo")
	public Result<User> userInfo(/* @RequestHeader(name="Authorization") String token */) {
//		Map<String, Object> map = JwtUtil.parseToken(token);
//		String username = (String)map.get("username");
		Map<String, Object> map = ThreadLocalUtil.get();
		String username = (String) map.get("username");
		User user = userService.findByUserName(username);
		return Result.success(user);
	}

	@PutMapping("/update")
	public Result update(@RequestBody @Validated User user) {
		userService.update(user);
		return Result.success();
	}

	@PatchMapping("/updateAvatar")
	public Result updateAvatar(@RequestParam @URL String avatarUrl) {
		userService.updateAvatar(avatarUrl);
		return Result.success();
	}

	@PatchMapping("/updatePwd")
	public Result updatePwd(@RequestBody Map<String,String> params) {
		//校验参数
		String oldPwd = params.get("old_pwd");
		String newPwd = params.get("new_pwd");
		String rePwd = params.get("re_pwd");
		
	if(!StringUtils.hasLength(oldPwd)||!StringUtils.hasLength(newPwd)||!StringUtils.hasLength(rePwd)) {
		return Result.error("内容が間違ってます");
	}
	Map<String,Object> map = ThreadLocalUtil.get();
	String username = (String) map.get("username");
	User loginUser = userService.findByUserName(username);
	if(!loginUser.getPassword().equals(Md5Util.getMD5String(oldPwd))) {
		return Result.error("入力したパスワードが間違ってます");
	}
	if(!rePwd.equals(newPwd)) {
		return Result.error("上とのパスワードが間違ってます");
	}
	userService.updataPwd(newPwd);
	return Result.success();
	
	  }
}
