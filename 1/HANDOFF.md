# Hibernate 跟課 交接檔（HANDOFF）

> 給「下一個 session / 下一個 Claude」看的：這工作區在做什麼、做到哪、規則、怎麼接手。
> 最後更新：2026-06-22。**hi1 同步至 `012ef83`；h2 鏡像至最新 h2 commit `2a13598`（Lombok→查詢/HQL→關聯+複合主鍵→Jackson JSON）。老師當日傍晚另開 Spring Boot 專案 Sprint1（repo HEAD `7ee92e4`，未鏡像，見第 7 節）。**

## 0. 這是什麼
EEIT22 Java 課程 Hibernate 單元的跟課練習。**逐個 commit 鏡像老師的程式碼**到本工作區，Claude 補教學註解。

## 1. 工作區 & 來源
- 工作區：`C:\Users\User\eclipse-workspace\HarryPeng\1`（Eclipse 專案名 Hibernate，Maven，JDK 21）
- 依賴：hibernate-core 6.5.2 / mysql-connector 8.0.33 / jbcrypt 0.4
- 鏡像來源：`github.com/bradchao/eeit22_java` 的 `hi1/` 資料夾
- **已同步：hi1 → `012ef83`；h2 → `2a13598`（HarryPeng/2，已搬入 HarryPeng git）。repo HEAD `7ee92e4` = 新 Sprint1 Spring Boot 專案（未鏡像，見第 7 節）。**
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
- **老師端**：h2 已到 `baec0fc`；再 git 就找 `baec0fc` 之後的 commit，照第 2 節流程接手（hi1 或 h2 都可能）。
- **用戶端 hi1**：實跑 Harry13-16（先 14、15 建資料，再 16 選課），驗收多對多 + 第 4 節觀念（**跑過、講得出來才可標已會**）。
- **用戶端 h2**：見第 6 節待辦。

## 6. h2 專案（2026-06-22 新增，主題 = Lombok + 查詢四招 + 關聯/複合主鍵）
- **位置**：`C:\Users\User\eclipse-workspace\HarryPeng\2`（獨立 Maven 專案 groupId/artifactId=2，已搬進 HarryPeng git）。pom 4 依賴：hibernate-core 6.5.2 / mysql-connector 8.0.33 / jbcrypt 0.4 / **lombok 1.18.42**。
- **DB（注意！不是 harry）**：連 `jdbc:mysql://localhost:3306/north`（Northwind 範例庫），用 `customers`(CustomerID 字串 PK, CompanyName)、`employees`(EmployeeID int PK, LastName, FirstName, Title)。建表+樣本 SQL 已給對話（沒資料 Harry02/03/04/05 跑不出東西）。
- **已鏡像（diff 驗證一致，至 `2a13598`）**：entity `Customer`(@Data)、`Employee`(拆開 Lombok)、`Order`(@ManyToOne+@OneToMany)、`OrderDetail`(**@IdClass 複合主鍵**)、`OrderDetailPK`、`Product`；dto `OrderItem`/`DetailItem`(public 欄位純資料載體)；tutor `Harry01`-`Harry08`（01 Lombok／02 PK查／03 native→entity／04 native→Object[]／05 Criteria／06 HQL／07 HQL投影+Jackson物件↔JSON／08 純 Jackson）；`HibernateUtil`、`hibernate.cfg.xml`(登記 5 entity)。pom 加 **jackson-databind 3.0.3**(套件名 `tools.jackson`)。
- **無害差異**：Harry02 保留 `import java.beans.Customizer;`（老師 IDE 誤匯入，自己看過標「可刪」）；Customer 保留沒用到的 lombok.Getter/Setter import。
- **⚠️ 用戶必做（Claude 無法代勞）**：① Lombok agent 裝進 Eclipse（`java -jar lombok.jar` → 指向 eclipse.exe → 重開），否則 @Data/@Getter 在 IDE 全紅。② 建 north DB + 灌資料。③ 跑 Harry01-05 驗收。
- **h2 待驗收觀念（勿標已會）**：Lombok（@Data 一次包 vs 拆開註解、@NoArgs 為何 JPA 必需、**@AllArgs 參數順序＝欄位宣告順序，要對應 Harry05 multiselect 取欄位順序**）、查詢四招對照（Harry03 native→entity／Harry04 native→Object[]／Harry05 Criteria 型別安全／Harry06 HQL 用 Java 名）、entity 關聯（@ManyToOne／@OneToMany mappedBy）、**複合主鍵**（@IdClass + PK 類別 3 規定：implements Serializable／欄位名型別對應 @Id／自寫 equals+hashCode）、HQL 投影查詢（SELECT 挑欄位→Object[]、用物件導覽 JOIN）、**Jackson**（ObjectMapper 物件↔JSON、writeValueAsString/readValue、DTO 對映、TypeReference 保泛型）。

## 7. Sprint1（Spring Boot，2026-06-22 傍晚老師新開，**尚未鏡像**）
- 老師從純 Hibernate 轉進 **Spring Boot**（repo `Sprint1/`，commit `7ee92e4`）。**這是 UcMarket 團隊專題的底層框架**，重點轉折。
- 內容（目前很小）：`Sprint1Application`(@SpringBootApplication + SpringApplication.run)、`application.properties`(DataSource 連 DB `brad`)、`static/brad01.html`(靜態頁，Boot 自動以 `/` 路徑服務)。pom 用 spring-boot-starter-parent **4.1.0** + starter-data-jdbc + starter-webmvc + mysql-connector-j + lombok；**Java 17**。
- **為何沒鏡像**：Spring Boot 專案要用 Spring Starter（start.spring.io 或 Eclipse「Spring Starter Project」）產生（含 mvnw、parent、starter 依賴），跟 h2 一樣得用戶先建殼。
- **鏡像時 brad→harry**：套件 `com.example.spring1` 用戶可自訂；DB `brad`→`harry`（application.properties）；`"Hello, Brad"`→Harry；`brad01.html` 內 "Brad Big Company"→Harry。
- **待用戶**：用 Spring Starter 建 Spring Boot 專案（Java 17、依賴 Spring Data JDBC + Spring Web + MySQL Driver + Lombok）→ 叫我鏡像。
- **（更正先前誤判）**：`OrderDetail` 的 OrderID/ProductID 同時當 `@Id` 又當 `@JoinColumn` FK（PK = 兩個 FK 的組合）—— 這是連接表帶複合主鍵的正常寫法，**老師實際跑成功（有 order.json 輸出）**，Hibernate 6 吃這種「主鍵兼外鍵」。先前擔心的「Column duplicated」啟動錯誤並未發生，已收回。
