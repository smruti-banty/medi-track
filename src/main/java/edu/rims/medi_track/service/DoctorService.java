package edu.rims.medi_track.service;

import edu.rims.medi_track.dto.DoctorResponseDTO;
import edu.rims.medi_track.entity.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DoctorService {
    Page<Doctor> getAllDoctors(Pageable pageable);

    Page<Doctor> getDoctorsByDepartment(String departmentId, Pageable pageable);

    DoctorResponseDTO getDoctorById(String doctorId);
}