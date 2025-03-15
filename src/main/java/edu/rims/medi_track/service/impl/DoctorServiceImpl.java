package edu.rims.medi_track.service.impl;

import edu.rims.medi_track.constants.UserRole;
import edu.rims.medi_track.dto.DoctorResponseDTO;
import edu.rims.medi_track.entity.Doctor;
import edu.rims.medi_track.repository.DoctorRepository;
import edu.rims.medi_track.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {
    private final DoctorRepository doctorRepository;

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

}