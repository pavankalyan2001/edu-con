package com.edu.consul.repository;

import com.edu.consul.model.Appointment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface AppointmentRepository extends MongoRepository<Appointment, String> {

    List<Appointment> findAllByStudentId(String studentId);

    List<Appointment> findAllByConsultantId(String consultantId);

    @Query("{ 'date': ?0, 'consultantId': ?1 'status': 'CONFIRMED' }")
    List<Appointment> findConfirmedAppointments(String date, String consultantId);
}