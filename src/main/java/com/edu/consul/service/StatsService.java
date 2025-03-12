package com.edu.consul.service;

import com.edu.consul.model.Appointment;
import com.edu.consul.repository.AppointmentRepository;
import com.edu.consul.repository.ProductPurchaseRepository;
import com.edu.consul.util.AppointmentStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatsService {
    private final AppointmentRepository appointmentRepository;
    private final ProductPurchaseRepository purchaseRepository;

    public Map<String, Long> getConStatuses(String consultantId) {
        List<Appointment> appointments = appointmentRepository.findAllByConsultantId(consultantId);
        return appointments.stream().collect(Collectors.groupingBy(appointment -> {
            String status = appointment.getStatus();
            if (status.equalsIgnoreCase(AppointmentStatus.PENDING.getDisplayName())) {
                return "Pending";
            } else if (status.equalsIgnoreCase(AppointmentStatus.CANCELED.getDisplayName())) {
                return "Cancelled";
            } else if (status.equalsIgnoreCase(AppointmentStatus.COMPLETED.getDisplayName())) {
                return "Completed";
            } else {
                return "Confirmed";
            }
        }, Collectors.counting()));
    }

    public Map<String, Long> salesMap(String consultantId) {
        Map<String, Long> map = new HashMap<>();
        map.put("Consulted", appointmentRepository.findAllByConsultantId(consultantId).stream().filter(e-> e.getStatus().equalsIgnoreCase(AppointmentStatus.COMPLETED.getDisplayName())).count());
        map.put("Sold", purchaseRepository.findAllByConsultantId(consultantId).stream().filter(e-> e.getConsultantId().equals(consultantId)).count());
        return map;
    }
}
