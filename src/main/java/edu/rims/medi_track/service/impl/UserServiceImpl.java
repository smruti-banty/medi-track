package edu.rims.medi_track.service.impl;

import edu.rims.medi_track.dto.AdminRegistrationDTO;
import edu.rims.medi_track.entity.*;
import edu.rims.medi_track.repository.DepartmentRepository;
import edu.rims.medi_track.service.FileService;
import edu.rims.medi_track.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import edu.rims.medi_track.constants.UserRole;
import edu.rims.medi_track.dto.UserRegistrationDTO;
import edu.rims.medi_track.repository.UserRepository;

import java.util.List;
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
    public void registerAdmin(AdminRegistrationDTO dto) {
        var admin = new Admin();

        admin.setUserRole(UserRole.ADMIN);
        admin.setName(dto.name());
        admin.setEmail(dto.email());
        admin.setPassword(passwordEncoder.encode(dto.password()));
        admin.setAge(dto.age());

        userRepository.save(admin);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow();
    }

    @Override
    public Page<User> getUsersByRole(UserRole role, Pageable pageable) {
        return userRepository.findByUserRole(role, pageable);
    }

    @Override
    public long doctors() {
        return userRepository.countAllByUserRole(UserRole.DOCTOR);
    }

    @Override
    public long clients() {
        return userRepository.countAllByUserRole(UserRole.CLIENT);
    }

    @Override
    public List<User> getLast5Users() {
        return userRepository.findTop5ByOrderByCreatedDateDesc();
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
        doctor.setHourlyRate(userDTO.getHourlyRate());

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

