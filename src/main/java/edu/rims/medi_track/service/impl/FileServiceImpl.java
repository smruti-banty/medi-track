package edu.rims.medi_track.service.impl;

import edu.rims.medi_track.entity.UploadFile;
import edu.rims.medi_track.repository.UploadFileRepository;
import edu.rims.medi_track.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Objects;
import java.util.UUID;

@Log
@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    @Value("${upload.file.dir}")
    private String uploadDir;

    private final UploadFileRepository uploadFileRepository;

    public void createRootDirectory() {
        var file = new File(uploadDir);
        if (!file.exists()) {
            if (file.mkdir()) {
                log.info("Upload directory created");
            } else {
                log.severe("Can't create upload directory " + uploadDir);
                throw new RuntimeException("Can't create upload directory");
            }
        } else {
            log.info("Upload directory exists");
        }
    }

    @Override
    public void uploadFile(String uploadId, MultipartFile multipartFile) {
        if (multipartFile !=null && !multipartFile.isEmpty()) {
            try {
                String fileName = generateFileName(Objects.requireNonNull(multipartFile.getOriginalFilename()));
                var fos = new FileOutputStream(getUploadPath(fileName));
                fos.write(multipartFile.getBytes());
                fos.close();
                updateFileDetails(uploadId, fileName);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public byte[] getFileContent(String uploadId) {
       var file = uploadFileRepository.findById(uploadId).orElseThrow();
       var fileName = file.getFileName();
       try {
           var fis = new FileInputStream(getUploadPath(fileName));
           byte[] fileContent = fis.readAllBytes();
           fis.close();
           return fileContent;
       } catch (Exception e) {
           log.severe("Image retrieve failed " + e.getMessage());
           throw new RuntimeException(e);
       }
    }

    private void updateFileDetails(String uploadId, String fileName) {
        var uploadFile = new UploadFile();
        uploadFile.setUploadId(uploadId);
        uploadFile.setFileName(fileName);
        uploadFileRepository.save(uploadFile);
    }

    private String generateFileName(String originalName) {
        return UUID.randomUUID() +  originalName.substring(originalName.lastIndexOf('.'));
    }

    private String getUploadPath(String fileName) {
        return uploadDir + File.separator + fileName;
    }
}
