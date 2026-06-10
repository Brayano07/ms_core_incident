package incident.com.service;

import java.time.Duration;
import java.util.List;

import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;


import incident.com.dto.IncidentsFeedResp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class CatalogCacheService {

	    private final ReactiveRedisTemplate<String, IncidentsFeedResp> redisTemplate;
	    private final ReactiveRedisTemplate<String, List<IncidentsFeedResp>> redisListTemplate;

	    //private static final Duration DEFAULT_TTL = Duration.ofHours(1);
	    private static final Duration DEFAULT_TTL = Duration.ofMinutes(5);
	    private static final String KEY_PREFIX = "incidentsFeed:";
	    
	    
	    
	    public Mono<Boolean> saveAllCache(List<IncidentsFeedResp> incidents) {

	        return redisListTemplate.opsForValue()
	                .set(KEY_PREFIX + "all", incidents, DEFAULT_TTL);
	    }
	    
	    
	    public Flux<IncidentsFeedResp> getAllCache() {

	        return redisListTemplate.opsForValue()
	                .get(KEY_PREFIX + "all")
	                .flatMapMany(Flux::fromIterable);
	    }

}
