DROP DATABASE IF EXISTS car_service;
CREATE DATABASE IF NOT EXISTS car_service DEFAULT CHARACTER SET utf8;
USE car_service;

CREATE TABLE IF NOT EXISTS masters(
id INT NOT NULL AUTO_INCREMENT,
name VARCHAR(10),
PRIMARY KEY pk_masters (id)
);

CREATE TABLE IF NOT EXISTS places(
number INT NOT NULL UNIQUE,
busy_status BOOLEAN DEFAULT false,
PRIMARY KEY pk_places (number)
);

CREATE TABLE IF NOT EXISTS orders(
id INT NOT NULL AUTO_INCREMENT,
creation_time DATETIME NOT NULL,
execution_start_time DATETIME NOT NULL,
lead_time DATETIME NOT NULL,
automaker VARCHAR(50),
model VARCHAR(50),
registrationNumber VARCHAR(50),
price DECIMAL(7,2),
status VARCHAR(10),
delete_status BOOLEAN DEFAULT false,
place_number INT NOT NULL,
PRIMARY KEY pk_orders (id),
FOREIGN KEY fk_orders (place_number) REFERENCES places (number)
);

CREATE TABLE IF NOT EXISTS orders_masters(
order_id INT NOT NULL,
master_id INT NOT NULL,
FOREIGN KEY fk_orders_masters_orders (order_id) REFERENCES orders (id),
FOREIGN KEY fk_orders_masters_masters (master_id) REFERENCES masters (id)
);
COMMIT;