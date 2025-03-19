package edu.rims.medi_track.repository;

import edu.rims.medi_track.constants.AppointmentStatus;
import edu.rims.medi_track.entity.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, String> {

    Page<Appointment> findByClientIdOrderByAppointmentDateDesc(String clientId, Pageable pageable);

    Page<Appointment> findByDoctorIdOrderByAppointmentDateDesc(String doctorId, Pageable pageable);

    Page<Appointment> findByDoctorIdAndStatusOrderByAppointmentDateDesc(String doctorId, AppointmentStatus status, Pageable pageable);

    long countByDoctorIdAndStatus(String doctorId, AppointmentStatus status);

    long countByClientIdAndStatus(String clientId, AppointmentStatus status);

    long countByDoctorId(String doctorId);

    @Query("SELECT COALESCE(SUM(a.doctorFee), 0) FROM Appointment a WHERE a.status = 'COMPLETED' AND a.doctor.id = :doctorId")
    Double findDoctorTotalEarnings(@Param("doctorId") String doctorId);

    List<Appointment> findTop5ByDoctorIdAndStatusOrderByCreatedDateDesc(String doctorId, AppointmentStatus status);

    List<Appointment> findTop5ByClientIdAndStatusOrderByCreatedDateDesc(String clientId, AppointmentStatus status);

    List<Appointment> findTop5ByOrderByCreatedDateDesc();
}
