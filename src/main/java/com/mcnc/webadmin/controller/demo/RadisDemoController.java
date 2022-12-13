package com.mcnc.webadmin.controller.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mcnc.webadmin.common.util.MData;
import com.mcnc.webadmin.models.jwt.JwtUserDTO;
import com.mcnc.webadmin.util.RedisUtil;

@RestController
@RequestMapping("/redis")
public class RadisDemoController {
	
	@Autowired
	private RedisUtil redisUtil;
	
	@GetMapping("/set")
	public MData setValue() {
		JwtUserDTO user = new JwtUserDTO();
		user.setUserName("Dara");
		user.setUserId("1");
		
		MData mUser = new MData();
		mUser.set("user", user);
		redisUtil.setValue("userInfo", mUser);
		return mUser;
		
	}
	@GetMapping("/get")
	public MData getValue() {
		System.out.println("=================================");
		MData userInfo = redisUtil.getValue("userInfo");
		System.out.println(userInfo);
		return userInfo;
	}

	@GetMapping("/getToken")
	public MData getTokenValue() {
		System.out.println("================Token Info =================");
		MData token = redisUtil.getValue("token");
		System.out.println(token);
		return token;
	}
}
