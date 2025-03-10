package com.edu.consul.service;

import com.edu.consul.exceptions.BadRequestException;
import com.edu.consul.model.Appointment;
import com.edu.consul.model.User;
import com.edu.consul.repository.UserRepository;
import com.edu.consul.util.Role;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private static final Log LOG = LogFactory.getLog(UserService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User registerUser(User user) {
        if (userRepository.existsByName(user.getName())) {
            throw new BadRequestException("User with name " + user.getName() + " already exists!");
        } else if (userRepository.existsByEmail(user.getEmail())) {
            throw new BadRequestException("User with email " + user.getEmail() + " already exists!");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.valueOf(user.getRole().toUpperCase()).getDisplayName());
        return userRepository.save(user);
    }

    public User getUserData(String emailId) {
        return userRepository.findByEmail(emailId).orElse(null);
    }

    public void updateStudentData(Appointment appointment) {
        Optional<User> userData = userRepository.findById(appointment.getStudentId());
        if (userData.isPresent()) {
            User user = userData.get();
            List<Appointment> appIds = Objects.nonNull(user.getAppointments()) ? user.getAppointments() : new ArrayList<>();
            appIds.add(appointment);
            user.setAppointments(appIds);
            userRepository.save(user);
        }
    }

    public List<User> getConsultants() {
        return userRepository.findAllByRole(Role.CONSULTANT.getDisplayName());
    }
}
