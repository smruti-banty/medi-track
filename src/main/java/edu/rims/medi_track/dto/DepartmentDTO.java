package edu.rims.medi_track.dto;

import org.springframework.web.multipart.MultipartFile;

public record DepartmentDTO(String departmentId,
                            String departmentName,
                            String departmentDescription,
                            MultipartFile file) {
}
