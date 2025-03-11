package com.edu.consul.controller;

import com.edu.consul.model.ProductPurchase;
import com.edu.consul.service.ProdPurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/purchases")
public class ProductPurchaseController {

    private final ProdPurchaseService purchaseService;

    @PostMapping
    public ResponseEntity<?> purchaseProduct(@RequestBody ProductPurchase productPurchase){
        return ResponseEntity.ok(purchaseService.purchaseProduct(productPurchase));
    }

}
