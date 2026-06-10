package incident.com.publisher;

import org.springframework.stereotype.Component;

import incident.com.dto.IncidentsFeedReq;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Component
@Slf4j
public class IncidentEventPublisher {

    private final Sinks.Many<IncidentsFeedReq> sink =
            Sinks.many().multicast().onBackpressureBuffer();

    public void publish(IncidentsFeedReq incident) {
    	
    	 log.info("PUBLICANDO EVENTO SSE: {}", incident);
    	 
        sink.tryEmitNext(incident);
    }

    public Flux<IncidentsFeedReq> getStream() {

        return sink.asFlux();
    }
}
