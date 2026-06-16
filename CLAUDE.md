# java_lesson — Claude 交接 / 專案說明

> 給「下一個 Claude session」(尤其學校電腦)讀的交接檔。最後更新 2026-06-17。
> 一打開這個 repo,請先讀完本檔再動手。

## 我是誰
- EEIT22 產業新尖兵 Java 班學員,初學者,主攻 **Java 後端**(前端為輔)。
- 2026-06-05 起跟不上老師進度。弱點:**前後端傳資料 + JSON**。

## 這個 repo(java_lesson)= 老師課程 4 階段
| 資料夾 | 對應 | 狀態 |
|---|---|---|
| `src`(tw.org.iii) | 核心 Java / OOP | 跟得上 |
| `JDBC`(tw.harry) | JDBC 連資料庫 | 後追中 |
| `HarryWeb`(tw.harry) | Web:Servlet/JSP/MVC(Eclipse Dynamic Web,Tomcat 10.1) | 後追中(最弱) |
| `1`(tw.harry) | Hibernate(Maven) | 老師最新進度 |

- 老師教材參考:`git clone https://github.com/bradchao/eeit22_java`(BradJava / JDBC / BradWeb2 / hi1)。**老師檔名用編號 = 教學順序。**

## 現在在做什麼:最短路徑後追
練習檔已建好(空檔 + 目標註解,**用戶自己從零寫,不要代筆/給骨架**):
- `JDBC/src/tw/harry/learnByMyself/`:Step1Connect ✓、Step2PreparedStmt ✓ → **Step3Query(下一個,對照老師 Jdbc07)** → Step4JdbcTool → Step5MemberDao
- `HarryWeb/src/main/java/tw/harry/practice/`:ForwardRedirect / ScopeSession / JsonCheckAccount
- `HarryWeb/src/main/webapp/practice/`:jspBasics / ajaxJQuery / jsonReturn / jsonTwoWay / jsonRawBody / jstlTags / jsonNestedOrder(各 .html + .jsp)
- **JSON 由淺到深**:jsonReturn → jsonTwoWay → jsonRawBody → JsonCheckAccount → jsonNestedOrder。

## 環境(已修好;壞了檢查這裡)
- JDBC driver:`JDBC/lib/mysql-connector-j-9.7.0.jar`,`.classpath` 用**相對路徑** `lib/...`。
- HarryWeb JSON:`WEB-INF/lib/json-20251224.jar`(**真 jar**;javadoc 版無 class、不能用,已停用為 `.unused`)。
- 待用戶自己修:`HarryWeb` 的 `HarryUtils.loadView()` 還缺 `return`(編譯錯,非 jar 問題,只給提示)。

## 給 Claude 的工作規則(務必遵守)
1. **繁體中文**回應。
2. 練習題**只給觀念提示,不給答案 / 骨架 / skeleton / 填空**;後端用戶自寫,不要代筆。
3. 前端可代筆,但用戶要看得懂、能改、能 debug。
4. 練習檔用**描述式檔名**(Step3Query…),不跟老師編號。
5. **學習狀態升級要當場驗收**(答對驗收題 / 手寫通過 / 用戶明說會了)才算;否則維持「未驗收」,**不准標已會**。「計畫」「預測」也不准因時間經過就當成事實。
6. **保持客觀,禁止吹捧**:不評等、不說「比 X% 強」這類話。
7. 每個練習檔頂端有「目標型塊狀註解」,照著走。

## 學習狀態(誠實)
- JDBC + Web 練習**全部未驗收**(用戶尚未手寫驗收任何一關)。**勿標已會。**
- 下一步:打開 `Step3Query.java`,對照老師 `Jdbc07`,用戶自己寫。

## 完整版在哪
- 完整回追地圖(概念分級 + 弱點地圖 + 估時)在用戶家裡電腦的 `EEIT22\老師教學檔案順序_回追路線.md`(Google Drive,學校可能沒有)。本檔是精簡自足版。
