package incident.com.config;

import java.time.Duration;
import java.util.List;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;

import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;


import lombok.extern.slf4j.Slf4j;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import incident.com.dto.IncidentsFeedResp;

import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

@Configuration
@EnableCaching
@Slf4j
public class RedisConfig {
	
	
    @Value("localhost")
    private String redisHost;

    @Value("6379")
    private int redisPort;

    @Value("123456")
    private String redisPassword;

    @Value("0")
    private int redisDatabase;
	    
	@Bean
	public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
		return RedisCacheManager.builder(connectionFactory)
				.cacheDefaults(RedisCacheConfiguration.defaultCacheConfig())
				.build();
	}

	@Bean
    public LettuceConnectionFactory  lettuceConnectionFactory()
    {
    	RedisStandaloneConfiguration redisConfig =
    			new RedisStandaloneConfiguration(redisHost, redisPort);
    	
    	redisConfig.setPassword(redisPassword);
    	redisConfig.setDatabase(0);
    	
    	LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
    			.commandTimeout(Duration.ofSeconds(3))
    			.shutdownTimeout(Duration.ofMillis(150))
    			.build();
    	
    	return new LettuceConnectionFactory(redisConfig,clientConfig);
    	
    }
	    
	    
	    
	@Bean
	public ReactiveRedisTemplate<String, IncidentsFeedResp> reactiveRedisTemplate(
			ReactiveRedisConnectionFactory connectionFactory,
			ObjectMapper objectMapper) {

		Jackson2JsonRedisSerializer<IncidentsFeedResp> serializer =
				new Jackson2JsonRedisSerializer<>(objectMapper, IncidentsFeedResp.class);

		RedisSerializationContext<String, IncidentsFeedResp> context =
				RedisSerializationContext.<String, IncidentsFeedResp>newSerializationContext()
						.key(StringRedisSerializer.UTF_8)
						.value(serializer)
						.hashKey(StringRedisSerializer.UTF_8)
						.hashValue(serializer)
						.build();

		return new ReactiveRedisTemplate<>(connectionFactory, context);
	}

	@Bean
	public ReactiveRedisTemplate<String, List<IncidentsFeedResp>> reactiveRedisListTemplate(
			ReactiveRedisConnectionFactory connectionFactory,
			ObjectMapper objectMapper) {

		JavaType type = objectMapper.getTypeFactory()
				.constructCollectionType(List.class, IncidentsFeedResp.class);

		Jackson2JsonRedisSerializer<List<IncidentsFeedResp>> serializer =
				new Jackson2JsonRedisSerializer<>(objectMapper, type);

		RedisSerializationContext<String, List<IncidentsFeedResp>> context =
				RedisSerializationContext.<String, List<IncidentsFeedResp>>newSerializationContext()
						.key(StringRedisSerializer.UTF_8)
						.value(serializer)
						.hashKey(StringRedisSerializer.UTF_8)
						.hashValue(serializer)
						.build();

		return new ReactiveRedisTemplate<>(connectionFactory, context);
	}
	
	
	@EventListener(ApplicationReadyEvent.class)
	public void veriifyRedisConnection()
	{
		log.info("Connection exit!");
	}
	
}
