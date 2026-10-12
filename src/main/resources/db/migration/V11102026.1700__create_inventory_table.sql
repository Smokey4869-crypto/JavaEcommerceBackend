CREATE TABLE inventory (
    warehouse_id VARCHAR(255) NOT NULL, 
    sku_id VARCHAR(255) NOT NULL,
    on_hand INTEGER NOT NULL, 
    reserved INTEGER NOT NULL,

    PRIMARY KEY (warehouse_id, sku_id),

    CHECK (on_hand >= 0),
    CHECK (reserved >= 0),
    CHECK (reserved <= on_hand)
);