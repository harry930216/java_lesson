-- ============================================================
-- learnjdbc 資料庫完整建置腳本（JDBC 主幹練習用）
-- 三張表：member（會員）/ product（商品）/ orders（訂單）
-- 環境：MySQL (MAMP)，編碼 utf8mb4
--
-- 用法 A（推薦）：phpMyAdmin → 上方「匯入 Import」→ 選這個檔 → 執行
-- 用法 B：phpMyAdmin → 「SQL」分頁 → 全部貼上 → 執行
--
-- 重跑安全：每張表都先 DROP 再建，跑幾次都得到乾淨資料。
-- 若中文變亂碼：匯入時把字元集選 utf-8。
-- ============================================================

CREATE DATABASE IF NOT EXISTS learnjdbc DEFAULT CHARACTER SET utf8mb4;
USE learnjdbc;

-- ---------- member（會員）----------
-- 埋料：Frank/Jack 的 email 是 NULL（練 getString 取 NULL）
--       O'Brien 名字含單引號（練注入 / 字串跳脫）
--       小明/小華 中文（測 utf8mb4 編碼）
DROP TABLE IF EXISTS member;
CREATE TABLE member (
  id    INT PRIMARY KEY AUTO_INCREMENT,
  name  VARCHAR(50)  NOT NULL,
  email VARCHAR(100)
);
INSERT INTO member (name, email) VALUES
  ('Harry',    'harry@example.com'),
  ('Brad',     'brad@example.com'),
  ('Amy',      'amy@example.com'),
  ('Cathy',    'cathy@example.com'),
  ('David',    'david@example.com'),
  ('Emma',     'emma@example.com'),
  ('Frank',    NULL),
  ('Grace',    'grace@example.com'),
  ('Henry',    'henry@example.com'),
  ('Ivy',      'ivy@example.com'),
  ('Jack',     NULL),
  ('Karen',    'karen@example.com'),
  ('Leo',      'leo@example.com'),
  ('O''Brien', 'obrien@example.com'),
  ('小明',      'ming@example.com'),
  ('小華',      'hua@example.com');

-- ---------- product（商品）----------
DROP TABLE IF EXISTS product;
CREATE TABLE product (
  id       INT PRIMARY KEY AUTO_INCREMENT,
  name     VARCHAR(50) NOT NULL,
  category VARCHAR(30) NOT NULL,
  price    INT NOT NULL,
  stock    INT NOT NULL DEFAULT 0
);
INSERT INTO product (name, category, price, stock) VALUES
  ('機械鍵盤',     '周邊', 2500, 30),
  ('無線滑鼠',     '周邊', 890,  50),
  ('27吋螢幕',     '螢幕', 6500, 12),
  ('USB-C 集線器', '周邊', 1200, 0),
  ('人體工學椅',   '家具', 8900, 5),
  ('筆電支架',     '周邊', 750,  40),
  ('降噪耳機',     '音訊', 4200, 18),
  ('webcam',       '視訊', 1500, 25);

-- ---------- orders（訂單）----------
-- 關係（之後 JOIN 用）：orders.member_id → member.id；orders.product_id → product.id
DROP TABLE IF EXISTS orders;
CREATE TABLE orders (
  id         INT PRIMARY KEY AUTO_INCREMENT,
  member_id  INT NOT NULL,
  product_id INT NOT NULL,
  quantity   INT NOT NULL,
  order_date DATE NOT NULL
);
INSERT INTO orders (member_id, product_id, quantity, order_date) VALUES
  (1, 1, 1, '2026-01-15'),
  (1, 2, 2, '2026-02-03'),
  (2, 3, 1, '2026-02-20'),
  (3, 5, 1, '2026-03-10'),
  (2, 7, 1, '2026-03-22'),
  (1, 4, 3, '2026-04-05'),
  (4, 2, 1, '2026-04-18'),
  (3, 6, 2, '2026-05-01');

-- ============================================================
-- 完成。驗證：SELECT COUNT(*) FROM member;  -- 應為 16
--            SELECT COUNT(*) FROM product; -- 應為 8
--            SELECT COUNT(*) FROM orders;  -- 應為 8
-- ============================================================
