package edu.rims.medi_track.dto;

import org.springframework.web.multipart.MultipartFile;

public record UpdateFileDTO(String uploadId, MultipartFile file) {
}
