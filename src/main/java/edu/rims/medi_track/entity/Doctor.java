package edu.rims.medi_track.entity;

import edu.rims.medi_track.constants.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "doctor")
@PrimaryKeyJoinColumn(name = "id")
@Getter
@Setter
public class Doctor extends User {

    @Column(name = "license_number")
    private String licenseNumber;

    @Column(name = "experience_years")
    private int experienceYears;

    @ManyToOne()
    @JoinColumn(name = "department_id")
    private Department department;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;
}
