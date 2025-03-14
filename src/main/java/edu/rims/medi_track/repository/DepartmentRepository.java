package edu.rims.medi_track.repository;

import edu.rims.medi_track.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, String> {
}
