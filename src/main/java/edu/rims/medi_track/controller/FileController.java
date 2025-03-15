package edu.rims.medi_track.controller;

import edu.rims.medi_track.dto.UpdateFileDTO;
import edu.rims.medi_track.entity.User;
import edu.rims.medi_track.service.FileService;
import edu.rims.medi_track.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    private final UserService userService;

    @GetMapping(path = "/images/{id}",
            produces = {"image/jpg", "image/png", "image/jpeg"})
    byte[] getImage(@PathVariable("id") String uploadId) {
        return fileService.getFileContent(uploadId);
    }

    @PostMapping("/update-profile-picture")
    @ResponseStatus(HttpStatus.CREATED)
    void updateFile(@ModelAttribute UpdateFileDTO dto) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        if (securityContext.getAuthentication() == null && !securityContext.getAuthentication().isAuthenticated()) {
                throw new RuntimeException("Not a valid person");
        }

        User user = userService.getUserByEmail(securityContext.getAuthentication().getName());
        if (!user.getId().equals(dto.uploadId())) {
            throw new RuntimeException("Update allowed only on own profile");
        }

        fileService.uploadFile(dto.uploadId(), dto.file());
    }
}
