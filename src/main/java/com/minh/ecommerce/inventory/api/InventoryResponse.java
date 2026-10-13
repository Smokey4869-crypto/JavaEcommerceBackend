package com.minh.ecommerce.inventory.api;

public record InventoryResponse(
    String warehouseId,
    String skuId,
    int onHand, 
    int reserved,
    int available
) {
}