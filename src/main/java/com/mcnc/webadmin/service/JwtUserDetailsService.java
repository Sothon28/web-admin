package com.mcnc.webadmin.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mcnc.webadmin.dao.JwtUserDAO;
import com.mcnc.webadmin.models.jwt.JwtRequest;
import com.mcnc.webadmin.models.jwt.JwtUserDTO;

@Service
public class JwtUserDetailsService implements UserDetailsService {
	
	@Autowired
	private JwtUserDAO userDAO;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		/*
		 * if ("javainuse".equals(username)) { return new User("javainuse",
		 * "$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6", new
		 * ArrayList<>()); } else { throw new
		 * UsernameNotFoundException("User not found with username: " + username); }
		 */
		
		JwtUserDTO userInfo = userDAO.getUserDetaislByUserName(userName);
		
		if(userInfo == null) {
			throw new UsernameNotFoundException("User not found with username: " + userName);
		}
		return new org.springframework.security.core.userdetails.User(userInfo.getUserName(), userInfo.getPassword(),
				new ArrayList<>());
		
	}
	

	public UserDetails loadUserInfoDetails(JwtRequest jwtRequest) throws UsernameNotFoundException {
		
		JwtUserDTO userInfo = userDAO.getUserDetails(jwtRequest);
		
		//MData ( userName, password )
		// JwtUserDTO tt = new JwtUserDTO();
		// tt.setUserName(user.get("")); 
	
		if(userInfo == null) {
			throw new UsernameNotFoundException("User not found with username: " + jwtRequest.getUserName());
		}
		
		return new org.springframework.security.core.userdetails.User(userInfo.getUserName(), userInfo.getPassword(),
				new ArrayList<>());
	}
	
	public long registerUser( JwtUserDTO user) {
		 return userDAO.registerUser(user);
	}
	
	
}