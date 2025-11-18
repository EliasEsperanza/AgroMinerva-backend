CREATE SCHEMA IF NOT EXISTS inventario_db;

CREATE TABLE IF NOT EXISTS inventario_db.inventario_stock (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    producto_id UUID UNIQUE NOT NULL,
    stock INTEGER NOT NULL CHECK (stock >= 0),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_inventario_producto_id ON inventario_db.inventario_stock(producto_id);
CREATE INDEX IF NOT EXISTS idx_inventario_updated_at ON inventario_db.inventario_stock(updated_at);