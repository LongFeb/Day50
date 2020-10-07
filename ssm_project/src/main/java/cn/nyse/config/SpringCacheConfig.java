package cn.nyse.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Arrays;

/**
 * spring整合缓存技术：
 * 1.spring data redis整合redis作为缓存
 * 2.spring cache 操作缓存
 * a.声明缓存管理器对象CacheManager -创建缓存对象，自动管理缓存对象
 * b.开启缓存注解支持
 * c.需要放入缓存的对象类型，需要实现序列化接口
 * d.在需要管理缓存的服务层逻辑方法上，添加缓存注解
 */
@Configuration
@PropertySource(value = "classpath:redis.properties",encoding = "utf-8")
@EnableCaching
public class SpringCacheConfig {
    @Bean
    public JedisConnectionFactory getRedisConnection(@Value("${host}")String host, @Value("${port}")int port, @Value("${password}")String password, @Value("${minIdle}")int minIdle){
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setHostName(host);//设置主机ip
        jedisConnectionFactory.setPort(port);//设置端口号
        jedisConnectionFactory.setPassword(password);

        JedisPoolConfig config = new JedisPoolConfig();
        config.setMinIdle(minIdle);
        jedisConnectionFactory.setPoolConfig(config);

        return jedisConnectionFactory;
    }


    @Bean("redisTemplate")
    public RedisTemplate<Object,Object> getRedisTemplate(RedisConnectionFactory connectionFactory){
        RedisTemplate <Object, Object> redisTemplate = new RedisTemplate <>();
        redisTemplate.setConnectionFactory(connectionFactory);
        StringRedisSerializer keySerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(keySerializer);
        GenericJackson2JsonRedisSerializer jsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
        redisTemplate.setValueSerializer(jsonRedisSerializer);

        //设置hash类型
        redisTemplate.setHashKeySerializer(keySerializer);
        redisTemplate.setHashValueSerializer(jsonRedisSerializer);

        return redisTemplate;
    }

    //创建spring缓存管理器
    @Bean
    public RedisCacheManager getRedisCacheManager(RedisTemplate redisTemplate){
        RedisCacheManager redisCacheManager = new RedisCacheManager(redisTemplate);
        redisCacheManager.setDefaultExpiration(60);
        redisCacheManager.setCacheNames(Arrays.asList(new String[]{"officeCache","resourceCache"}));

        return redisCacheManager;
    }

}
