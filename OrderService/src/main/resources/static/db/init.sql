CREATE SCHEMA IF NOT EXISTS order_service;

DROP TABLE IF EXISTS order_service.orders CASCADE;
DROP TABLE IF EXISTS order_service.order_details CASCADE;

CREATE TABLE IF NOT EXISTS order_service.orders
(
    id              UUID PRIMARY KEY  NOT NULL,
    order_number    VARCHAR(5) UNIQUE NOT NULL,
    order_sum       DOUBLE PRECISION  NOT NULL,
    order_date      DATE              NOT NULL,
    order_recipient VARCHAR(255)      NOT NULL,
    order_address   VARCHAR(255)      NOT NULL,
    order_payment   VARCHAR(255)      NOT NULL,
    order_delivery  VARCHAR(255)      NOT NULL
);

CREATE TABLE IF NOT EXISTS order_service.order_details
(
    id              SERIAL PRIMARY KEY NOT NULL,
    product_article INTEGER            NOT NULL,
    product_name    VARCHAR(255)       NOT NULL,
    product_amount  INTEGER            NOT NULL,
    product_cost    DOUBLE PRECISION   NOT NULL,
    order_id        UUID               NOT NULL REFERENCES order_service.orders (id) ON DELETE CASCADE
);