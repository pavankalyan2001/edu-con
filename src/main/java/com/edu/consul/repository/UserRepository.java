package com.edu.consul.repository;

import com.edu.consul.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);

    boolean existsByName(String name);
    boolean existsByEmail(String email);

    List<User> findAllByRole(String role);

    @Query("{ 'role': { $ne: 'ADMIN' } }")
    Page<User> findAllExceptAdmin(Pageable pageable);
}