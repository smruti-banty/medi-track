package edu.rims.medi_track.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "client")
@PrimaryKeyJoinColumn(name = "id")
public class Client extends User {
    @Column(name = "contact_number")
    private String contactNumber;

    @Column(name = "address", columnDefinition = "text")
    private String address;
}
