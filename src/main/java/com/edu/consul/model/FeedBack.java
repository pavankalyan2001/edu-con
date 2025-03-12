package com.edu.consul.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "feedbacks")
public class FeedBack {
    @Id
    private String id;
    private String text;
    private String reply;
    private String studentId;
    private String consultantId;
}
