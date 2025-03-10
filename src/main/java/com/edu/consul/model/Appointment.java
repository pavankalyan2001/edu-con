package com.edu.consul.model;

import com.edu.consul.dto.AppointmentSlot;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "appointments")
public class Appointment {
    @Id
    private String id;
    private String studentId;
    private String consultantId;
    private AppointmentSlot appointmentSlot;
    private String status;
}
