package com.jp.ht.service.impl;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jp.ht.mapper.UserMapper;
import com.jp.ht.pojo.User;
import com.jp.ht.service.UserService;
import com.jp.ht.utils.Md5Util;
import com.jp.ht.utils.ThreadLocalUtil;
@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserMapper userMapper;
	@Override
	public User findByUserName(String username) {
		
		return userMapper.findByUserName(username);
	}

	@Override
	public void register(String username, String password) {
		//加密处理
		String md5String = Md5Util.getMD5String(password);
		userMapper.add(username,md5String);
	}

	@Override
	public void update(User user) {
		user.setUpdateTime(LocalDateTime.now());
		userMapper.update(user);
	}

	@Override
	public void updateAvatar(String avatarUrl) {
		Map<String,Object> map = ThreadLocalUtil.get();
		Integer id = (Integer) map.get("id");
		userMapper.updateAvatar(avatarUrl,id);
	}

	@Override
	public void updataPwd(String newPwd) {
		Map<String,Object> map = ThreadLocalUtil.get();
		Integer id = (Integer) map.get("id");
		userMapper.updatePwd(Md5Util.getMD5String(newPwd),id);
	}

	

}
