package com.minh.ecommerce.inventory.application;

import org.springframework.stereotype.Service;

import com.minh.ecommerce.inventory.domain.InventoryItem;
import com.minh.ecommerce.inventory.domain.InventoryNotFoundException;
import com.minh.ecommerce.inventory.domain.InventoryRepository;

@Service
public class GetInventoryService {
    private final InventoryRepository inventoryRepository;
    private final InventoryCache inventoryCache;

    public GetInventoryService(InventoryRepository inventoryRepository, InventoryCache inventoryCache) {
        this.inventoryRepository = inventoryRepository;
        this.inventoryCache = inventoryCache;
    }

    public InventoryItem get(String warehouseId, String skuId) {
        return inventoryCache.get(warehouseId, skuId).orElseGet(() -> loadFromDatabase(warehouseId, skuId));
    }

    private InventoryItem loadFromDatabase(String warehouseId, String skuId) {
        InventoryItem inventoryItem = inventoryRepository
                .findByWarehouseIdAndSkuId(warehouseId, skuId)
                .orElseThrow(() -> new InventoryNotFoundException(warehouseId, skuId));
        inventoryCache.put(inventoryItem);
        return inventoryItem;
    }
}
