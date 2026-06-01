package incident.com.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import incident.com.dto.IncidentsFeedReq;
import incident.com.dto.IncidentsFeedResp;
import incident.com.model.IncidentsFeedCollection;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Mapper(componentModel="spring")
public interface IncidentsFeedMapper {

	IncidentsFeedResp toResponse(IncidentsFeedCollection collection);
	
	
	
    @Mapping( target="id", ignore = true)
	@Mapping( target="createdBy", ignore = true)
	IncidentsFeedCollection toCollection(IncidentsFeedReq collection);
	
	
	default Flux<IncidentsFeedResp> toResponseFlux (Flux<IncidentsFeedCollection> collections)
	{ 
		return collections.map(this::toResponse);
		
	}

	default Mono<IncidentsFeedResp> toResponseMono (Mono<IncidentsFeedCollection> collection)
	{ 
		return collection.map(this::toResponse);
		
	}
	
	
	default Flux<IncidentsFeedCollection> toCollectionFlux (Flux<IncidentsFeedReq> requests)
	{ 
		return requests.map(this::toCollection);
		
	}
	
	
	default Mono<IncidentsFeedCollection> toCollectionMono (Mono<IncidentsFeedReq> request)
	{ 
		return request.map(this::toCollection);
		
	}
}
