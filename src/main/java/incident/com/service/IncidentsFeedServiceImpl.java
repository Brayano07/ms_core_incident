package incident.com.service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import incident.com.dto.IncidentsFeedReq;
import incident.com.dto.IncidentsFeedResp;
import incident.com.exception.ResourceNotFoundException;
import incident.com.mapper.IncidentsFeedMapper;
import incident.com.model.IncidentsFeedCollection;
import incident.com.repository.IncidentsFeedRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class IncidentsFeedServiceImpl implements IncidentsFeedService{
	
	@Autowired
	private IncidentsFeedRepository  incidentsFeedRepository;
	
	@Autowired
	private IncidentsFeedMapper  incidentsFeedMapper;
	
	@Override
	public Mono<IncidentsFeedCollection> createIncidentsFeed(IncidentsFeedReq incident) {
		
		
		IncidentsFeedCollection collection = incidentsFeedMapper.toCollection(incident);
		
		collection.setId(UUID.randomUUID());
		
		return this.incidentsFeedRepository.save(collection);
		
		/*return this.incidentsFeedRepository.findById(UUID.fromString(inicident.getIncidentUuid()))
				.switchIfEmpty(Mono.error(new ResourceNotFoundException("Inicident not found")))	
	            .flatMap( incident -> {
					
					return this.incidentsFeedRepository.save(incident);
				});*/
	}

	@Override
	public Flux<IncidentsFeedResp> readAll(Integer page, Integer size) {
		
		return  this.incidentsFeedMapper.toResponseFlux(this.incidentsFeedRepository.findAll()
				        .skip((long) (page - 1) * size)
				        .take(size));
	}

}
