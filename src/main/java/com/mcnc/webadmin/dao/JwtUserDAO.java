package com.mcnc.webadmin.dao;

import org.apache.ibatis.annotations.Mapper;

import com.mcnc.webadmin.models.jwt.JwtRequest;
import com.mcnc.webadmin.models.jwt.JwtUserDTO;

@Mapper
public interface JwtUserDAO {

	public JwtUserDTO getUserDetails(JwtRequest jwtRequest);
	public JwtUserDTO getUserDetaislByUserName(String userName);
	public Long registerUser(JwtUserDTO user);
	
	
}
