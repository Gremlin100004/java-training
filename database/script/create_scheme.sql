CREATE DATABASE IF NOT EXISTS car_service DEFAULT CHARACTER SET utf8;
USE car_service;

CREATE TABLE IF NOT EXISTS masters(
id INT NOT NULL AUTO_INCREMENT,
name VARCHAR(10),
delete_status BOOLEAN DEFAULT false,
PRIMARY KEY pk_masters (id)
);

CREATE TABLE IF NOT EXISTS places(
id INT NOT NULL AUTO_INCREMENT,
number INT NOT NULL UNIQUE,
busy_status BOOLEAN DEFAULT false,
delete_status BOOLEAN DEFAULT false,
PRIMARY KEY pk_places (id)
);

CREATE TABLE IF NOT EXISTS orders(
id INT NOT NULL AUTO_INCREMENT,
creation_time DATETIME NOT NULL,
execution_start_time DATETIME NOT NULL,
lead_time DATETIME NOT NULL,
automaker VARCHAR(50),
model VARCHAR(50),
registration_number VARCHAR(50),
price DECIMAL(7,2),
status VARCHAR(10),
delete_status BOOLEAN DEFAULT false,
place_id INT NOT NULL,
PRIMARY KEY pk_orders (id)
);

CREATE TABLE IF NOT EXISTS orders_masters(
order_id INT NOT NULL,
master_id INT NOT NULL
);

ALTER TABLE orders
ADD CONSTRAINT fk_orders
FOREIGN KEY (place_id)
REFERENCES places (id) ON DELETE CASCADE;

ALTER TABLE orders_masters
ADD CONSTRAINT fk_orders_masters_orders
FOREIGN KEY (order_id)
REFERENCES orders (id) ON DELETE CASCADE;

ALTER TABLE JOIN places ON places.id = orders.place_id
ADD CONSTRAINT fk_orders_masters_masters
FOREIGN KEY (master_id)
REFERENCES masters (id) ON DELETE CASCADE;

CREATE UNIQUE INDEX masters_id_idx
ON masters (id);
CREATE UNIQUE INDEX places_id_idx
ON places (id);
CREATE UNIQUE INDEX orders_id_idx
ON orders (id);
COMMIT;



SELECT COUNT(places.id) AS amount_of_elements FROM orders JOIN places ON places.id = orders.place_id WHERE orders.lead_time > '2020-09-15 00:00' AND orders.lead_time < '2020-09-16 00:00';

SELECT COUNT(order_id) AS amount_of_elements FROM orders_masters WHERE master_id=1;

SELECT orders.id, orders.creation_time, orders.execution_start_time, orders.lead_time, orders.automaker, orders.model, orders.registration_number, orders.price, orders.status, orders.delete_status, places.id AS order_place_id, places.number AS place_number, places.busy_status AS place_busy_status, places.delete_status AS place_delete_status FROM orders JOIN places ON orders.place_id = places.id WHERE orders.status='PERFORM' ORDER BY price;

SELECT orders.id, orders.creation_time, orders.execution_start_time, orders.lead_time, orders.automaker, orders.model, orders.registration_number, orders.price, orders.status, orders.delete_status, places.id AS order_place_id, places.number AS place_number, places.busy_status AS place_busy_status, places.delete_status AS place_delete_status  FROM orders JOIN orders_masters ON orders_masters.order_id = orders.id JOIN places ON orders.place_id=places.id WHERE orders_masters.master_id=1;