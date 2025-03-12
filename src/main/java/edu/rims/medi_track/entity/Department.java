package edu.rims.medi_track.entity;

import edu.rims.medi_track.constants.Status;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @Column(name = "department_name", unique = true, nullable = false)
    private String departmentName;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    List<Doctor> doctors;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;
}
