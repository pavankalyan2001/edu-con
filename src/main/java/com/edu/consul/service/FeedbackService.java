package com.edu.consul.service;

import com.edu.consul.model.FeedBack;
import com.edu.consul.repository.FeedBackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedbackService {
    private final FeedBackRepository feedBackRepository;

    public String addFeedBack(FeedBack feedBack) {
        feedBackRepository.save(feedBack);
        return "Feedback added!";
    }

    public List<FeedBack> fetchAll() {
        return feedBackRepository.findAll();
    }
}
