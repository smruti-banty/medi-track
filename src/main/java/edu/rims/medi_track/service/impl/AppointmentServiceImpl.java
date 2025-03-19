package edu.rims.medi_track.service.impl;

import edu.rims.medi_track.constants.ActivityType;
import edu.rims.medi_track.constants.AppointmentStatus;
import edu.rims.medi_track.dto.AppointmentRequestDTO;
import edu.rims.medi_track.entity.Activity;
import edu.rims.medi_track.entity.Appointment;

import edu.rims.medi_track.entity.User;
import edu.rims.medi_track.repository.ActivityRepository;
import edu.rims.medi_track.repository.AppointmentRepository;
import edu.rims.medi_track.repository.ClientRepository;
import edu.rims.medi_track.repository.DoctorRepository;
import edu.rims.medi_track.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;

    private final DoctorRepository doctorRepository;

    private final ClientRepository clientRepository;

    private final ActivityRepository activityRepository;

    @Override
    public void bookAppointment(String clientId, AppointmentRequestDTO requestDTO) {
        var doctor = doctorRepository.findById(requestDTO.doctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        var client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found"));

        Appointment appointment = new Appointment();
        appointment.setDoctor(doctor);
        appointment.setClient(client);
        appointment.setAppointmentDate(requestDTO.appointmentDate());
        appointment.setAppointmentTime(requestDTO.appointmentTime());
        appointment.setNotes(requestDTO.notes());
        appointment.setStatus(AppointmentStatus.PENDING);
        appointment.setDoctorFee(doctor.getHourlyRate());
        appointmentRepository.save(appointment);

        var description = "Appointment with Dr. " +
                getFirstName(appointment.getDoctor().getName()) +
                " " + appointment.getStatus().name().toLowerCase();
        var activityType = activityTypeFromStatus(appointment.getStatus());
        addActivity(appointment.getClient(), activityType, description);
    }

    @Override
    public Page<Appointment> getClientAppointments(String clientId, Pageable pageable) {
        return appointmentRepository.findByClientIdOrderByAppointmentDateDesc(clientId, pageable);
    }

    @Override
    public Page<Appointment> getDoctorAppointments(String doctorId, Pageable pageable) {
        return appointmentRepository.findByDoctorIdOrderByAppointmentDateDesc(doctorId, pageable);
    }

    @Override
    public Page<Appointment> getDoctorPendingAppointments(String doctorId, Pageable pageable) {
        return appointmentRepository
                .findByDoctorIdAndStatusOrderByAppointmentDateDesc(
                        doctorId, AppointmentStatus.PENDING, pageable);
    }

    @Override
    public void cancelAppointment(String appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        appointment.setStatus(AppointmentStatus.CANCELLED);
        appointmentRepository.save(appointment);
    }

    @Override
    public void updateAppointmentStatus(String appointmentId, AppointmentStatus status) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        appointment.setStatus(status);
        appointmentRepository.save(appointment);

        // For doctor activity
        var description = "Appointment with " +
                getFirstName(appointment.getClient().getName()) +
                " " + appointment.getStatus().name().toLowerCase();
        var activityType = activityTypeFromStatus(appointment.getStatus());

        addActivity(appointment.getDoctor(), activityType, description);

        // For client activity
        description = "Appointment with Dr. " +
                getFirstName(appointment.getDoctor().getName()) +
                " " +  appointment.getStatus().name().toLowerCase();

        addActivity(appointment.getClient(), activityType, description);
    }

    @Override
    public long countDoctorPendingAppointments(String doctorId) {
        return appointmentRepository.countByDoctorIdAndStatus(doctorId, AppointmentStatus.PENDING);
    }

    @Override
    public long countPatientByDoctorId(String doctorId) {
        return appointmentRepository.countByDoctorId(doctorId);
    }

    @Override
    public long upcomingDoctorAppointments(String doctorId) {
        return appointmentRepository.countByDoctorIdAndStatus(doctorId, AppointmentStatus.CONFIRMED);
    }

    @Override
    public Double getDoctorTotalEarning(String doctorId) {
        return appointmentRepository.findDoctorTotalEarnings(doctorId);
    }

    @Override
    public List<Appointment> upcoming5DoctorAppointments(String doctorId) {
        return appointmentRepository.findTop5ByDoctorIdAndStatusOrderByCreatedDateDesc(doctorId, AppointmentStatus.CONFIRMED);
    }

    @Override
    public List<Appointment> upcoming5ClientAppointments(String clientId) {
        return appointmentRepository.findTop5ByClientIdAndStatusOrderByCreatedDateDesc(clientId, AppointmentStatus.CONFIRMED);
    }

    @Override
    public long countClientPendingAppointments(String clientId) {
        return appointmentRepository.countByClientIdAndStatus(clientId, AppointmentStatus.PENDING);
    }

    @Override
    public long countClientCancelAppointments(String clientId) {
        return appointmentRepository.countByClientIdAndStatus(clientId, AppointmentStatus.CANCELLED);
    }

    @Override
    public long countClientCompleteAppointments(String clientId) {
        return appointmentRepository.countByClientIdAndStatus(clientId, AppointmentStatus.COMPLETED);
    }

    @Override
    public long countClientConfirmAppointments(String clientId) {
        return appointmentRepository.countByClientIdAndStatus(clientId, AppointmentStatus.CONFIRMED);
    }

    @Override
    public long countAll() {
        return appointmentRepository.count();
    }

    @Override
    public List<Appointment> getLast5Appointments() {
        return appointmentRepository.findTop5ByOrderByCreatedDateDesc();
    }

    @Override
    public Page<Appointment> getAppointments(Pageable pageable) {
        return appointmentRepository.findAll(pageable);
    }

    private ActivityType activityTypeFromStatus(AppointmentStatus status) {
        return switch (status) {
            case PENDING -> ActivityType.SCHEDULED_APPOINTMENT;
            case CONFIRMED -> ActivityType.CONFIRMED_APPOINTMENT;
            case CANCELLED -> ActivityType.CANCELED_APPOINTMENT;
            default -> ActivityType.COMPLETED_APPOINTMENT;
        };
    }

    private void addActivity(User user, ActivityType type, String description) {
        var activity = new Activity();
        activity.setActivityType(type);
        activity.setUser(user);
        activity.setDescription(description);

        activityRepository.save(activity);
    }

    private String getFirstName(String name) {
        return name.split(" ")[0];
    }
}

