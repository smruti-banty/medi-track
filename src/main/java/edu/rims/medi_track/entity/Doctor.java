package edu.rims.medi_track.entity;

import edu.rims.medi_track.constants.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "doctor")
@PrimaryKeyJoinColumn(name = "id")
@Getter
@Setter
public class Doctor extends User {

    @Column(name = "license_number")
    private String licenseNumber;

    @Column(name = "experience_years")
    private Integer experienceYears;

    @Column(name = "hourly_rate")
    private Double hourlyRate;

    @Column(name = "address", columnDefinition = "text")
    private String address;

    @Column(name = "is_specialist")
    private Boolean isSpecialist;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @OneToMany(mappedBy = "doctor")
    private List<Appointment> appointments;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;
}
