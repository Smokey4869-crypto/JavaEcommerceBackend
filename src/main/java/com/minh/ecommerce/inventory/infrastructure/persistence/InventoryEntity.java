package com.minh.ecommerce.inventory.infrastructure.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

@Entity
@Table (name = "inventory")
@IdClass (InventoryEntityId.class)
public class InventoryEntity {
    @Id 
    @Column(name = "warehouse_id")
    private String warehouseId;
    @Id
    @Column(name = "sku_id")
    private String skuId;
    @Column (name = "on_hand", nullable = false)
    private int onHand;
    @Column (name = "reserved", nullable = false)
    private int reserved;

    protected InventoryEntity() {}
    public InventoryEntity(String warehouseId, String skuId, int onHand, int reserved) {
        this.warehouseId = warehouseId;
        this.skuId = skuId;
        this.onHand = onHand;
        this.reserved = reserved;
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
