# Hibernate 跟課 交接檔（HANDOFF）

> 給「下一個 session / 下一個 Claude」看的：這工作區在做什麼、做到哪、規則、怎麼接手。
> 最後更新：2026-06-18。**hi1 已同步老師 commit `012ef83`；h2 新專案刻意跳過（見第 5 節）。**

## 0. 這是什麼
EEIT22 Java 課程 Hibernate 單元的跟課練習。**逐個 commit 鏡像老師的程式碼**到本工作區，Claude 補教學註解。

## 1. 工作區 & 來源
- 工作區：`C:\Users\User\eclipse-workspace\HarryPeng\1`（Eclipse 專案名 Hibernate，Maven，JDK 21）
- 依賴：hibernate-core 6.5.2 / mysql-connector 8.0.33 / jbcrypt 0.4
- 鏡像來源：`github.com/bradchao/eeit22_java` 的 `hi1/` 資料夾
- **已同步：hi1 → `012ef83`（2026-06-18 11:03）。h2/（`3bcfbef` 起的 3 個 commit）尚未鏡像。**
- DB：MySQL `localhost:3306/harry`（root/root）。**無 hbm2ddl.auto → 表要手動建**

## 2. 固定工作流程（老師每次 git 後）
1. clone 老師 repo 到暫存，`git log` 找比「已同步 commit」新的。
2. `git diff <舊> <新>` 看改了哪些檔。
3. 搬進工作區，規則：
   - `package tw.brad → tw.harry`；tutor 類別 `BradNN → HarryNN`；DB/樣本名 `brad → harry`。
   - **保留用戶既有程式碼不覆蓋**（用戶常自己先建殼或半成品；先 Read 再決定）。
   - 加繁中教學註解；需親自想通的點標 `===== 自己看過 =====`。
   - **不破梗**：練習/觀念留給用戶自己想，只給提示。
   - entity 沒關聯註解前不要登記 cfg（會啟動報錯）。
4. 有 entity/欄位變更才給建表 SQL；沒有就明說不用動 DB。
5. 結尾附：模型推薦 + 是否壓縮 + 可複製提示詞。
6. 驗證：Python 腳本（老師內容 `brad→harry`、去註解去空行、再 `difflib` 比對）確認沒抄漏/抄錯。

## 3. 目前完成狀態（hi1，已 diff 驗證）
### entity
- Test：單表入門。（用戶用 `private`，老師 `public/protected` — 刻意不同步）
- Member ↔ MemberInfo：`@OneToOne` + `@MapsId` 共用主鍵。表已建 +15 筆假資料。
- Order ↔ OrderItem：`@OneToMany`(mappedBy/cascade ALL/orphanRemoval) ↔ `@ManyToOne` + `@JoinColumn("oid")`。**OrderItem.order 的 fetch 老師最後改回 EAGER**（LAZY 那行留註解）。表 `orders`/`oitem` 已建。
- **Student ↔ Course：`@ManyToMany`（新）**。Student=owner（`@JoinTable name="sc"`，joinColumns sid／inverseJoinColumns cid，cascade ALL，EAGER，有 addCourse 雙向方法）；Course=inverse（`mappedBy="courses"`，EAGER）。用 `Set` 不用 List。**表 student/course/sc 需建（SQL 見對話）**。
### dao
- TestDao、MemberDao（findByAccountLike）
- OrderDao 介面 + OrderDaoImp（5 法 + 老師加的 test1/2/3 HQL 練習，非介面方法）。Session 由呼叫端傳入。
- **SCDao（新）**：Student/Course 存取。回到「DAO 自己開關 session」的簡單寫法；save 多載(Student/Course)。
### service
- OrderService + OrderServiceImp（8 法全實作 + 老師加的 getOrderById/test1 練習方法）。交易在 service 層、dirty checking、Stream、orphanRemoval。
### tutor
- Harry01-05：Test CRUD（playground）
- Harry06：BCrypt 新增；Harry07：一對一導覽+update（**有 println 順序 bug 未修**）；Harry08：模糊查詢+N+1
- Harry09-12：實跑 OrderService（建單/分次 addItems/補明細/讀回，11、12 寫死 id=1L）
- **Harry13-16（新）**：13 印訂單明細(service.test1)／14 建 4 學生／15 建 5 課程／`harry16`(小寫，鏡像老師 typo) 互動選課跑多對多
### DB（harry）
- 已建：test、member、memberinfo(+15)、orders、oitem
- **待建：student、course、sc**（多對多，SQL 在對話）
### 已知無害差異（刻意不改）
Test private／TestDao 少一行 println／cfg `show_sql=true`(老師 false)／Student 建構子多 `;`／Harry15 少一個沒用到的 import。

## 4. 待驗收觀念（用戶「講過但未正式驗收」，勿標已會）
`@MapsId`／mappedBy 與 owner-inverse／雙向 vs 單向／EAGER vs LAZY（OrderItem 還被改來改去）／N+1 與 JOIN FETCH／dirty checking／cascade vs orphanRemoval／`Optional`／Stream（期間 vs 收尾、anyMatch）／介面+實作分層／**@ManyToMany 為何要 join table、owner 定 @JoinTable／為何用 Set／方法多載**／Harry07 順序 bug 修法。

## 5. 下一步預期
- **h2/ 新專案（跳過中）**：老師另開 Eclipse 專案 `h2/`（用 **Lombok**，含 Customer、Brad01-02）。用戶決定「自己在 Eclipse 建 HarryPeng/2 再叫 Claude 鏡像」。⚠️ Lombok 需在 Eclipse 裝外掛/agent，Claude 無法代勞。等用戶建好 HarryPeng/2 再鏡像 h2 內容。
- 用戶端：實跑 Harry13-16（先跑 14、15 建資料，再 16 選課），驗收多對多 + 第 4 節觀念（**跑過、講得出來才可標已會**）。
- 老師端：可能繼續 h2（Lombok/JPA 進階）或新主題 —— 預測，未發生。照第 2 節流程接手。
