package edu.rims.medi_track.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "doctor")
@PrimaryKeyJoinColumn(name = "id")
public class Doctor extends User {

    @Column(name = "license_number")
    private String licenseNumber;

    @Column(name = "experience_years")
    private int experienceYears;

    @ManyToOne()
    @JoinColumn(name = "department_id")
    private Department department;
}
