package edu.rims.medi_track.repository;

import edu.rims.medi_track.constants.UserRole;
import edu.rims.medi_track.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String username);

    Page<User> findByUserRole(UserRole userRole, Pageable pageable);

    long countAllByUserRole(UserRole userRole);

    List<User> findTop5ByOrderByCreatedDateDesc();
}
