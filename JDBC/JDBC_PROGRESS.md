# JDBC 學習進度交接

> 給未來的我 / 新的 Claude session：JDBC 系統性主幹學到哪、環境怎麼起、下一步做什麼。
> 最後更新：2026-06-12

## 我在學什麼
用「系統性主幹」複習 JDBC——**不照老師 `eeit22_java` 的 Jdbc01~27 編號**，自己重排成依賴順序。
教學模式：**逐行陪寫 / 手刻練習**（卡住才給一句提示，不給骨架、不給組好的答案）。

## 主幹六步 + 狀態
| 步驟 | 主題 | 檔案 | 狀態 |
|---|---|---|---|
| 1 | 連線 → 第一筆查詢 | `Step1Connect.java` | code 已寫，**待跑驗收**（還沒看過實際輸出） |
| 2 | Statement → PreparedStatement | `Step2PreparedStmt.java` | 主體可跑；**驗收題 debug 中**（已修 `?`引號 + 表名，剩 `>`→`<=`、`ORDER BY price`、printf 洞數） |
| 3 | ResultSet → 讀進 Java 物件（建 `Member` 類別） | （下一隻） | ⏳ **下一步從這開始** |
| 4 | 完整 CRUD（增刪改查） | - | ⏳ |
| 5 | Transaction（轉帳 rollback） | - | ⏳ |
| 6 | 收斂成 DAO（entity / interface / impl） | - | ⏳ |

## 環境（回家先起這些）
- **DB**：MAMP 的 MySQL，`localhost:3306`，帳密 `root` / `root`
- **資料庫 `learnjdbc`**（三表：member 16 筆 / product 8 筆 / orders 8 筆）
  - 沒有就匯入 `JDBC/learnjdbc_setup.sql`（重跑安全，DROP 後重建）
- **驅動**：`mysql-connector-j-9.7.0` 已在專案 build path
- **練習 package**：`tw.harry.learnByMyself`

## 已透過問答表現理解的觀念（但 code 尚未實跑驗收，勿標「已會」）
- JDBC 脊椎鏈：`DriverManager → Connection → Statement → ResultSet`，每個由上一個「生」出（**factory method**；看不到 `new` 是因為 `new` 在驅動裡）
- 全程操作「**介面**」不碰 driver 實作類別 → 換 DB 不改 code
- **try-with-resources 看「宣告的變數」關閉**，一個變數關一個；關閉往下傳不往上 → 三個資源都要各自宣告
- 為何不能「只關 conn」：迴圈 / 連線池會 cursor 用盡（ORA-01000 那類）
- **PreparedStatement**：`?` 挖空會變動的值、`setXxx` 塞、**index 從 1（不是 0）**、`execute` 不帶 SQL；防 SQL injection + 值與語法分離
- **`prepareStatement(sql)` = 預編譯**：把帶 `?` 的 SQL 提早交給 DB 解析 + 建執行計畫；Statement 是 execute 時才給 SQL、每次重編譯 → PreparedStatement 重複執行較快
- **`?` 只能綁「值」，不能綁「表名/欄名」**：因為預編譯時 DB 必須先知道結構（表/欄/索引），`?` 是編譯後才填的洞，只能放值。這跟「防注入」是同一原因兩面——結構先凍結
- **MySQL 引號**：反引號 `` ` `` = 識別字(表名/欄名)、雙引號 `"` = 字串(預設模式，別拿來框表名會語法錯)、單引號 `'` = 字串值、裸寫 = 識別字（最乾淨）
- **printf 洞 vs 值**：洞比值多 → 丟 `MissingFormatArgumentException`；值比洞多 → 多的默默丟掉（會漏印，不報錯）

## 待做 / homework
- [ ] **跑 Step1Connect、Step2PreparedStmt**，確認真的查得到（補實跑驗收，才能把 Step 1/2 標成過）
- [ ] **收掉 Step 2 驗收題**（差最後 3 修）：`>`→`<=`、加 `ORDER BY price`、printf 洞數對上要印的欄（name/category/price）。驗證：周邊 + 2000 → 筆電支架(750) / 無線滑鼠(890) / USB-C 集線器(1200)，依價由低到高
- [ ] （bonus）注入體驗：`name = ?` 餵 `' OR '1'='1'`，比較 Statement 拼接 vs PreparedStatement
- [ ] **進 Step 3**：建 `Member` 類別，把 ResultSet 一列(row) 對應成一個 `Member` 物件（這就是 ORM 的手工版）

## 相關連結
- 老師 repo：https://github.com/bradchao/eeit22_java/tree/master/JDBC
- 我的 repo：https://github.com/harry930216/java_lesson
- Notion 筆記頁「JDBC」（學習導覽章已建；筆記由另一個 session 負責，本檔只記 code 進度）

---
回家流程：① 起 MAMP MySQL ② 若無 DB 就匯入 `learnjdbc_setup.sql` ③ 打開 `tw.harry.learnByMyself` 繼續 homework。
