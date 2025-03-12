package com.edu.consul.controller;

import com.edu.consul.service.StatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stats")
public class StatsController {

    private final StatsService statsService;

    @GetMapping("/conStatuses")
    public ResponseEntity<?> conStatuses(@RequestParam String consultantId){
        return ResponseEntity.ok(statsService.getConStatuses(consultantId));
    }

    @GetMapping("/sales")
    public ResponseEntity<?> conSales(@RequestParam String consultantId){
        return ResponseEntity.ok(statsService.salesMap(consultantId));
    }
}
