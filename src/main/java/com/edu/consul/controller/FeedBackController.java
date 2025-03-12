package com.edu.consul.controller;

import com.edu.consul.model.FeedBack;
import com.edu.consul.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/feedbacks")
public class FeedBackController {

    private final FeedbackService service;

    @PostMapping("/add")
    public ResponseEntity<?> addFeedback(@RequestBody FeedBack feedBack){
        return ResponseEntity.ok(service.addFeedBack(feedBack));
    }

    @GetMapping("/fetchAll")
    public ResponseEntity<?> fetchAll(){
        return ResponseEntity.ok(service.fetchAll());
    }
}
