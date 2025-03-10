package com.edu.consul.service;

import com.edu.consul.model.Appointment;
import com.edu.consul.model.Consultant;
import com.edu.consul.repository.ConsultantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConsultantService {
    private final ConsultantRepository consultantRepository;

    public Consultant findConsultantDetails(String consultantId) {
        return consultantRepository.findById(consultantId).orElse(null);
    }

    public void updateConsultantData(Appointment appointment) {
        Optional<Consultant> consultantOptional = consultantRepository.findById(appointment.getConsultantId());
        if (consultantOptional.isPresent()) {
            Consultant consultant = consultantOptional.get();
            List<Appointment> appIds = Objects.nonNull(consultant.getAppointments()) ? consultant.getAppointments() : new ArrayList<>();
            appIds.add(appointment);
            consultant.setAppointments(appIds);
            consultantRepository.save(consultant);
        }
    }
}
