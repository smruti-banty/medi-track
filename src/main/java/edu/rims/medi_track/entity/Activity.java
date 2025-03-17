package edu.rims.medi_track.entity;

import edu.rims.medi_track.constants.ActivityType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "activities")
@Getter
@Setter
public class Activity extends  Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "activity_type", nullable = false, columnDefinition = "varchar(255)")
    private ActivityType activityType;

    @Column(name = "description", nullable = false)
    private String description;

}
