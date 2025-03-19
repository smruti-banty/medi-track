package edu.rims.medi_track.repository;

import edu.rims.medi_track.constants.UserRole;
import edu.rims.medi_track.entity.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, String> {

    Page<Doctor> findByUserRole(UserRole userRole, Pageable pageable);

    Page<Doctor> findByUserRoleAndDepartmentId(UserRole userRole, String departmentId, Pageable pageable);

    List<Doctor> findTop5ByIsSpecialist(boolean isSpecialist);
}
