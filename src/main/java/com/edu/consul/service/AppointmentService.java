package com.edu.consul.service;

import com.edu.consul.exceptions.BadRequestException;
import com.edu.consul.exceptions.GeneralException;
import com.edu.consul.model.Appointment;
import com.edu.consul.repository.AppointmentRepository;
import com.edu.consul.util.AppointmentStatus;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AppointmentService {
    private static final Log LOG = LogFactory.getLog(AppointmentService.class);
    private final AppointmentRepository appointmentRepository;
    private final UserService userService;
    private final ConsultantService consultantService;

    public Appointment bookAppointment(Appointment appointment) {
        try {
            appointment.setStatus(AppointmentStatus.PENDING.getDisplayName());
            Appointment appointmentDetails = appointmentRepository.save(appointment);
            userService.updateStudentData(appointmentDetails);
            consultantService.updateConsultantData(appointmentDetails);
            return appointmentDetails;
        } catch (Exception e) {
            LOG.error(e);
            throw new GeneralException(e.getMessage());
        }
    }

    public List<Appointment> getAppointmentsForStudent(String studentId) {
        return appointmentRepository.findAllByStudentId(studentId);
    }

    public List<Appointment> getAppointmentsForConsultant(String consultantId) {
        return appointmentRepository.findAllByConsultantId(consultantId);
    }

    public Appointment updateAppointmentStatus(Appointment appointment) {
        return appointmentRepository.findById(appointment.getId()).map(appointmentDB -> {
            appointmentDB.setStatus(getAppointmentStatus(appointment.getStatus()).getDisplayName());
            return appointmentRepository.save(appointmentDB);
        }).orElse(null);
    }

    private AppointmentStatus getAppointmentStatus(String status) {
        try {
            return AppointmentStatus.valueOf(status.toUpperCase());
        } catch (Exception e) {
            throw new BadRequestException("Invalid status");
        }
    }
}
