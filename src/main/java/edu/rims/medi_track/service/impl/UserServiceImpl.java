package edu.rims.medi_track.service.impl;

import edu.rims.medi_track.entity.User;
import edu.rims.medi_track.repository.DepartmentRepository;
import edu.rims.medi_track.service.FileService;
import edu.rims.medi_track.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import edu.rims.medi_track.constants.UserRole;
import edu.rims.medi_track.dto.UserRegistrationDTO;
import edu.rims.medi_track.entity.Client;
import edu.rims.medi_track.entity.Department;
import edu.rims.medi_track.entity.Doctor;
import edu.rims.medi_track.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final DepartmentRepository departmentRepository;

    private final PasswordEncoder passwordEncoder;

    private final FileService fileService;

    @Override
    public void registerUser(UserRegistrationDTO userDTO) {
        userDTO.setPassword(passwordEncoder
                .encode(userDTO.getPassword()));

        User user = null;
        if (userDTO.getUserRole() == UserRole.DOCTOR) {
          user = createDoctor(userDTO);
        } else {
          user = createClient(userDTO);
        }

        fileService.uploadFile(user.getId(), userDTO.getFile());
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow();
    }

    private User createDoctor(UserRegistrationDTO userDTO) {
        Doctor doctor = new Doctor();
        doctor.setName(userDTO.getName());
        doctor.setEmail(userDTO.getEmail());
        doctor.setPassword(userDTO.getPassword());
        doctor.setAge(userDTO.getAge());
        doctor.setUserRole(UserRole.DOCTOR);
        doctor.setAddress(userDTO.getAddress());
        doctor.setLicenseNumber(userDTO.getLicenseNumber());
        doctor.setExperienceYears(userDTO.getExperienceYears());

        if (userDTO.getDepartmentId() != null) {
            Optional<Department> department = departmentRepository.findById(userDTO.getDepartmentId());
            department.ifPresent(doctor::setDepartment);
        }

        return  userRepository.save(doctor);
    }

    private User createClient(UserRegistrationDTO userDTO) {
        Client client = new Client();
        client.setName(userDTO.getName());
        client.setEmail(userDTO.getEmail());
        client.setPassword(userDTO.getPassword());
        client.setAge(userDTO.getAge());
        client.setUserRole(UserRole.CLIENT);
        client.setContactNumber(userDTO.getContactNumber());
        client.setAddress(userDTO.getAddress());

        return  userRepository.save(client);
    }
}

