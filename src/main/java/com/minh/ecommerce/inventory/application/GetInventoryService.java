package com.minh.ecommerce.inventory.application;

import org.springframework.stereotype.Service;

import com.minh.ecommerce.inventory.domain.InventoryItem;
import com.minh.ecommerce.inventory.domain.InventoryNotFoundException;
import com.minh.ecommerce.inventory.domain.InventoryRepository;

@Service
public class GetInventoryService {
    private final InventoryRepository inventoryRepository;

    public GetInventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public InventoryItem get(String warehouseId, String skuId) {
        return inventoryRepository.findByWarehouseIdAndSkuId(warehouseId, skuId).orElseThrow(() -> new InventoryNotFoundException(warehouseId, skuId));
    }

}
