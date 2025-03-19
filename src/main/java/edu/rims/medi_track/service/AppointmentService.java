package edu.rims.medi_track.service;

import edu.rims.medi_track.constants.AppointmentStatus;
import edu.rims.medi_track.dto.AppointmentRequestDTO;
import edu.rims.medi_track.entity.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface AppointmentService {
    void bookAppointment(String clientId, AppointmentRequestDTO requestDTO);

    Page<Appointment> getClientAppointments(String clientId, Pageable pageable);

    Page<Appointment> getDoctorAppointments(String doctorId, Pageable pageable);

    Page<Appointment> getDoctorPendingAppointments(String doctorId, Pageable pageable);

    void cancelAppointment(String appointmentId);

    void updateAppointmentStatus(String appointmentId, AppointmentStatus status);

    long countDoctorPendingAppointments(String doctorId);

    long countPatientByDoctorId(String doctorId);

    long upcomingDoctorAppointments(String doctorId);

    Double getDoctorTotalEarning(String doctorId);

    List<Appointment> upcoming5DoctorAppointments(String doctorId);

    List<Appointment> upcoming5ClientAppointments(String clientId);

    long countClientPendingAppointments(String clientId);

    long countClientCancelAppointments(String clientId);

    long countClientCompleteAppointments(String clientId);

    long countClientConfirmAppointments(String clientId);

    long countAll();

    List<Appointment> getLast5Appointments();

    Page<Appointment> getAppointments(Pageable pageable);
}
