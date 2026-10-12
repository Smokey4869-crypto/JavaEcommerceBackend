package com.minh.ecommerce.inventory.api;

import jakarta.validation.constraints.Positive;

public record ReserveInventoryRequest(
    @Positive int quantity
) {}
