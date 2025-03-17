package edu.rims.medi_track.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "client")
@PrimaryKeyJoinColumn(name = "id")
@Getter
@Setter
public class Client extends User {
    @Column(name = "contact_number")
    private String contactNumber;

    @Column(name = "address", columnDefinition = "text")
    private String address;

    @OneToMany(mappedBy = "client")
    private List<Appointment> appointments;
}
