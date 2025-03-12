package com.edu.consul.repository;

import com.edu.consul.model.ProductPurchase;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProductPurchaseRepository extends MongoRepository<ProductPurchase, String> {
    List<ProductPurchase> findAllByStudentIdAndConsultantIdAndProductName(String studentId, String consultantId, String productName);

    List<ProductPurchase> findAllByConsultantId(String consultantId);
}
