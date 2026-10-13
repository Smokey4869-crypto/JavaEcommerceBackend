package com.minh.ecommerce.inventory.application;

import org.springframework.stereotype.Service;
import com.minh.ecommerce.inventory.domain.InventoryRepository;
import org.springframework.transaction.annotation.Transactional;

import com.minh.ecommerce.inventory.domain.InsufficientInventoryException;

@Service
public class ReserveInventoryService {
    private final InventoryRepository inventoryRepository;
    private final InventoryCache inventoryCache;

    public ReserveInventoryService(InventoryRepository inventoryRepository, InventoryCache inventoryCache) {
        this.inventoryRepository = inventoryRepository;
        this.inventoryCache = inventoryCache;
    }

    @Transactional
    public void reserve(String warehouseId, String skuId, int quantity) {
        inventoryRepository
                .findByWarehouseIdAndSkuId(warehouseId, skuId)
                .orElseThrow(() -> new IllegalArgumentException("Inventory item not found"));

        if (quantity <= 0) {
            throw new IllegalArgumentException(
                    "quantity must be positive");
        }
        boolean reserved = inventoryRepository.tryReserve(warehouseId, skuId, quantity);
        if (!reserved) {
            throw new InsufficientInventoryException(warehouseId, skuId, quantity);
        }

        inventoryCache.evict(warehouseId, skuId);
    }
}
