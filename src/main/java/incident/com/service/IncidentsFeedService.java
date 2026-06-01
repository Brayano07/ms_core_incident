package incident.com.service;


import incident.com.model.IncidentsFeedCollection;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IncidentsFeedService {

	Mono<IncidentsFeedCollection> createIncidentsFeed(IncidentsFeedCollection reservation);
	
	Flux<IncidentsFeedCollection> readAll(Integer page, Integer size);
}
