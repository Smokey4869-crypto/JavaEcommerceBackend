package com.minh.ecommerce.inventory.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface SpringDataInventoryRepository extends JpaRepository<InventoryEntity, InventoryEntityId> {
    @Modifying 
    @Query(
        value = """
            UPDATE inventory
            SET reserved = reserved + :quantity
            WHERE warehouse_id = :warehouseId
            AND sku_id = :skuId
            AND on_hand - reserved >= :quantity
            """,
            nativeQuery = true
    )
    int tryReserve(String warehouseId, String skuId, int quantity);
}