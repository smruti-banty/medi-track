package edu.rims.medi_track.dto;

public record DoctorResponseDTO(String doctorId,
                                String doctorName,
                                String doctorSpecialization,
                                String doctorEmail,
                                Integer doctorExperience,
                                Double doctorRate,
                                String doctorLicense,
                                String doctorAddress) {
}
