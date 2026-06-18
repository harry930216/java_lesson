# Hibernate 跟課 交接檔（HANDOFF）

> 給「下一個 session / 下一個 Claude」看的：這工作區在做什麼、做到哪、規則、怎麼接手。
> 最後更新：2026-06-17，已同步老師 commit **a73e2c8**。

## 0. 這是什麼
EEIT22 Java 課程 Hibernate 單元的跟課練習。**逐個 commit 鏡像老師的程式碼**到本工作區，Claude 補教學註解。

## 1. 工作區 & 來源
- 工作區：`C:\Users\User\eclipse-workspace\HarryPeng\1`（Eclipse 專案名 Hibernate，Maven，JDK 21）
- 依賴：hibernate-core 6.5.2 / mysql-connector 8.0.33 / jbcrypt 0.4
- 鏡像來源：`github.com/bradchao/eeit22_java` 的 `hi1/` 資料夾
- **已同步到 commit：a73e2c8（2026-06-17 16:29）**
- DB：MySQL `localhost:3306/harry`（root/root）。**無 hbm2ddl.auto → 表要手動建**

## 2. 固定工作流程（老師每次 git 後）
1. clone 老師 repo 到暫存，`git log` 找比「已同步 commit」新的。
2. `git diff <舊> <新>` 看改了哪些檔。
3. 搬進工作區，規則：
   - `package tw.brad → tw.harry`；tutor 類別 `BradNN → HarryNN`；DB `brad → harry`。
   - **保留用戶既有程式碼不覆蓋**（用戶常自己先建殼或抄一份；先 Read 再決定）。
   - 加繁中教學註解；需親自想通的點標 `===== 自己看過 =====`。
   - **不破梗**：練習/觀念留給用戶自己想，只給提示。
   - entity 沒有關聯註解前**不要登記 cfg**（會啟動報錯）。
4. 有 entity/欄位變更才給建表 SQL；沒有就明說不用動 DB。
5. 結尾附：模型推薦 + 是否壓縮 + 可複製提示詞。
6. 驗證：Python 腳本（老師內容 `brad→harry`、去註解去空行，再 `difflib` 比對）確認沒抄漏/抄錯。

## 3. 目前完成狀態（全部已 diff 驗證 = 對上老師）
### entity
- **Test**：單表入門。（用戶用 `private` 欄位，老師是 `public/protected` —— 用戶較好，刻意不同步）
- **Member ↔ MemberInfo**：`@OneToOne` + `@MapsId` 共用主鍵。MemberInfo=owner(`@JoinColumn`)、Member=inverse(`mappedBy`)。**表已建 + 15 筆假資料**。
- **Order ↔ OrderItem**：`@OneToMany(mappedBy="order", cascade=ALL, orphanRemoval=true)` ↔ `@ManyToOne(fetch=LAZY)` + `@JoinColumn("oid")`。表名 `orders`/`oitem`（避 SQL 保留字）。**已登記 cfg、表已建**。
### dao
- TestDao、MemberDao（含 `findByAccountLike` 具名參數模糊查詢）
- **OrderDao 介面 + OrderDaoImp**：program-to-interface；`Session` 由呼叫端傳入。5 法全實作（`findByIdWithItems` 用 `LEFT JOIN FETCH` 解 N+1、`findAll` 分頁）。
### service（新分層）
- **OrderService 介面 + OrderServiceImp**：8 法全實作。交易在 service 層開、dirty checking 自動 UPDATE、Stream API、`Optional.orElseThrow`、orphanRemoval。
### tutor
- Harry01-05：Test CRUD（用戶 playground，會自由改，diff 會有差屬正常）
- Harry06：新增會員 + BCrypt 雜湊
- Harry07：findById + 一對一導覽 + update。⚠️ **有「`println` 印在 null 檢查之前」的順序 bug**（老師原碼就有，未修，留給用戶）
- Harry08：`findByAccountLike` 清單 + N+1 觀察
- **Harry09-12**：實跑 OrderService（鏡像 Brad09-12，a73e2c8 新增）。09 一次交易建單+多明細(cascade) ／ 10 先建空單再分次 addItems(交易邊界) ／ 11 對既有單 1L 補明細 ／ 12 `getOrderWithItems` 讀回印 pname(JOIN FETCH 收尾)。**全 diff 驗證對上老師、用戶尚未實跑**。
### DB（harry，已建表）
- test、member、memberinfo（+15 筆）、orders、oitem
- 建表 SQL 在對話紀錄中（member/memberinfo 一組、orders/oitem 一組）

## 4. 待驗收觀念（用戶「講過但未正式驗收」，勿標已會）
`@MapsId` vs `@GeneratedValue`／`mappedBy` 與 owner-inverse／雙向 vs 單向／EAGER vs LAZY／N+1 與 JOIN FETCH／持久化上下文 + dirty checking／cascade vs orphanRemoval／`Optional`／Stream（期間 vs 收尾操作）／介面+實作分層的理由／Harry07 順序 bug 的修法。

## 5. 下一步預期
上一版預測的「tutor 把 OrderService 串起來實跑」已實現 → Harry09-12。接下來：
- 用戶端：實跑 09→12，驗收第 4 節的 cascade／交易邊界／orphanRemoval／JOIN FETCH（**跑過、講得出來才可標已會**）。注意 11、12 寫死 order id=1L，要先有資料。
- 老師端：可能進下一個 ORM 主題（如 `@ManyToMany`、JPQL/Criteria 查詢、或 Spring Data 整合）—— 此為預測，未發生。照第 2 節流程接手。
