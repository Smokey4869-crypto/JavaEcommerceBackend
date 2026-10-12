package com.minh.ecommerce.inventory.infrastructure.persistence;

import java.io.Serializable;
import java.util.Objects;

public class InventoryEntityId implements Serializable {
    private String warehouseId;
    private String skuId;

    public InventoryEntityId() {}

    public InventoryEntityId(String warehouseId, String skuId) {
        this.warehouseId = warehouseId;
        this.skuId = skuId;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public String getSkuId() {
        return skuId;
    }

    @Override 
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InventoryEntityId that)) return false;
        return Objects.equals(warehouseId, that.warehouseId) && Objects.equals(skuId, that.skuId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(warehouseId, skuId);
    }
}
