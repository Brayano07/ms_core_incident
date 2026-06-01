package incident.com.service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import incident.com.exception.ResourceNotFoundException;
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
	
	@Override
	public Mono<IncidentsFeedCollection> createIncidentsFeed(IncidentsFeedCollection incident) {
		
		incident.setId(UUID.randomUUID());
		
		return this.incidentsFeedRepository.save(incident);
		
		/*return this.incidentsFeedRepository.findById(UUID.fromString(inicident.getIncidentUuid()))
				.switchIfEmpty(Mono.error(new ResourceNotFoundException("Inicident not found")))	
	            .flatMap( incident -> {
					
					return this.incidentsFeedRepository.save(incident);
				});*/
	}

	@Override
	public Flux<IncidentsFeedCollection> readAll(Integer page, Integer size) {
		return this.incidentsFeedRepository.findAll()
				.skip( (long) page * size).take(size);
	}

}
