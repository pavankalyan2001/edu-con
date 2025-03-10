package com.edu.consul.repository;

import com.edu.consul.model.Appointment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;

public interface AppointmentRepository extends MongoRepository<Appointment, String> {

    List<Appointment> findAllByStudentId(String studentId);

    List<Appointment> findAllByConsultantId(String consultantId);

    List<Appointment> findAllByDateAndConsultantId(Date date, String consultantId);
}