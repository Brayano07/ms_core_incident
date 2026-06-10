package incident.com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import incident.com.dto.IncidentsFeedReq;
import incident.com.publisher.IncidentEventPublisher;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/incidents_notification")
@Slf4j
public class IncidentsNotificationController {

	@Autowired
	private IncidentEventPublisher publisher;

	@GetMapping(
	        value = "/stream",
	        produces = MediaType.TEXT_EVENT_STREAM_VALUE
	)
	public Flux<ServerSentEvent<IncidentsFeedReq>> stream() {

	    log.info("CLIENTE CONECTADO AL STREAM");

	    return publisher.getStream()
	            .map(incident -> {

	                log.info("ENVIANDO AL CLIENTE: {}", incident);

	                return ServerSentEvent
	                        .builder(incident)
	                        .event("INCIDENT_CREATED")
	                        .build();
	            });
	}
}
