USE car_service;
INSERT INTO masters VALUES
(NULL, 'Petya', 2, false),
(NULL, 'Vasya', 2, false),
(NULL, 'Georgiy', 1, false),
(NULL, 'Antonina', 1, false),
(NULL, 'Igor', 1, false),
(NULL, 'Dmitriy', 1, false),
(NULL, 'Aleksandr', 1, false),
(NULL, 'Jon', 1, false),
(NULL, 'Konstantin', 1, false),
(NULL, 'Andrey', 1, false),
(NULL, 'Pasha', 1, false),
(NULL, 'Egor', 1, false),
(NULL, 'Slava', 1, false),
(NULL, 'Valera', 1, false),
(NULL, 'Vitalik', 1, false),
(NULL, 'Sergei', 1, false),
(NULL, 'Danial', 1, false),
(NULL, 'Ivan', 1, false),
(NULL, 'Nikolai', 1, false),
(NULL, 'Patrik', 1, false),
(NULL, 'Semen', 1, false),
(NULL, 'Uriy', 1, false);

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