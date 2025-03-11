package com.edu.consul.service;

import com.edu.consul.model.ProductPurchase;
import com.edu.consul.repository.ProductPurchaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProdPurchaseService {
    private final ProductPurchaseRepository purchaseRepository;

    public String purchaseProduct(ProductPurchase productPurchase) {
        if ((purchaseRepository.findAllByStudentIdAndConsultantIdAndProductName(productPurchase.getStudentId(), productPurchase.getConsultantId(), productPurchase.getProductName()).isEmpty())) {
            purchaseRepository.save(productPurchase);
            return "Purchase Success";
        } else {
            return "You've already purchased the Product";
        }
    }
}
