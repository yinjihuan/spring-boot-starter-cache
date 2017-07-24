package com.cxytiandi.cache.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.annotation.CachingConfigurerSupport;

/**
 * 缓存接口数据<br>
 * 在需要缓存的servie方法上加上@Cacheable(value = "GoodsService.queryGoods", keyGenerator = "wiselyKeyGenerator")
 * @author yinjihuan
 *
 */
@ConfigurationProperties(prefix = "spring.redis")
public class CacheConfig extends CachingConfigurerSupport {
    private String host;
    private int port;
    private String password;
    private int maxActive = 50;
    private int maxIdle = 50;
    private int minIdle = 10;
    private int maxWait = 10000;
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getMaxActive() {
		return maxActive;
	}
	public void setMaxActive(int maxActive) {
		this.maxActive = maxActive;
	}
	public int getMaxIdle() {
		return maxIdle;
	}
	public void setMaxIdle(int maxIdle) {
		this.maxIdle = maxIdle;
	}
	public int getMinIdle() {
		return minIdle;
	}
	public void setMinIdle(int minIdle) {
		this.minIdle = minIdle;
	}
	public int getMaxWait() {
		return maxWait;
	}
	public void setMaxWait(int maxWait) {
		this.maxWait = maxWait;
	}

}
