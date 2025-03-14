package edu.rims.medi_track.controller;

import edu.rims.medi_track.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    @GetMapping(path = "/image/{id}",
            produces = {"image/jpg", "image/png", "image/jpeg"})
    byte[] getImage(@PathVariable("id") String uploadId) {
        return fileService.getFileContent(uploadId);
    }
}
