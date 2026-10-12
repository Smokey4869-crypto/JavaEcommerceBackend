package com.minh.ecommerce.inventory.domain;

public class InsufficientInventoryException extends RuntimeException {
    public InsufficientInventoryException(
        String warehouseId, String skuId, int requestedQuantity
    ) {
        super("Insufficient inventory for SKU " + skuId + " in warehouse " + warehouseId + ". Requested quantity: " + requestedQuantity);
    }
}
