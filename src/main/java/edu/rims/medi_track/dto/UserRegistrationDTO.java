package edu.rims.medi_track.dto;

import edu.rims.medi_track.constants.UserRole;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class UserRegistrationDTO {
    private String name;
    private String email;
    private String password;
    private int age;
    private UserRole userRole;

    // Fields for Doctors
    private String licenseNumber;
    private Integer experienceYears;
    private String departmentId;
    private Double hourlyRate;

    // Fields for Clients
    private String contactNumber;

    private String address;
    private MultipartFile file;
}
