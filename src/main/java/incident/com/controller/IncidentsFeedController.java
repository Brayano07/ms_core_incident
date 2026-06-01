package incident.com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import incident.com.dto.IncidentsFeedReq;
import incident.com.dto.IncidentsFeedResp;
import incident.com.service.IncidentsFeedBsService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/incidents_feed")
@RequiredArgsConstructor
public class IncidentsFeedController {

	    @Autowired
	    private IncidentsFeedBsService incidentsFeedBsService;

	    @GetMapping
	    public Flux<IncidentsFeedResp> getAll(){

	        return incidentsFeedBsService.readAll(1, 10);
	        
	        
	    }
	    
	    
	    @PostMapping
		public Mono<Object> crearUsuario( @RequestBody IncidentsFeedReq incidentsFeedReq)
		{
			
	    	return incidentsFeedBsService.createIncidentsFeed(incidentsFeedReq);
		}
}
