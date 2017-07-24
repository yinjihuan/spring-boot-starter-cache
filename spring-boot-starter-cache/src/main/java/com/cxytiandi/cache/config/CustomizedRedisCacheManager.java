package com.cxytiandi.cache.config;

import org.springframework.cache.Cache;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisOperations;

/**
 * 自定义Manager，可以在注解中加上具体的缓存时间，不设置则用全局的<br>
 * 在name后用##分割，然后加上过期的时间，单位秒
 * <pre>@Cacheable(value = "GoodsService.queryGoods##60", keyGenerator = "wiselyKeyGenerator")</pre>
 * @author yinjihuan
 */
public class CustomizedRedisCacheManager extends RedisCacheManager {

	@SuppressWarnings("rawtypes")
	public CustomizedRedisCacheManager(RedisOperations redisOperations) {
		super(redisOperations);
	}

	@Override
	public Cache getCache(String name) {
		String[] cacheParams = name.split("##");
	    String cacheName = cacheParams[0];
	    Long expirationSecondTime = this.computeExpiration(cacheName);
	    if(cacheParams.length > 1) {
	        expirationSecondTime = Long.parseLong(cacheParams[1]);
	        this.setDefaultExpiration(expirationSecondTime);
	    }
	    Cache cache = super.getCache(cacheName);
	    return cache;
	}

}
