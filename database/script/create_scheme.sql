DROP DATABASE IF EXISTS hrinkov_car_service;

CREATE DATABASE IF NOT EXISTS hrinkov_car_service DEFAULT CHARACTER SET utf8;

USE hrinkov_car_service;

CREATE TABLE IF NOT EXISTS masters(
id INT NOT NULL AUTO_INCREMENT,
name VARCHAR(20),
number_orders INT DEFAULT 0,
is_deleted BOOLEAN DEFAULT false,
PRIMARY KEY pk_masters (id)
);

CREATE TABLE IF NOT EXISTS places(
id INT NOT NULL AUTO_INCREMENT,
number INT NOT NULL UNIQUE,
is_busy BOOLEAN DEFAULT false,
is_deleted BOOLEAN DEFAULT false,
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
is_deleted BOOLEAN DEFAULT false,
place_id INT NOT NULL,
PRIMARY KEY pk_orders (id)
);

CREATE TABLE IF NOT EXISTS roles(
id INT NOT NULL AUTO_INCREMENT,
name VARCHAR(50) NOT NULL UNIQUE,
PRIMARY KEY pk_roles (id)
);

CREATE TABLE IF NOT EXISTS users(
id INT NOT NULL AUTO_INCREMENT,
email VARCHAR(50) NOT NULL UNIQUE,
password VARCHAR(60) NOT NULL UNIQUE,
role_id INT NOT NULL,
PRIMARY KEY pk_users (id)
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

ALTER TABLE orders_masters
ADD CONSTRAINT fk_orders_masters_masters
FOREIGN KEY (master_id)
REFERENCES masters (id) ON DELETE CASCADE;

ALTER TABLE users
ADD CONSTRAINT fk_users
FOREIGN KEY (role_id)
REFERENCES roles (id) ON DELETE CASCADE;

CREATE UNIQUE INDEX masters_id_idx
ON masters (id);

CREATE UNIQUE INDEX places_id_idx
ON places (id);

CREATE UNIQUE INDEX orders_id_idx
ON orders (id);

CREATE UNIQUE INDEX roles_id_idx
ON roles (id);

CREATE UNIQUE INDEX users_id_idx
ON users (id);

COMMIT;