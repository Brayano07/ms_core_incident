package incident.com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import incident.com.dto.IncidentsFeedResp;
import incident.com.service.IncidentsFeedService;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/dashboard")
public class GeneralController {

	
	@Autowired
	private IncidentsFeedService incidentsFeedService;
	
	
	public Flux<IncidentsFeedResp> getDashboard()
	{
		return null;
	}
}
