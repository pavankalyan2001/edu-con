package com.edu.consul.service;

import com.edu.consul.exceptions.BadRequestException;
import com.edu.consul.exceptions.GeneralException;
import com.edu.consul.model.Appointment;
import com.edu.consul.model.User;
import com.edu.consul.repository.AppointmentRepository;
import com.edu.consul.repository.UserRepository;
import com.edu.consul.util.AppointmentStatus;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentService {
    private static final Log LOG = LogFactory.getLog(AppointmentService.class);
    private final AppointmentRepository appointmentRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    public Map<String, Object> bookAppointment(Appointment appointment) {
        try {
            LOG.info("appointment: " + appointment);
            appointment.setStatus(AppointmentStatus.PENDING.getDisplayName());
            Appointment appointmentDetails = appointmentRepository.save(appointment);
            userService.updateStudentData(appointmentDetails);
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
        if (appointment == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("id", appointment.getId());
        map.put("consultantId", appointment.getConsultantId());
        map.put("studentId", appointment.getStudentId());
        map.put("appointmentSlot", appointment.getTimeSlot());
        map.put("appointmentDate", appointment.getDate());
        map.put("status", appointment.getStatus());

        User student = userRepository.findById(appointment.getStudentId()).orElse(null);
        User consultant = userRepository.findById(appointment.getConsultantId()).orElse(null);
        map.put("consultantName", consultant != null ? consultant.getName() : "Unknown");
        map.put("studentName", student != null ? student.getName() : "Unknown");
        return map;
    }

    public List<Map<String, Object>> getAppointmentsForConsultant(String consultantId) {
        List<Appointment> appointments = appointmentRepository.findAllByConsultantId(consultantId);
        return appointments.stream().map(this::getAppointmentDetails).toList();
    }

    public Map<String, Object> updateAppointmentStatus(String appointmentId, String appointmentStatus) {
        return getAppointmentDetails(appointmentRepository.findById(appointmentId).map(appointmentDB -> {
            appointmentDB.setStatus(getAppointmentStatus(appointmentStatus).getDisplayName());
            return appointmentRepository.save(appointmentDB);
        }).orElse(null));
    }

    private AppointmentStatus getAppointmentStatus(String status) {
        try {
            if (status.equalsIgnoreCase("confirm")) {
                return AppointmentStatus.CONFIRMED;
            } else if (status.equalsIgnoreCase("reject")) {
                return AppointmentStatus.CANCELED;
            }else if (status.equalsIgnoreCase("complete")) {
                return AppointmentStatus.COMPLETED;
            }
            return AppointmentStatus.valueOf(status.toUpperCase());
        } catch (Exception e) {
            throw new BadRequestException("Invalid status");
        }
    }

    public List<String> fetchAvailableSlots(String date, String consultantId) {
        List<String> availableSlots = new ArrayList<>(Arrays.asList("9AM-10:30AM",
                "11AM-12:30PM",
                "3PM-4:30PM"));
        List<Appointment> appointments = appointmentRepository.findConfirmedAppointments(date, consultantId);
        Set<String> bookedSlots = appointments.stream().map(Appointment::getTimeSlot).collect(Collectors.toSet());
        availableSlots.removeAll(bookedSlots);
        return availableSlots;
    }

    public List<Map<String, Object>> getAllAppointments() {
        List<Appointment> appointments = appointmentRepository.findAll();
        return appointments.stream().map(this::getAppointmentDetails).toList();
    }

    public String deleteAppointment(String appointmentId) {
        List<User> allUsers = userRepository.findAll();
        allUsers.forEach(user -> {
            if (!Objects.isNull(user.getAppointments()))
                user.getAppointments().removeIf(appointment -> appointment.getId().equals(appointmentId));
        });
        userRepository.saveAll(allUsers);
        appointmentRepository.deleteById(appointmentId);
        return "Success";
    }
}
