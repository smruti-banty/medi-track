package edu.rims.medi_track.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    void createRootDirectory();

    void uploadFile(String uploadId, MultipartFile multipartFile);

    byte[] getFileContent(String uploadId);
}
