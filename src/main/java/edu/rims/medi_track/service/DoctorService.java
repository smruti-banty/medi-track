package edu.rims.medi_track.service;

import edu.rims.medi_track.dto.DoctorResponseDTO;
import edu.rims.medi_track.dto.DoctorUpdateRequestDTO;
import edu.rims.medi_track.entity.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DoctorService {
    Page<Doctor> getAllDoctors(Pageable pageable);

    Page<Doctor> getDoctorsByDepartment(String departmentId, Pageable pageable);

    DoctorResponseDTO getDoctorById(String doctorId);

    void updateDoctor(DoctorUpdateRequestDTO dto);

    void makeSpecialist(String doctorId);

    List<Doctor> getSpecialists();
}