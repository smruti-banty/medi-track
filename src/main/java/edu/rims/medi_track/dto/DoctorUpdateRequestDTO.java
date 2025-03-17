package edu.rims.medi_track.dto;

public record DoctorUpdateRequestDTO(
        String doctorId,
        Double hourlyRate,
        Integer age,
        Integer experienceYears,
        String address
) {
}
