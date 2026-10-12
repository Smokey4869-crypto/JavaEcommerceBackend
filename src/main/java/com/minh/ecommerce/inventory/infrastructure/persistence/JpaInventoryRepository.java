package com.minh.ecommerce.inventory.infrastructure.persistence;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.minh.ecommerce.inventory.domain.InventoryItem;
import com.minh.ecommerce.inventory.domain.InventoryRepository;

@Repository 
public class JpaInventoryRepository implements InventoryRepository {
    private final SpringDataInventoryRepository springDataRepository;

    public JpaInventoryRepository(SpringDataInventoryRepository springDataRepository) {
        this.springDataRepository = springDataRepository;
    }

    @Override 
    public Optional<InventoryItem> findByWarehouseIdAndSkuId(String warehouseId, String skuId) {
        InventoryEntityId id = new InventoryEntityId(warehouseId, skuId);
        return springDataRepository.findById(id).map(entity -> this.toDomain(entity));
    }

    @Override 
    public InventoryItem save(InventoryItem inventoryItem) {
        InventoryEntity entity = this.toEntity(inventoryItem);
        InventoryEntity savedEntity = springDataRepository.save(entity);
        return this.toDomain(savedEntity);
    }

    @Override
    public boolean tryReserve(String warehouseId, String skuId, int quantity) {
        int updatedRows = springDataRepository.tryReserve(warehouseId, skuId, quantity);
        return updatedRows == 1;
    }

    private InventoryItem toDomain(InventoryEntity entity) {
        return new InventoryItem(entity.getWarehouseId(), entity.getSkuId(), entity.getOnHand(), entity.getReserved());
    }

    private InventoryEntity toEntity(InventoryItem inventoryItem) {
        return new InventoryEntity(inventoryItem.getWarehouseId(), inventoryItem.getSkuId(), inventoryItem.getOnHand(), inventoryItem.getReserved());
    }
}
