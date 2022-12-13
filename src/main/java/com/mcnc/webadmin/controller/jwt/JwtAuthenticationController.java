package com.mcnc.webadmin.controller.jwt;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mcnc.webadmin.common.util.MData;
import com.mcnc.webadmin.models.jwt.JwtRequest;
import com.mcnc.webadmin.models.jwt.JwtUserDTO;
import com.mcnc.webadmin.service.JwtUserDetailsService;
import com.mcnc.webadmin.util.JwtTokenUtil;
import com.mcnc.webadmin.util.RedisUtil;

import io.jsonwebtoken.impl.DefaultClaims;

@RestController
@CrossOrigin
@RequestMapping("/auth")
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private RedisUtil redisUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public MData createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
		String token = "";
		MData tokenInfo = new MData();
		try {
			final UserDetails userDetails = userDetailsService.loadUserInfoDetails(authenticationRequest);

			SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyMMddHHmmss");
			Date date = new Date();

			MData getTokenInfo = new MData();
			getTokenInfo = redisUtil.getValue(authenticationRequest.getUserName());
			
			
			if (getTokenInfo != null) {
				long currentDateTime = Long.parseLong(dateFormatter.format(date));
				long tokenExpirationDateTime = Long.parseLong(getTokenInfo.getString("expirationDate"));
				long isExpired = currentDateTime - tokenExpirationDateTime;
				
				if(isExpired < 0 ) {
					token = getTokenInfo.getString("token");
					tokenInfo.put("token",token);
					tokenInfo.put("expirationDate",getTokenInfo.getString("expirationDate"));
					tokenInfo.put("issuedDate",getTokenInfo.getString("expirationDate")); 
				}else {
					token = jwtTokenUtil.generateToken(userDetails);
					tokenInfo.put("token",token);
					tokenInfo.put("expirationDate",dateFormatter.format(jwtTokenUtil.getExpirationDateFromToken(token)));
					tokenInfo.put("issuedDate",dateFormatter.format(jwtTokenUtil.getIssuedAtDateFromToken(token)));
					
				}
				
			} else {

				token = jwtTokenUtil.generateToken(userDetails);

				tokenInfo.set("token", token);
				tokenInfo.set("expirationDate", dateFormatter.format(jwtTokenUtil.getExpirationDateFromToken(token)));
				tokenInfo.set("issuedDate", dateFormatter.format(jwtTokenUtil.getIssuedAtDateFromToken(token)));

				// Set Token key by userName
				redisUtil.setValue(userDetails.getUsername(), tokenInfo);
			}


		} catch (Exception e) {
			throw e;
		}

		return tokenInfo;
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<?> saveUser(@RequestBody JwtUserDTO user) throws Exception {
		return ResponseEntity.ok(userDetailsService.registerUser(user));
	}
	
	@RequestMapping(value = "/refreshtoken", method = RequestMethod.GET)
	public ResponseEntity<?> refreshtoken(HttpServletRequest request) throws Exception {
		// From the HttpRequest get the claims
		DefaultClaims claims = (io.jsonwebtoken.impl.DefaultClaims) request.getAttribute("claims");

		Map<String, Object> expectedMap = getMapFromIoJsonwebtokenClaims(claims);
		String token = jwtTokenUtil.doGenerateRefreshToken(expectedMap, expectedMap.get("sub").toString());
		return ResponseEntity.ok(token);
	}

	public Map<String, Object> getMapFromIoJsonwebtokenClaims(DefaultClaims claims) {
		Map<String, Object> expectedMap = new HashMap<String, Object>();
		for (java.util.Map.Entry<String, Object> entry : claims.entrySet()) {
			expectedMap.put(entry.getKey(), entry.getValue());
		}
		return expectedMap;
	}

}
