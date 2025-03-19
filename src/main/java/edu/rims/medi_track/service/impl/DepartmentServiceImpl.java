package edu.rims.medi_track.service.impl;

import edu.rims.medi_track.constants.Status;
import edu.rims.medi_track.dto.DepartmentDTO;
import edu.rims.medi_track.entity.Department;
import edu.rims.medi_track.repository.DepartmentRepository;
import edu.rims.medi_track.service.DepartmentService;
import edu.rims.medi_track.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    private final FileService fileService;

    @Override
    public void createDepartment(DepartmentDTO dto) {
        var department = departmentRepository
                .findById(dto.departmentId()).orElse(new Department());

        department.setDepartmentName(dto.departmentName());
        department.setDepartmentDescription(dto.departmentDescription());

        var savedDepartment = departmentRepository.save(department);
        fileService.uploadFile(savedDepartment.getId(), dto.file());
    }

    @Override
    public void deleteDepartment(String id) {
        departmentRepository.deleteById(id);
    }

    @Override
    public Page<Department> getDepartments(Pageable pageable) {
        return departmentRepository.findAll(pageable);
    }

    @Override
    public List<Department> getDepartments() {
        return departmentRepository.findByStatus(Status.ACTIVE);
    }

    @Override
    public Page<Department> getActiveDepartments(Pageable pageable) {
        return departmentRepository.findByStatus(Status.ACTIVE, pageable);
    }

    @Override
    public Page<Department> getInactiveDepartments(Pageable pageable) {
        return departmentRepository.findByStatus(Status.INACTIVE, pageable);
    }

    @Override
    public Optional<Department> getDepartmentById(String id) {
        return departmentRepository.findById(id);
    }

    @Override
    public Department updateDepartmentStatus(String id, Status status) {
        return departmentRepository.findById(id)
                .map(department -> {
                    department.setStatus(status);
                    return departmentRepository.save(department);
                })
                .orElseThrow(() -> new RuntimeException("Department not found with ID: " + id));
    }

    @Override
    public long departments() {
        return departmentRepository.count();
    }

    @Override
    public void removeDepartmentById(String departmentId) {
        var department = departmentRepository.findById(departmentId).orElseThrow();
        department.removeDepartment();
        departmentRepository.delete(department);
    }
}
