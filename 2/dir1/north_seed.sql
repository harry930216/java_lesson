-- ============================================================
-- north 資料庫種子（Northwind 子集）— 給 HarryPeng/2 的 h2 練習用
-- 涵蓋：Harry02(BLONP)、Harry03-06(employees)、Harry07(訂單 10249 跨表)
-- 執行方式（擇一）：
--   1. MySQL Workbench 開這個檔 → 全選執行
--   2. 命令列：mysql -u root -p < north_seed.sql
-- 註：欄位名大小寫對齊 entity 的 @Column；OrderDate 用 VARCHAR 對應 entity 的 String orderDate
-- ============================================================
SET NAMES utf8mb4;

CREATE DATABASE IF NOT EXISTS north DEFAULT CHARACTER SET utf8mb4;
USE north;

DROP TABLE IF EXISTS orderdetails;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS employees;
DROP TABLE IF EXISTS customers;

-- 1. customers（Customer entity）
CREATE TABLE customers (
  CustomerID  VARCHAR(5)  NOT NULL,
  CompanyName VARCHAR(40),
  PRIMARY KEY (CustomerID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 2. employees（Employee entity）
CREATE TABLE employees (
  EmployeeID INT NOT NULL,
  LastName   VARCHAR(20),
  FirstName  VARCHAR(10),
  Title      VARCHAR(30),
  PRIMARY KEY (EmployeeID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 3. products（Product entity）
CREATE TABLE products (
  ProductID   INT NOT NULL,
  ProductName VARCHAR(40),
  PRIMARY KEY (ProductID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 4. orders（Order entity；OrderDate 用 VARCHAR 對應 String）
CREATE TABLE orders (
  OrderID    INT NOT NULL,
  OrderDate  VARCHAR(30),
  CustomerID VARCHAR(5),
  EmployeeID INT,
  PRIMARY KEY (OrderID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 5. orderdetails（OrderDetail entity；複合主鍵 OrderID+ProductID）
CREATE TABLE orderdetails (
  OrderID   INT NOT NULL,
  ProductID INT NOT NULL,
  UnitPrice DECIMAL(10,4),
  Quantity  INT,
  PRIMARY KEY (OrderID, ProductID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ---------- 資料 ----------
INSERT INTO customers (CustomerID, CompanyName) VALUES
('ALFKI','Alfreds Futterkiste'),
('BLONP','Blondesddsl père et fils'),
('TOMSP','Toms Spezialitäten'),
('VINET','Vins et alcools Chevalier');

INSERT INTO employees (EmployeeID, LastName, FirstName, Title) VALUES
(1,'Davolio','Nancy','Sales Representative'),
(2,'Fuller','Andrew','Vice President, Sales'),
(3,'Leverling','Janet','Sales Representative'),
(4,'Peacock','Margaret','Sales Representative'),
(5,'Buchanan','Steven','Sales Manager'),
(6,'Suyama','Michael','Sales Representative'),
(7,'King','Robert','Sales Representative'),
(8,'Callahan','Laura','Inside Sales Coordinator'),
(9,'Dodsworth','Anne','Sales Representative');

INSERT INTO products (ProductID, ProductName) VALUES
(11,'Queso Cabrales'),
(14,'Tofu'),
(42,'Singaporean Hokkien Fried Mee'),
(51,'Manjimup Dried Apples'),
(72,'Mozzarella di Giovanni');

INSERT INTO orders (OrderID, OrderDate, CustomerID, EmployeeID) VALUES
(10248,'1996-07-04','VINET',5),
(10249,'1996-07-05','TOMSP',6);

INSERT INTO orderdetails (OrderID, ProductID, UnitPrice, Quantity) VALUES
(10248,11,14.0000,12),
(10248,42,9.8000,10),
(10248,72,34.8000,5),
(10249,14,18.6000,9),
(10249,51,42.4000,40);
