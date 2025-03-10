package com.edu.consul.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "appointments")
public class Appointment {
    @Id
    private String id;
    private String studentId;
    private String consultantId;
    Date date;
    String timeSlot;
    private String status;
}
