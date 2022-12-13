package com.mcnc.webadmin.config.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableRedisRepositories(value = "com.tericcabrel.springbootcaching.repositories")
public class RedisConfiguration {

	@Value("${spring.redis.host}")
	private String REDIS_HOSTNAME;

	@Value("${spring.redis.port}")
	private int REDIS_PORT;

	@Bean
	public RedisConnectionFactory redisConnectionFactory() {
		RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration(REDIS_HOSTNAME, REDIS_PORT);
		JedisClientConfiguration jedisClientConfiguration = JedisClientConfiguration.builder().build();
		return new JedisConnectionFactory(configuration, jedisClientConfiguration);
	}

	@Bean
	public RedisTemplate<?, ?> redisTemplate() {
		RedisTemplate<?, ?> template = new RedisTemplate<>();
		template.setKeySerializer(new StringRedisSerializer());
		template.setValueSerializer(new JdkSerializationRedisSerializer());
		template.setHashKeySerializer(new GenericToStringSerializer<String>(String.class));
		template.setHashValueSerializer(new JdkSerializationRedisSerializer());
		
		template.setConnectionFactory(redisConnectionFactory());
		return template;
	}
}
