package com.mcnc.webadmin.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.mcnc.webadmin.common.util.MData;

@Service
public class RedisUtil {
	
	
//	private int timeExpried = 5; // Minutes 
	
	@Qualifier("redisTemplate")
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	@Autowired
	Gson gson;
	
	public void setValue(final String key, MData mData) {
		redisTemplate.opsForValue().set(key, gson.toJson(mData));
//		redisTemplate.expire(key, timeExpried , TimeUnit.MINUTES);
	}
	
	public MData getValue(final String key) {
		return gson.fromJson((String) redisTemplate.opsForValue().get(key), MData.class);
	}
	
	public void deleteKey(String key) {
		redisTemplate.delete(key);
	}
}