package com.edu.consul.dto;

import lombok.Data;

import java.util.Date;

@Data
public class AppointmentSlot {
    Date date;
    String timeSlot;
    String consultantStatus;
}
