package incident.com.router;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import incident.com.handler.IncidentsFeedHandler;

@Configuration
public class IncidentsFeedRouter {

	//private final static String BY_NAME_URL = "/{name}";

	@Bean
	public RouterFunction<ServerResponse> routes(IncidentsFeedHandler handler){
		return route()
				.path("/incidents_feed", builder -> builder
					.GET("", request -> {
							return handler.getAllIncidents(request);
					})
				)
				.build();
	}
}




