package com.edu.consul.repository;

import com.edu.consul.model.FeedBack;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FeedBackRepository extends MongoRepository<FeedBack, String> {
}
