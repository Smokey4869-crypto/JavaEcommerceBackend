package com.minh.ecommerce.inventory.domain;

public class InventoryNotFoundException extends RuntimeException {
    public InventoryNotFoundException(String warehouseId, String skuId) {
        super("Inventory item not found for warehouse: " + warehouseId + " and SKU: " + skuId);
    }
}
