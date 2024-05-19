CREATE SCHEMA IF NOT EXISTS test;
USE test;

DROP TABLE IF EXISTS employees;
CREATE TABLE employees(id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(80), salary VARCHAR(100), position VARCHAR(100));
INSERT INTO employees(name, salary, position) VALUES('Anna Swift', '1200$', 'Manager');
INSERT INTO employees(name, salary, position) VALUES('Jacob Davis', '1500$', 'Mechanic');
INSERT INTO employees(name, salary, position) VALUES('Ethan Harris', '800$', 'Specialist');
INSERT INTO employees(name, salary, position) VALUES('Emily Johnson', '1400$', 'Accountant');

DROP TABLE IF EXISTS servicesList;
CREATE TABLE servicesList(id BIGINT PRIMARY KEY AUTO_INCREMENT, service VARCHAR(100), price VARCHAR(100));
INSERT INTO servicesList(service, price) VALUES('Oil change', '35$ - 50$');
INSERT INTO servicesList(service, price) VALUES('Tire rotation', '20$ - 40$');
INSERT INTO servicesList(service, price) VALUES('Brake inspection and repair', '100$ - 150$');
INSERT INTO servicesList(service, price) VALUES('Wheel alignment', '90$ - 130$');
INSERT INTO servicesList(service, price) VALUES('Battery testing and replacement', '230$ - 300$');
INSERT INTO servicesList(service, price) VALUES('Filter replacement', '200$ - 500$');

DROP TABLE IF EXISTS ordersList;
CREATE TABLE ordersList(id BIGINT PRIMARY KEY AUTO_INCREMENT, orderName VARCHAR(100), customer_name VARCHAR(100), date VARCHAR(100), price VARCHAR(100), status VARCHAR(100));
INSERT INTO ordersList(orderName, customer_name, date, price, status) VALUES('Filter replacement', 'Sophia Martinez', '3/05/2024', '350$', 'ready');
INSERT INTO ordersList(orderName, customer_name, date, price, status) VALUES('Tire rotation', 'William Rodriguez', '4/05/2024', '20$', 'in process');
INSERT INTO ordersList(orderName, customer_name, date, price, status) VALUES('Battery testing', 'Olivia Anderson', '4/05/2024', '250$', 'in process');

DROP TABLE IF EXISTS inventoryList;
CREATE TABLE inventoryList(id BIGINT PRIMARY KEY AUTO_INCREMENT, inventory VARCHAR(80), amount INT);
INSERT INTO inventoryList(inventory, amount) VALUES('Oil filter', 3);
INSERT INTO inventoryList(inventory, amount) VALUES('Torque wrench', 6);
INSERT INTO inventoryList(inventory, amount) VALUES('Coolant', 10);
INSERT INTO inventoryList(inventory, amount) VALUES('Brake fluid', 1);
INSERT INTO inventoryList(inventory, amount) VALUES('Funnel and drain pan', 0);
INSERT INTO inventoryList(inventory, amount) VALUES('Oil filter', 5);
INSERT INTO inventoryList(inventory, amount) VALUES('Ball joints', 45);
INSERT INTO inventoryList(inventory, amount) VALUES('AC leak detection kit', 0);
INSERT INTO inventoryList(inventory, amount) VALUES('Alignment machine', 3);
INSERT INTO inventoryList(inventory, amount) VALUES('Shock absorbers', 1);

DROP TABLE IF EXISTS InventoriesToBuyList;
CREATE TABLE InventoriesToBuyList(id BIGINT PRIMARY KEY AUTO_INCREMENT, inventoryToBuy VARCHAR(80), number INT);
INSERT INTO InventoriesToBuyList(inventoryToBuy, number) VALUES('AC recharge kit', 5);
INSERT INTO InventoriesToBuyList(inventoryToBuy, number) VALUES('Replacement battery', 20);
