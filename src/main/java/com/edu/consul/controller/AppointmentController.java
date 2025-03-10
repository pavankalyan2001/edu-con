package com.edu.consul.controller;

import com.edu.consul.model.Appointment;
import com.edu.consul.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping("/book")
    public ResponseEntity<Appointment> bookAppointment(@RequestBody Appointment appointment) {
        return new ResponseEntity<>(appointmentService.bookAppointment(appointment), HttpStatus.OK);
    }

    @GetMapping("/student")
    public ResponseEntity<List<Appointment>> getAppointmentsForStudent(@RequestParam("studentId") String studentId) {
        return new ResponseEntity<>(appointmentService.getAppointmentsForStudent(studentId), HttpStatus.OK);
    }

    @GetMapping("/consultant")
    public ResponseEntity<List<Appointment>> getAppointmentsForConsultant(@RequestParam("consultantId") String consultantId) {
        return new ResponseEntity<>(appointmentService.getAppointmentsForConsultant(consultantId), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<Appointment> updateAppointmentStatus(@RequestBody Appointment appointment) {
        return new ResponseEntity<>(appointmentService.updateAppointmentStatus(appointment), HttpStatus.OK);
    }
}
