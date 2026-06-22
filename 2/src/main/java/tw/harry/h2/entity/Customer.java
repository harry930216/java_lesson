package tw.harry.h2.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;   // 自己看過：@Data 已含 getter/setter，這兩個 import（Getter/Setter）其實沒用到，老師留著的
import lombok.Setter;

// Customer：對映 north 資料庫的 customers 表（Northwind 範例庫）
// JPA (Jakarta Persistence API) = Java 操作資料庫的標準「註解規格」；Hibernate 是它的其中一種實作
@Entity                         // 宣告這是 entity（一個類別 ↔ 一張表）
@Table(name = "customers")      // 對映表名 customers（類別叫 Customer、表叫 customers，名字不同就要指定）
@Data                           // ===== 自己看過（Lombok 的主角）=====
                                // @Data 一次幫你產生：getter + setter + toString + equals + hashCode +（必要欄位）建構子
                                // → 下面完全不用手寫 getId/setId/getCname...，Lombok 在編譯時自動補進這個 class
                                // ⚠️ 要在 Eclipse 內看到/用到這些方法，必須先把 Lombok agent 裝進 Eclipse；pom 那個依賴只負責命令列 mvn 編譯
public class Customer {
	@Id                          // 主鍵 (Primary Key, PK)
	@Column(name = "CustomerID") // 對映欄位 CustomerID；Northwind 的客戶代碼是「字串」（例如 "BLONP"）
	private String id;           // 注意：主鍵型別是 String，不是數字

	@Column(name = "CompanyName")
	private String cname;        // 公司名稱


}
