package incident.com.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import incident.com.dto.IncidentsFeedResp;
import incident.com.service.IncidentsFeedBsService;
import reactor.core.publisher.Mono;

@Component
public class IncidentsFeedHandler {

	@Autowired
	IncidentsFeedBsService incindentsFeedBsService;
	
	public Mono<ServerResponse> getAllIncidents( ServerRequest serverRequest){
		
		
		 final Integer page = Integer.parseInt(serverRequest.queryParam("page").orElse("0"));
	     final Integer size = Integer.parseInt(serverRequest.queryParam("size").orElse("10"));

		 final var inicidentFlux = this.incindentsFeedBsService.readAll(page, size);
		
		 return  ServerResponse.ok()
				 .contentType(MediaType.APPLICATION_JSON)
				 .body(inicidentFlux, IncidentsFeedResp.class)
				 .switchIfEmpty(ServerResponse.notFound().build());
	}
	
}
