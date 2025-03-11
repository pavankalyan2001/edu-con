package com.edu.consul.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "productpurchases")
public class ProductPurchase {
    @Id
    private String id;
    private String studentId;
    private String consultantId;
    private String price;
    private String productName;
}
