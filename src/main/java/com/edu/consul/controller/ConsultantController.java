package com.edu.consul.controller;

import com.edu.consul.model.Consultant;
import com.edu.consul.service.ConsultantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/consultants")
public class ConsultantController {

    private final ConsultantService consultantService;

    @GetMapping
    public ResponseEntity<Consultant> fetchConsultantDetails(@RequestParam("consultantId") String consultantId) {
        return new ResponseEntity<>(consultantService.findConsultantDetails(consultantId), HttpStatus.OK);
    }
}
