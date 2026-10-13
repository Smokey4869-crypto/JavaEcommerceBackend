package com.minh.ecommerce.inventory.application;

import java.util.Optional;

import com.minh.ecommerce.inventory.domain.InventoryItem;

public interface InventoryCache {
    Optional<InventoryItem> get(String warehouseId, String skuId);
    void put(InventoryItem inventoryItem);
    void evict(String warehouseId, String skuId);
}
