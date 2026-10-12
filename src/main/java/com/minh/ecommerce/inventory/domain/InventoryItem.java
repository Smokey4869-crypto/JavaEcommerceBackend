package com.minh.ecommerce.inventory.domain;

public class InventoryItem {
    private final String warehouseId;
    private final String skuId;
    private int onHand;
    private int reserved;

    public InventoryItem(String warehouseId, String skuId, int onHand, int reserved) {
        if (onHand < 0) {
            throw new IllegalArgumentException("On hand quantity cannot be negative");
        }

        if (reserved < 0) {
            throw new IllegalArgumentException("Reserved quantity cannot be negative");
        }

        if (reserved > onHand) {
            throw new IllegalArgumentException("Reserved quantity cannot exceed on hand quantity");
        }

        this.warehouseId = warehouseId;
        this.skuId = skuId;
        this.onHand = onHand;
        this.reserved = reserved;
    }

    public int available() {
        return onHand - reserved;
    }

    public void reserve(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity to reserve cannot be negative");
        }

        if (quantity > available()) {
            throw new IllegalArgumentException("Not enough available inventory to reserve");
        }

        reserved += quantity;
    }

    public void release(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity to release cannot be negative");
        }
        
        if (quantity > reserved) {
            throw new IllegalArgumentException("Cannot release more than reserved quantity");
        }

        reserved -= quantity;
    }

    public void restock(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity to restock cannot be negative");
        }

        onHand += quantity;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public String getSkuId() {
        return skuId;
    }

    public int getOnHand() {
        return onHand;
    }
    
    public int getReserved() {
        return reserved;
    }
}