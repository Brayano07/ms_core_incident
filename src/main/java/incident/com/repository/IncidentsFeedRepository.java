package incident.com.repository;

import java.util.UUID;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import incident.com.model.IncidentsFeedCollection;


public interface IncidentsFeedRepository extends ReactiveMongoRepository<IncidentsFeedCollection, UUID> {

}
