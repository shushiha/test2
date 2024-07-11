package com.jp.ht.service;

import com.jp.ht.pojo.User;

public interface UserService {

	User findByUserName(String username);

	void register(String username, String password);
	//更新
	void update(User user);

	void updateAvatar(String avatarUrl);
    //密码更新
	void updataPwd(String newPwd);

}
