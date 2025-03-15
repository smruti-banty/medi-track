package edu.rims.medi_track.service;

import edu.rims.medi_track.constants.UserRole;
import edu.rims.medi_track.dto.AdminRegistrationDTO;
import edu.rims.medi_track.dto.UserRegistrationDTO;
import edu.rims.medi_track.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    void registerUser(UserRegistrationDTO userDTO);

    void registerAdmin(AdminRegistrationDTO dto);

    User getUserByEmail(String email);

    Page<User> getUsersByRole(UserRole role, Pageable pageable);

    long doctors();

    long clients();

    List<User> getLast5Users();
}
