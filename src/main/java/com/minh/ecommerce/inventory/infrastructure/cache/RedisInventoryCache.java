package com.minh.ecommerce.inventory.infrastructure.cache;

import java.time.Duration;
import java.util.Optional;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import com.minh.ecommerce.inventory.application.InventoryCache;
import com.minh.ecommerce.inventory.domain.InventoryItem;

import tools.jackson.databind.ObjectMapper;

@Repository 
public class RedisInventoryCache implements InventoryCache {
    private static final Duration TTL = Duration.ofSeconds(30);

    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;

    public RedisInventoryCache(StringRedisTemplate redisTemplate, ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public Optional<InventoryItem> get(String warehouseId, String skuId) {
        String value = redisTemplate.opsForValue().get(key(warehouseId, skuId));

        if (value == null) {
            return Optional.empty();
        }

        try {
            InventoryCacheEntry entry = objectMapper.readValue(value, InventoryCacheEntry.class);
            return Optional.of(
                    new InventoryItem(entry.warehouseId(), entry.skuId(), entry.onHand(), entry.reserved()));
        } catch (Exception e) {
            throw new RuntimeException("Failed to deserialize inventory cache entry", e);
        }
    }

    @Override
    public void put(InventoryItem inventoryItem) {
        InventoryCacheEntry entry = new InventoryCacheEntry(
                inventoryItem.getWarehouseId(),
                inventoryItem.getSkuId(),
                inventoryItem.getOnHand(),
                inventoryItem.getReserved());

        try {
            String value = objectMapper.writeValueAsString(entry);
            redisTemplate.opsForValue().set(key(inventoryItem.getWarehouseId(), inventoryItem.getSkuId()), value, TTL);
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize inventory cache entry", e);
        }
    }

    @Override
    public void evict(String warehouseId, String skuId) {
        redisTemplate.delete(key(warehouseId, skuId));
    }

    private String key(String warehouseId, String skuId) {
        return "inventory:%s:%s".formatted(warehouseId, skuId);
    }

    private record InventoryCacheEntry(String warehouseId, String skuId, int onHand, int reserved) {
    }
}
