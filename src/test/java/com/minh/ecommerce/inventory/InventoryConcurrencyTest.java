package com.minh.ecommerce.inventory;

import com.minh.ecommerce.inventory.application.ReserveInventoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class InventoryConcurrencyTest {

    @Autowired
    private ReserveInventoryService reserveInventoryService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void should_not_oversell_when_many_requests_reserve_concurrently()
        throws Exception {

        jdbcTemplate.update("""
            UPDATE inventory
            SET on_hand = 10,
                reserved = 0
            WHERE warehouse_id = 'HCM'
              AND sku_id = 'SKU123'
        """);

        int requestCount = 20;

        ExecutorService executor =
            Executors.newFixedThreadPool(requestCount);

        CountDownLatch ready = new CountDownLatch(requestCount);
        CountDownLatch start = new CountDownLatch(1);

        List<Future<Boolean>> futures = new ArrayList<>();

        for (int i = 0; i < requestCount; i++) {
            futures.add(executor.submit(() -> {
                ready.countDown();
                start.await();

                try {
                    reserveInventoryService.reserve(
                        "HCM",
                        "SKU123",
                        1
                    );

                    return true;
                } catch (Exception e) {
                    return false;
                }
            }));
        }

        ready.await();

        start.countDown();

        int successCount = 0;

        for (Future<Boolean> future : futures) {
            if (future.get()) {
                successCount++;
            }
        }

        executor.shutdown();

        Integer reserved = jdbcTemplate.queryForObject("""
            SELECT reserved
            FROM inventory
            WHERE warehouse_id = 'HCM'
              AND sku_id = 'SKU123'
        """, Integer.class);

        assertEquals(10, successCount);
        assertEquals(10, reserved);
    }
}