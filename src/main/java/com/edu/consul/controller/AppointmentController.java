package com.edu.consul.controller;

import com.edu.consul.model.Appointment;
import com.edu.consul.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping("/book")
    public ResponseEntity<?> bookAppointment(@RequestBody Appointment appointment) {
        return new ResponseEntity<>(appointmentService.bookAppointment(appointment), HttpStatus.OK);
    }

    @GetMapping("/student")
    public ResponseEntity<?> getAppointmentsForStudent(@RequestParam("studentId") String studentId) {
        return new ResponseEntity<>(appointmentService.getAppointmentsForStudent(studentId), HttpStatus.OK);
    }

    @GetMapping("/fetchAll")
    public ResponseEntity<?> getAllAppointments() {
        return ResponseEntity.ok(appointmentService.getAllAppointments());
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAppointment(@RequestParam("appointmentId") String appointmentId) {
        return ResponseEntity.ok(appointmentService.deleteAppointment(appointmentId));
    }

    @GetMapping("/consultant")
    public ResponseEntity<?> getAppointmentsForConsultant(@RequestParam("consultantId") String consultantId) {
        return new ResponseEntity<>(appointmentService.getAppointmentsForConsultant(consultantId), HttpStatus.OK);
    }

    @GetMapping("/booked-slots")
    public ResponseEntity<?> getBookedSlots(@RequestParam("date") String date, @RequestParam("consultantId") String consultantId) {
        return new ResponseEntity<>(appointmentService.fetchAvailableSlots(date, consultantId), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateAppointmentStatus(@RequestBody String appointmentStatus, @RequestParam("appointmentId") String appointmentId) {
        return ResponseEntity.ok(appointmentService.updateAppointmentStatus(appointmentId, appointmentStatus));
    }
}
