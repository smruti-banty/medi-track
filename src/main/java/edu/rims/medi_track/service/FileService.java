package edu.rims.medi_track.service;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@Log
public class FileService {
    @Value("${upload.image.dir}")
    private String uploadDir;

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
}
