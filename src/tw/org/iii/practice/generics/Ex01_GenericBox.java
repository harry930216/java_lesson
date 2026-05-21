package tw.org.iii.practice.generics;

import java.util.*;

// 題 1 — 泛型 class（generic class）：Box<T>
// 目標：寫一個能裝「任意型別」一個值的容器 class，型別參數加在 class 上。
//
// 情境：
//   寫一個泛型 class Box，能裝一個型別為 T 的值，提供：
//     set(...)  放一個值進去
//     get()     把值拿出來
//   Box 要能裝任何型別 —— Box<String> 裝字串、Box<Integer> 裝整數，
//   而且拿出來不用 cast（強制轉型）。
//
//   class 的型別參數寫在哪、怎麼宣告 —— 本題考點，不給，你自己從零寫。
//   Box class 寫在這個檔案裡（public class 下面，跟你 interface 練習擺法一樣）。
//
// 完成後 main 跑起來應該是：
//   b1.get() → hello
//   b2.get() → 42
//
// 觀察重點（寫完回答，填到檔案最下面的觀察結果區）：
//   1. Box 的型別參數 T 宣告在哪個位置？跟方法參數的 ( ) 差在哪？
//   2. new Box<>() 那個 <>（diamond operator，菱形運算子）省掉了什麼？不省要寫成什麼？
//   3. get() 回傳值為什麼不用 cast，舊式的 Object 容器卻要？
//   4. 如果要一次裝「兩個不同型別」（像 key 配 value），Box 的宣告要怎麼改？

public class Ex01_GenericBox {

	public static void main(String[] args) {
		Box<String> b1 = new Box<>();
		b1.set("hello");
		System.out.println(b1.get());   // 預期 hello

		Box<Integer> b2 = new Box<>();
		b2.set(42);
		System.out.println(b2.get());   // 預期 42
	}
	
}

class Box<T>{
	T content;
	
	void set(T t) {
		content = t;
	}
	
	T get() {
		return content; 
	}
	
}
/* === 觀察結果 ===

Q1 T 宣告在哪個位置？跟方法參數的 () 差在哪？
	你的答案：<T>

	[Claude 解答]
	- 位置：class Box<T> —— 在 class 名稱「後面」、用角括號 <> 框住。
	  （泛型「方法」則是放在回傳型別前面，見 Ex02）
	- <> 跟 () 的差別：<> 裡裝「型別」、() 裡裝「值」。
	  Box<String> 給的是型別 String；set("hello") 給的是值 "hello"。
	→ 一句話：<> 傳型別、() 傳值，是兩條不同的軌道。

Q2 new Box<>() 那個 <> 省掉了什麼？不省要寫成什麼？
	你的答案：<String>

	[Claude 補充] 方向對
	- 不省略要寫成 new Box<String>()。
	- <>（diamond operator 菱形運算子，Java 7+）讓你省掉重複的 <String> ——
	  因為左邊 Box<String> b1 已經宣告過型別，編譯器自己從左邊推回來。
	→ 一句話：<> = 「型別跟左邊一樣，編譯器你自己補」。

Q3 get() 回傳值為什麼不用 cast，舊式 Object 容器卻要？
	你的答案：「因為回傳的是 T」（抓到一半）

	[Claude 解答]（用你 polymorphism 練過的 downcast 來理解）
	- 規則：範圍大的型別塞進範圍小的型別變數，要 cast。
	  就是 downcast，例如 Dog d = (Dog) animal。
	- 舊的 Object 容器，get() 簽章是「Object get()」——回傳 Object（大）。
	  String s = old.get(); 是大→小，所以要寫 (String) cast。
	- 你的 Box<T>，get() 簽章是「T get()」，而 Box<String> 讓 T = String，
	  所以實際上是「String get()」——回傳 String。
	  String s = b1.get(); 同型別，不用 cast。
	→ 一句話：差別在 get() 的回傳型別 —— 舊的回 Object（逼你 cast）、
	  你的回 String（免了）。這就是泛型替你省掉 cast。

Q4 要一次裝兩個不同型別，Box 的宣告要怎麼改？
	你的答案：<T1, T2>

	[Claude 補充] 答對
	- class Box<T1, T2> 完全正確 —— <> 裡的型別參數要幾個都行，逗號隔開。
	- 唯一更好：命名慣例用有意義的單字母。key-value 配對的標準寫法是
	  <K, V>（K = Key、V = Value），JDK 的 Map<K, V> 就是這樣。
	  T1 / T2 能跑，只是不道地。
	→ 一句話：多個型別參數 = 逗號隔開，慣例寫 <K, V>。

*/
