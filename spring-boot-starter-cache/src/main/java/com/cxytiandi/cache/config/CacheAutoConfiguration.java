package com.cxytiandi.cache.config;

import java.lang.reflect.Method;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.StringUtils;

import com.cxytiandi.cache.util.ClassUtil;
import com.cxytiandi.cache.util.JsonUtils;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
@EnableConfigurationProperties(CacheConfig.class)
public class CacheAutoConfiguration {

	@Autowired
	private CacheConfig config;

	@Bean
	public JedisConnectionFactory jedisConnectionFactory() {
		JedisConnectionFactory factory = new JedisConnectionFactory();
		factory.setHostName(config.getHost());
		factory.setPort(config.getPort());
		if (StringUtils.hasText(config.getPassword())) {
			factory.setPassword(config.getPassword());
		}
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxTotal(config.getMaxActive());
		jedisPoolConfig.setMaxIdle(config.getMaxIdle());
		jedisPoolConfig.setMinIdle(config.getMinIdle());
		jedisPoolConfig.setMaxWaitMillis(config.getMaxWait());
		factory.setPoolConfig(jedisPoolConfig);
		return factory;
	}

	@SuppressWarnings("rawtypes")
	@Bean
	public CacheManager cacheManager(RedisTemplate redisTemplate) {
		CustomizedRedisCacheManager cacheManager = new CustomizedRedisCacheManager(redisTemplate);
		cacheManager.setDefaultExpiration(1000 * 60 * 60);
		return cacheManager;
	}

	@Bean
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
		RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
		template.setConnectionFactory(factory);
		setSerializer(template);
		template.afterPropertiesSet();
		return template;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void setSerializer(RedisTemplate<String, Object> template) {
		Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
		ObjectMapper om = new ObjectMapper();
		om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		jackson2JsonRedisSerializer.setObjectMapper(om);
		template.setValueSerializer(jackson2JsonRedisSerializer);
		template.setKeySerializer(new StringRedisSerializer());
	}

	/**
	 * key自动生成类
	 * @return
	 */
	@Bean
	public KeyGenerator wiselyKeyGenerator() {
		return new KeyGenerator() {
			public Object generate(Object target, Method method, Object... params) {
				StringBuilder sb = new StringBuilder();
				sb.append(ClassUtil.getShortClassName(target.getClass().getName()));
				sb.append(".");
				sb.append(method.getName());
				if (params != null && params.length > 0) {
					for (Object obj : params) {
						sb.append(".");
						sb.append(JsonUtils.toJson(obj).replaceAll("\"", ""));
					}
				}
				return sb.toString();
			}
		};
	}
}
