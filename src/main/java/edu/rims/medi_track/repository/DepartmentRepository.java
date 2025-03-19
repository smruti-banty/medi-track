package edu.rims.medi_track.repository;

import edu.rims.medi_track.constants.Status;
import edu.rims.medi_track.entity.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, String> {
    Page<Department> findByStatus(Status status, Pageable pageable);

    List<Department> findByStatus(Status status);
}
