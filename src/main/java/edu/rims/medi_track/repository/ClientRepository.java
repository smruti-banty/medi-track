package edu.rims.medi_track.repository;

import edu.rims.medi_track.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, String> {
}
