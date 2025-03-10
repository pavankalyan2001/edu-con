package com.edu.consul.model;

import com.edu.consul.dto.AppointmentSlot;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "consultants")
public class Consultant {
    @Id
    private String id;
    private String name;
    private String email;
    private String expertise;
    private List<Appointment> appointments;
}
