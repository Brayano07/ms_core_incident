package incident.com.dto;

import java.time.LocalDateTime;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IncidentsFeedReq {

	    private String eventType;

	    private Long incidentId;

	    private String incidentUuid;

	    private String title;

	    private String description;

	    private String priority;

	    private String status;

	    private CreatedBy createdBy;

	    private Date createdAt;

	    private Date eventTimestamp;

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
