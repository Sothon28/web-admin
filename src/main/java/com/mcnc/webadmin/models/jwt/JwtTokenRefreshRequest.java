package com.mcnc.webadmin.models.jwt;

public class JwtTokenRefreshRequest {
	
	private String refreshToken;

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
}
