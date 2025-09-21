package com.shortify.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.shortify.entity.Url;

@Configuration
public class RedisConfig {

	@Bean
	RedisTemplate<String, Url> redisTemplate(RedisConnectionFactory connectionFactory) {
		RedisTemplate<String, Url> template = new RedisTemplate<>();
		template.setConnectionFactory(connectionFactory);

		// Key as String
		template.setKeySerializer(new StringRedisSerializer());

		// Value as JSON (serialize Url entity)
		template.setValueSerializer(new GenericJackson2JsonRedisSerializer());

		// For hash operations
		template.setHashKeySerializer(new StringRedisSerializer());
		template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

		template.afterPropertiesSet();
		return template;
	}
}
