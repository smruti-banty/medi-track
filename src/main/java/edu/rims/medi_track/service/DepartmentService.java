package edu.rims.medi_track.service;

import edu.rims.medi_track.constants.Status;
import edu.rims.medi_track.dto.DepartmentDTO;
import edu.rims.medi_track.entity.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface DepartmentService {
    void createDepartment(DepartmentDTO dto);

    void deleteDepartment(String id);

    Page<Department> getDepartments(Pageable pageable);

    List<Department> getDepartments();

    Page<Department> getActiveDepartments(Pageable pageable);

    Page<Department> getInactiveDepartments(Pageable pageable);

    Optional<Department> getDepartmentById(String id);

    Department updateDepartmentStatus(String id, Status status);

    long departments();

    void removeDepartmentById(String departmentId);
}
