package incident.com.kafka;

import java.util.function.Consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import incident.com.dto.IncidentsFeedReq;
import incident.com.publisher.IncidentEventPublisher;
import incident.com.service.IncidentsFeedService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class IncidentConsumer {

    private final IncidentsFeedService incidentsFeedService;
    
    private final IncidentEventPublisher publisher;

    @Bean
    public Consumer<IncidentsFeedReq> incidentFeedConsumer() {

        return event -> {

            log.info("Evento recibido: {}", event);
            
            incidentsFeedService.createIncidentsFeed(event)
            .subscribe(
                result -> log.info("Guardado correctamente"),
                error -> log.error("Error guardando incidente", error)
            );
            
            log.info("EVENTO KAFKA RECIBIDO: {}", event);
            
            publisher.publish(event);

        };
    }
}