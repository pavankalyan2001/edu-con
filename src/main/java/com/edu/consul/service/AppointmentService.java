package com.edu.consul.service;

import com.edu.consul.exceptions.BadRequestException;
import com.edu.consul.exceptions.GeneralException;
import com.edu.consul.model.Appointment;
import com.edu.consul.model.User;
import com.edu.consul.repository.AppointmentRepository;
import com.edu.consul.repository.UserRepository;
import com.edu.consul.util.AppointmentStatus;
import com.edu.consul.util.EduUtils;
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
    private final UserRepository userRepository;

    public Map<String, Object> bookAppointment(Appointment appointment) {
        try {
            LOG.info("appointment: " + appointment);
            appointment.setStatus(AppointmentStatus.PENDING.getDisplayName());
            Appointment appointmentDetails = appointmentRepository.save(appointment);
            userService.updateStudentData(appointmentDetails);
            consultantService.updateConsultantData(appointmentDetails);
            return getAppointmentDetails(appointmentDetails);
        } catch (Exception e) {
            LOG.error(e);
            throw new GeneralException(e.getMessage());
        }
    }

    public List<Map<String, Object>> getAppointmentsForStudent(String studentId) {
        List<Appointment> appointments = appointmentRepository.findAllByStudentId(studentId);
        return appointments.stream().map(this::getAppointmentDetails).toList();
    }

    private Map<String, Object> getAppointmentDetails(Appointment appointment) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", appointment.getId());
        map.put("consultantId", appointment.getConsultantId());
        map.put("studentId", appointment.getStudentId());
        map.put("appointmentSlot", appointment.getTimeSlot());
        map.put("appointmentDate", EduUtils.getNormalDate(appointment.getDate()));
        map.put("status", appointment.getStatus());

        User student = userRepository.findById(appointment.getStudentId()).orElse(null);
        User consultant = userRepository.findById(appointment.getConsultantId()).orElse(null);
        map.put("consultantName", consultant != null ? consultant.getName() : "Unknown");
        map.put("studentName", student != null ? student.getName() : "Unknown");
        return map;
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

    public List<Appointment> getBookedSlots(String date, String consultantId) {
        return appointmentRepository.findAllByDateAndConsultantId(EduUtils.getStandardDate(date), consultantId);
    }
}
