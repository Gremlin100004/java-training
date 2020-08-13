USE hrynkou_task10;
-- task 1
SELECT model, speed, hd
FROM pc
WHERE price < 500;
-- task 2
SELECT DISTINCT maker
FROM product
WHERE type = 'Printer';
-- task 3
SELECT model, hd, screen
FROM laptop
WHERE price > 1000;
-- task 4
SELECT *
FROM printer
WHERE color = 'y';
-- task 5
SELECT model, speed, hd
FROM pc
WHERE (
cd = '12x'
OR cd = '24x'
)
AND price < 600;
-- task 6
SELECT DISTINCT product.maker, laptop.speed
FROM product
JOIN laptop
ON laptop.model=product.model
WHERE laptop.hd >= 100;
-- task 7
SELECT DISTINCT product.model, pc.price
FROM product
JOIN pc
ON product.model = pc.model
WHERE maker = 'B'
UNION
SELECT DISTINCT product.model, laptop.price
FROM product
JOIN laptop
ON product.model=laptop.model
WHERE maker='B'
UNION
SELECT DISTINCT product.model, printer.price
FROM product
JOIN printer
ON product.model=printer.model
WHERE maker='B';
-- task 8
SELECT DISTINCT maker
FROM product
WHERE type = 'PC'
AND maker
NOT IN (
SELECT maker
FROM product
WHERE type = 'Laptop'
);
-- task 9
SELECT DISTINCT product.maker
FROM product
JOIN pc
ON pc.model=product.model
WHERE pc.speed >= 450;
-- task 10
SELECT model, price
FROM printer
WHERE price = (
SELECT MAX(price)
FROM printer
);
-- task 11
SELECT AVG(speed) AS AVG_SPEED
FROM pc;
-- task 12
SELECT AVG(speed) AS AVG_SPEED
FROM laptop
WHERE price > 1000;
-- task 13
SELECT AVG(pc.speed) AS AVG_SPEED
FROM pc
JOIN product
ON pc.model=product.model
WHERE product.maker = 'A';
-- task 14
SELECT speed, AVG(price) AS AVG_PRICE
FROM pc
GROUP BY speed;
-- task 15
SELECT hd
FROM pc
GROUP BY hd
HAVING COUNT(code) > 1;
-- task 16
SELECT pc1.model AS MODEL, pc2.model, pc1.speed, pc1.ram
FROM pc pc1, pc pc2
WHERE pc1.speed = pc2.speed
AND pc1.ram = pc2.ram
AND pc1.model > pc2.model;
-- task 17
SELECT product.type, laptop.model, laptop.speed
FROM laptop
JOIN product
ON laptop.model=product.model
WHERE laptop.speed < (
SELECT MIN(pc.speed)
FROM pc
);
-- task 18
SELECT DISTINCT product.maker, printer.price
FROM printer
JOIN product
ON printer.model=product.model
WHERE printer.color = 'y'
AND printer.price = (
SELECT MIN(price)
FROM printer
WHERE printer.color = 'y'
);
-- task 19
SELECT product.maker, AVG(laptop.screen) AS AVG_SCREEN
FROM product
JOIN laptop
ON laptop.model=product.model
GROUP BY product.maker;
-- task 20
SELECT maker, COUNT(model)
FROM product
WHERE type = 'PC'
GROUP BY maker
HAVING COUNT(model) > 2;
-- task 21
SELECT product.maker, MAX(pc.price)
FROM product
JOIN pc
ON pc.model=product.model
GROUP BY maker;
-- task 22
SELECT speed, AVG(price) AS AVG_PRICE
FROM pc
WHERE speed > 600
GROUP BY speed;
-- task 23
SELECT DISTINCT maker
FROM product
JOIN pc
ON product.model=pc.model
WHERE speed>=750
AND maker
IN (
SELECT maker
FROM product
JOIN laptop
ON product.model=laptop.model
WHERE speed>=750
);
-- task 24
SELECT model
FROM (
SELECT model, price
FROM pc
UNION
SELECT model, price
FROM laptop
UNION
SELECT model, price
FROM printer
) general_table_model
WHERE price = (
SELECT MAX(price)
FROM (SELECT price
FROM pc
UNION
SELECT price
FROM laptop
UNION
SELECT price
FROM printer
) general_table_price
);
-- task 25
SELECT DISTINCT maker
FROM product
WHERE type='printer'
AND maker
IN (
SELECT maker
FROM product
JOIN pc
ON product.model = pc.model
WHERE ram = (
SELECT MIN(ram)
 FROM pc
 )
AND speed = (
SELECT MAX(speed)
FROM pc
WHERE ram = (
SELECT MIN(ram) FROM pc
)));