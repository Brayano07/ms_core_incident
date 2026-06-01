package incident.com.service;


import incident.com.dto.IncidentsFeedReq;
import incident.com.dto.IncidentsFeedResp;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IncidentsFeedBsService {

	Mono<Object> createIncidentsFeed(IncidentsFeedReq reservation);
	
	Flux<IncidentsFeedResp> readAll(Integer page, Integer size);
}
