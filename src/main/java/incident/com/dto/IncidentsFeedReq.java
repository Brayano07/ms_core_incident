package incident.com.dto;

import java.time.LocalDateTime;
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

    private Long  incidentId;
    
    private Long  user_id;

    private String incidentUuid;

    private String title;

    private String description;

    private String priority;

    private String status;

    private LocalDateTime createdAt;

    private LocalDateTime eventTimestamp;

}
