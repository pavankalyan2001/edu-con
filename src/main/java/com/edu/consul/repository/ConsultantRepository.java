package com.edu.consul.repository;

import com.edu.consul.model.Consultant;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConsultantRepository extends MongoRepository<Consultant, String> {
}