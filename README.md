# spring-boot-starter-cache

## 特点
- 快速集成 spring boot项目
- 实现了基于注解中的缓存时间设置（spring cache是不行的）

要使用的童鞋请先下载源码编译到本地仓库即可
```
<!-- cache -->
<dependency>
	<groupId>com.cxytiandi</groupId>
	<artifactId>spring-boot-starter-cache</artifactId>
        <version>1.0.0</version>
</dependency>
```

redis配置 参考：[https://github.com/yinjihuan/spring-boot-starter-cache/blob/master/spring-boot-starter-cache/src/main/java/com/cxytiandi/cache/config/CacheConfig.java](https://github.com/yinjihuan/spring-boot-starter-cache/blob/master/spring-boot-starter-cache/src/main/java/com/cxytiandi/cache/config/CacheConfig.java)
```
spring.redis.host=192.168.10.47
spring.redis.port=6379
```

使用在service实现类上加上下面的注解

value中的值格式为：key+过期时间（单位秒）

过期时间不加默认为一个小时，不加过期时间则格式就是：
```
@Cacheable(value = "c.f.o.a.l.s.LdRestService.findAll", keyGenerator = "wiselyKeyGenerator")
```

keyGenerator是固定的
```
@Cacheable(value = "c.f.o.a.l.s.LdRestService.findAll##60", keyGenerator = "wiselyKeyGenerator")
public List<LouDongBo> findAll() {
    List<LouDong> list = ldRpcService.findAll();
    return FangjiaBeanUtils.copyProperties(list, LouDongBo.class);
}
 ```

# 作者
- 尹吉欢 1304489315@qq.com
- 博客 http://cxytiandi.com/blogs/yinjihuan
