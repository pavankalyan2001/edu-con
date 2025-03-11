package com.edu.consul.service;

import com.edu.consul.exceptions.BadRequestException;
import com.edu.consul.model.Appointment;
import com.edu.consul.model.User;
import com.edu.consul.repository.AppointmentRepository;
import com.edu.consul.repository.UserRepository;
import com.edu.consul.util.Role;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private static final Log LOG = LogFactory.getLog(UserService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AppointmentRepository appointmentRepository;

    public User registerUser(User user) {
        if (Role.ADMIN.getDisplayName().equalsIgnoreCase(user.getRole())) {
            throw new BadRequestException("Admin registration is not allowed.");
        }
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

    public Page<User> getUsersExceptAdmin(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.findAllExceptAdmin(pageable);
    }

    public void updateStudentData(Appointment appointment) {
        Optional<User> studentData = userRepository.findById(appointment.getStudentId());
        Optional<User> consultantData = userRepository.findById(appointment.getConsultantId());
        if (studentData.isPresent()) {
            User user = studentData.get();
            List<Appointment> appIds = Objects.nonNull(user.getAppointments()) ? user.getAppointments() : new ArrayList<>();
            appIds.add(appointment);
            user.setAppointments(appIds);
            userRepository.save(user);
        }
        if (consultantData.isPresent()) {
            User user = consultantData.get();
            List<Appointment> appIds = Objects.nonNull(user.getAppointments()) ? user.getAppointments() : new ArrayList<>();
            appIds.add(appointment);
            user.setAppointments(appIds);
            userRepository.save(user);
        }
    }

    public List<User> getConsultants() {
        return userRepository.findAllByRole(Role.CONSULTANT.getDisplayName());
    }

    public void deleteUser(String userId, String role) {
        Optional<User> userOP = userRepository.findById(userId);
        if (userOP.isPresent()) {
            User user = userOP.get();
            List<Appointment> appointmentsWithUser;
            if (Role.STUDENT.getDisplayName().equalsIgnoreCase(role)) {
                List<String> consultants = userOP.map(user1 -> user.getAppointments().stream().map(Appointment::getConsultantId).toList()).orElse(new ArrayList<>());
                for (String conId : consultants) {
                    Optional<User> conOP = userRepository.findById(conId);
                    if (conOP.isPresent()) {
                        User con = conOP.get();
                        List<Appointment> matchingAppointments = con.getAppointments().stream()
                                .filter(a -> a.getStudentId().equals(userId))
                                .toList();
                        con.getAppointments().removeAll(matchingAppointments);
                        userRepository.save(con);
                    }
                }
                appointmentsWithUser = appointmentRepository.findAllByStudentId(userId);
                appointmentRepository.deleteAll(appointmentsWithUser);
            } else if (Role.CONSULTANT.getDisplayName().equalsIgnoreCase(role)) {
                List<String> students = userOP.map(user1 -> user.getAppointments().stream().map(Appointment::getStudentId).toList()).orElse(new ArrayList<>());
                for (String stdId : students) {
                    Optional<User> stdOP = userRepository.findById(stdId);
                    if (stdOP.isPresent()) {
                        User std = stdOP.get();
                        List<Appointment> matchingAppointments = std.getAppointments().stream()
                                .filter(a -> a.getConsultantId().equals(userId))
                                .toList();
                        std.getAppointments().removeAll(matchingAppointments);
                        userRepository.save(std);
                    }
                }
                appointmentsWithUser = appointmentRepository.findAllByConsultantId(userId);
                appointmentRepository.deleteAll(appointmentsWithUser);
            }

            userRepository.deleteById(userId);
        }
    }
}
