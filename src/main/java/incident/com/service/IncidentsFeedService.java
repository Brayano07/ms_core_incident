package incident.com.service;


import incident.com.dto.IncidentsFeedReq;
import incident.com.dto.IncidentsFeedResp;
import incident.com.model.IncidentsFeedCollection;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IncidentsFeedService {

	Mono<IncidentsFeedCollection> createIncidentsFeed(IncidentsFeedReq reservation);
	
	Flux<IncidentsFeedResp> readAll(Integer page, Integer size);
}
