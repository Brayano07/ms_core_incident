package incident.com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import incident.com.dto.IncidentsFeedReq;
import incident.com.dto.IncidentsFeedResp;
import incident.com.mapper.IncidentsFeedMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class IncidentsFeedBsServiceImpl implements IncidentsFeedBsService{
	
	@Autowired
	private IncidentsFeedService incidentsFeedService;
	
	@Autowired
	private IncidentsFeedMapper  incidentsFeedMapper;
	
	@Override
	public Mono<Object> createIncidentsFeed(IncidentsFeedReq incidentFeed) {
		
		return Mono.just(incidentFeed)
				.transform(this.incidentsFeedMapper::toCollectionMono)
				.flatMap(this.incidentsFeedService::createIncidentsFeed)
				.map( incidentFeedSave -> {
					return incidentFeedSave;
				});
	}

	@Override
	public Flux<IncidentsFeedResp> readAll(Integer page, Integer size) {
		
		
		return this.incidentsFeedService.readAll(page, size)
				.transform(this.incidentsFeedMapper::toResponseFlux)
				.doOnComplete(() -> log.info("Reading all incidents complete"));
	}
	
	

}
