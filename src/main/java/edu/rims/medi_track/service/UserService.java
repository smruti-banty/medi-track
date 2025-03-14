package edu.rims.medi_track.service;

import edu.rims.medi_track.dto.UserRegistrationDTO;
import edu.rims.medi_track.entity.User;

public interface UserService {
    void registerUser(UserRegistrationDTO userDTO);

    User getUserByEmail(String email);
}
