package com.minh.ecommerce.inventory.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.minh.ecommerce.inventory.application.ReserveInventoryService;
import com.minh.ecommerce.inventory.domain.InsufficientInventoryException;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/inventory")
public class InventoryController {
    private final ReserveInventoryService reserveInventoryService;

    public InventoryController(ReserveInventoryService reserveInventoryService) {
        this.reserveInventoryService = reserveInventoryService;
    }

    @PostMapping("/{warehouseId}/{skuId}/reserve")
    public ResponseEntity<Void> reserve(
            @PathVariable String warehouseId,
            @PathVariable String skuId,
            @Valid @RequestBody ReserveInventoryRequest request) {
        reserveInventoryService.reserve(warehouseId, skuId, request.quantity());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/test-error")
    public void testError() {
        throw new InsufficientInventoryException(
                "HCM",
                "SKU123",
                999);
    }
}
