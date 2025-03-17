package edu.rims.medi_track.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record AppointmentRequestDTO(
        String doctorId,
        LocalDate appointmentDate,
        LocalTime appointmentTime,
        String notes
) {
}
