USE hrinkov_car_service;

ALTER TABLE masters AUTO_INCREMENT = 1;

INSERT INTO masters VALUES
(NULL, 'Petya', false, 2),
(NULL, 'Vasya', false, 2),
(NULL, 'Georgiy', false, 1),
(NULL, 'Antonina', false, 1),
(NULL, 'Igor', false, 1),
(NULL, 'Dmitriy', false, 1),
(NULL, 'Aleksandr', false, 1),
(NULL, 'Jon', false, 1),
(NULL, 'Konstantin', false, 1),
(NULL, 'Andrey', false, 1),
(NULL, 'Pasha', false, 1),
(NULL, 'Egor', false, 1),
(NULL, 'Slava', false, 1),
(NULL, 'Valera', false, 1),
(NULL, 'Vitalik', false, 1),
(NULL, 'Sergei', false, 1),
(NULL, 'Danial', false, 1),
(NULL, 'Ivan', false, 1),
(NULL, 'Nikolai', false, 1),
(NULL, 'Patrik', false, 1),
(NULL, 'Semen', false, 1),
(NULL, 'Uriy', false, 1);

ALTER TABLE places AUTO_INCREMENT = 1;

INSERT INTO places VALUES
(NULL, 1, false, false),
(NULL, 2, false, false),
(NULL, 3, false, false),
(NULL, 4, false, false),
(NULL, 5, false, false),
(NULL, 6, false, false),
(NULL, 7, false, false),
(NULL, 8, false, false),
(NULL, 9, false, false),
(NULL, 10, false, false),
(NULL, 11, false, false),
(NULL, 12, false, false);

ALTER TABLE orders AUTO_INCREMENT = 1;

INSERT INTO orders VALUES
(NULL, NOW(), '2020-09-11 10:00', '2020-09-11 18:00', 'Lexus', 'LS', '1234 AB-7', '360.99', 'WAIT', false, 1),
(NULL, NOW(), '2020-09-11 10:00', '2020-09-11 18:00', 'Mercedes', 'A 200', '3234 AB-7', '600.23', 'WAIT', false, 2),
(NULL, NOW(), '2020-09-12 14:00', '2020-09-13 10:00', 'BMW', 'X6', '2222 AB-7', '457.34', 'WAIT', false, 3),
(NULL, NOW(), '2020-09-13 15:00', '2020-09-14 18:00', 'Audi', 'A6', '1111 AB-7', '10020.99', 'WAIT', false, 4),
(NULL, NOW(), '2020-09-14 15:00', '2020-09-15 10:00', 'Opel', 'Insignia', '4444 AB-7', '443.65', 'WAIT', false, 5),
(NULL, NOW(), '2020-09-15 15:00', '2020-09-16 10:00', 'Audi', 'A4', '5555 AB-7', '321.67', 'WAIT', false, 6),
(NULL, NOW(), '2020-09-16 15:00', '2020-09-17 10:00', 'Ford', 'Mondeo', '6666 AB-7', '367.46', 'WAIT', false, 7),
(NULL, NOW(), '2020-09-16 15:00', '2020-09-18 10:00', 'KIA', 'Optima', '7777 AB-7', '642.12', 'WAIT', false, 8),
(NULL, NOW(), '2020-09-16 15:00', '2020-09-19 10:00', 'Infinity', 'QX30', '8888 AB-7', '735.78', 'WAIT', false, 9),
(NULL, NOW(), '2020-09-16 15:00', '2020-09-20 10:00', 'Mazda', 'RX7', '9999 AB-7', '135.39', 'WAIT', false, 10),
(NULL, NOW(), '2020-09-16 15:00', '2020-09-20 10:00', 'Chevrolet', 'Epica', '8484 AB-7', '335.62', 'WAIT', false, 11),
(NULL, NOW(), '2020-09-16 15:00', '2020-09-20 10:00', 'Lexus', 'LS', '1919 AB-7', '867.31', 'WAIT', false, 12);

INSERT INTO orders_masters VALUES
(1, 1), (1, 2), (2, 3), (2, 4),
(3, 5), (3, 6), (4, 7), (4, 8),
(5, 9), (5, 10), (6, 11), (6, 12),
(7, 13), (7, 14), (8, 15), (8, 16),
(9, 17), (9, 18), (10, 19), (10, 20),
(11, 21), (11, 22), (12, 1), (12, 2);

COMMIT;