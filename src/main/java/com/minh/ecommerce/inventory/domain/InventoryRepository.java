package com.minh.ecommerce.inventory.domain;

import java.util.Optional;

public interface InventoryRepository {
    Optional<InventoryItem> findByWarehouseIdAndSkuId(String warehouseId, String skuId);
    InventoryItem save(InventoryItem inventoryItem);
    boolean tryReserve(String warehouseId, String skuId, int quantity);
}