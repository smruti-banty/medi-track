package edu.rims.medi_track.service.impl;

import edu.rims.medi_track.constants.ActivityType;
import edu.rims.medi_track.constants.UserRole;
import edu.rims.medi_track.dto.DoctorResponseDTO;
import edu.rims.medi_track.dto.DoctorUpdateRequestDTO;
import edu.rims.medi_track.entity.Activity;
import edu.rims.medi_track.entity.Doctor;
import edu.rims.medi_track.repository.ActivityRepository;
import edu.rims.medi_track.repository.DoctorRepository;
import edu.rims.medi_track.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {
    private final DoctorRepository doctorRepository;

    private final ActivityRepository activityRepository;

    @Override
    public Page<Doctor> getAllDoctors(Pageable pageable) {
        return doctorRepository.findByUserRole(UserRole.DOCTOR, pageable);
    }

    @Override
    public Page<Doctor> getDoctorsByDepartment(String departmentId, Pageable pageable) {
        return doctorRepository.findByUserRoleAndDepartmentId(UserRole.DOCTOR, departmentId, pageable);
    }

    @Override
    public DoctorResponseDTO getDoctorById(String doctorId) {
        return doctorRepository.findById(doctorId).map(
                doctor -> new DoctorResponseDTO(
                        doctor.getId(),
                        doctor.getName(),
                        doctor.getDepartment() == null
                        ? null : doctor.getDepartment().getDepartmentName(),
                        doctor.getEmail(),
                        doctor.getExperienceYears(),
                        doctor.getHourlyRate(),
                        doctor.getLicenseNumber(),
                        doctor.getAddress()
                )
        ).orElseThrow();
    }

    @Override
    public void updateDoctor(DoctorUpdateRequestDTO dto) {
        var doctor = doctorRepository.findById(dto.doctorId()).orElseThrow();
        doctor.setAge(dto.age());
        doctor.setAddress(dto.address());
        doctor.setHourlyRate(dto.hourlyRate());
        doctor.setExperienceYears(dto.experienceYears());
        doctorRepository.save(doctor);

        var activitiy = new Activity();
        activitiy.setUser(doctor);
        activitiy.setActivityType(ActivityType.UPDATED_PROFILE);
        activitiy.setDescription("Update profile");
        activityRepository.save(activitiy);
    }

    @Override
    public void makeSpecialist(String doctorId) {
        var doctor = doctorRepository.findById(doctorId).orElseThrow();
        doctor.setIsSpecialist(true);
        doctorRepository.save(doctor);
    }

    @Override
    public List<Doctor> getSpecialists() {
        return doctorRepository.findTop5ByIsSpecialist(true);
    }

}