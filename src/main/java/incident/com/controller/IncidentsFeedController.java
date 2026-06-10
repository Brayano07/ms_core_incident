package incident.com.controller;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import incident.com.dto.IncidentsFeedReq;
import incident.com.dto.IncidentsFeedResp;
import incident.com.model.IncidentsFeedCollection;
import incident.com.service.CatalogCacheService;
import incident.com.service.IncidentsFeedService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/incidents_feed")
@RequiredArgsConstructor
@Slf4j
public class IncidentsFeedController {

	    @Autowired
	    private IncidentsFeedService incidentsFeedService;
	    
	    private final CatalogCacheService catalogCacheService;

	    @GetMapping
	    public Flux<IncidentsFeedResp> getAll(
	            @RequestParam("page") Integer page,
	            @RequestParam("size") Integer size) {

	        return catalogCacheService.getAllCache()
	                .doOnSubscribe(s ->
	                        log.info("Buscando incidents feed en Redis"))
	                .doOnNext(item ->
	                        log.info("Respuesta obtenida desde CACHE"))
	                .switchIfEmpty(

	                        Flux.defer(() -> {

	                            log.info("CACHE MISS - Consultando MongoDB");

	                            return incidentsFeedService.readAll(page, size)
	                                    .collectList()
	                                    .flatMapMany(list ->

	                                            catalogCacheService.saveAllCache(list)
	                                                    .doOnSuccess(saved ->
	                                                            log.info("Datos almacenados en CACHE"))
	                                                    .thenMany(Flux.fromIterable(list))
	                                    );
	                        })
	                )
	                .doOnComplete(() ->
	                        log.info("Reading incidents feed completed"));
	    }
	    
	    @PostMapping
		public Mono<IncidentsFeedCollection> crearIncidentsFeed( @RequestBody IncidentsFeedReq incidentsFeedReq)
		{
			
	    	return  incidentsFeedService.createIncidentsFeed(incidentsFeedReq);
		}
}
