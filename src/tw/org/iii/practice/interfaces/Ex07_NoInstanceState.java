package tw.org.iii.practice.interfaces;

// 題 7 — 沒有 instance state 根定理（踩雷型）
// 目標：實際撞編譯錯,內化「interface 沒有 instance state」這條根定理衍生的所有規則。
//
// 情境：
//   下面 Conflict07 介面有 3 個故意踩雷的寫法,目前全部註解掉。
//   一個一個取消註解,觀察編譯訊息,記下來再重新註解掉。
//
//   雷 1：宣告一個沒給初始值的欄位
//   雷 2：宣告一個建構子
//   雷 3：在 default 方法裡修改介面欄位的值
//
// 然後寫一個正確版的 Config07 介面：只放常量 + 一個方法簽章。
// MyApp07 實作 Config07,把方法寫出來。
//
// 在 main:
//   - 印出 Config07.MAX_USERS（從介面名直接讀常量）
//   - 嘗試寫 Config07.MAX_USERS = 999;（觀察編譯訊息）
//   - 嘗試寫 Config07 c = new Config07();（觀察編譯訊息）
//   - new MyApp07() 然後呼叫方法
//
// 觀察重點（寫完回答）：
//   1. 雷 1 的編譯訊息是什麼？為什麼介面欄位必須給初始值？
//   2. 雷 2 的編譯訊息是什麼？為什麼介面不能有建構子？
//   3. 雷 3 的編譯訊息是什麼？這跟「介面欄位隱式 final」有什麼關係？
//   4. 為什麼 Config07.MAX_USERS = 999 編譯炸？
//   5. 為什麼 new Config07() 編譯炸？
//   6. 用一句話總結「沒有 instance state」這 5 個字怎麼推出上面所有規則。

public class Ex07_NoInstanceState {

	public static void main(String[] args) {

	}
}

interface Conflict07 {

	// === 雷 1 === 取消註解觀察錯誤,觀察完再註解回去
	// int noInitValue;

	// === 雷 2 === 取消註解觀察錯誤,觀察完再註解回去
	// Conflict07() { }

	// === 雷 3 === 取消註解觀察錯誤,觀察完再註解回去
	// int counter = 0;
	// default void increment() { counter = counter + 1; }
}

interface Config07 {

}

class MyApp07 implements Config07 {

}

/* === 觀察結果（寫完填這裡）===
雷 1 編譯訊息：
雷 2 編譯訊息：
雷 3 編譯訊息：

Config07.MAX_USERS 印出 =
Config07.MAX_USERS = 999 編譯訊息：
new Config07() 編譯訊息：

Q1 為什麼介面欄位必須給初始值：

Q2 為什麼介面不能有建構子：

Q3 雷 3 的編譯訊息跟隱式 final 的關係：

Q4 為什麼 Config07.MAX_USERS = 999 編譯炸：

Q5 為什麼 new Config07() 編譯炸：

Q6 「沒有 instance state」怎麼推出所有規則：

*/
