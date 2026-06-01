package incident.com.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "incident_feed")
public class IncidentsFeedCollection {

	@Id
	private UUID id;

    private String eventType;

    private Long incidentId;

    private String incidentUuid;

    private String title;

    private String description;

    private String priority;

    private String status;

    private CreatedBy createdBy;

    private LocalDateTime createdAt;

    private LocalDateTime eventTimestamp;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreatedBy {

        private Long userId;

        private String username;

        private String email;

        private String role;
    }
}