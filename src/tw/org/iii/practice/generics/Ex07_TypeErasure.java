package tw.org.iii.practice.generics;

import java.util.*;

// 題 7 — 型別擦除（type erasure）：執行期泛型會消失
// 目標：親手踩擦除的雷，理解「泛型只是編譯期的事，執行期 JVM 看不到 <T>」。
//
// 情境：
//   這題不是寫一個方法，是「驗證 + 解釋」。照下面做，把觀察寫進觀察結果區：
//
//   (A) main 已有一行：印出兩個 ArrayList 的 getClass() 比較結果。先預測，再看。
//
//   (B) 在這個檔案裡寫一個小小的泛型 class（例如 class Lab<T>），
//       在裡面試著寫這三行，看 Eclipse 各給什麼錯誤訊息
//       （看完就好、不用真的留著）：
//          T value = new T();
//          T[] arr = new T[10];
//          Class<?> c = T.class;
//
//   (C) main 裡把 obj instanceof List<String> 那行的註解解除，看編譯紅；
//       對照沒有 <String> 的那行為什麼可以。
//
// 觀察重點（寫完回答，填到檔案最下面的觀察結果區）：
//   1. (A) 兩個 ArrayList 的 getClass() 為什麼相等？擦除把 <String>/<Integer> 變成什麼？
//   2. (B) new T() / new T[10] / T.class 為什麼都不行？執行期 JVM 缺了什麼資訊？
//   3. (C) instanceof List<String> 不行、instanceof List 可以，差在哪？
//   4. 既然擦除帶來這麼多限制，Java 當初為什麼還要這樣設計？（提示：回溯相容）

public class Ex07_TypeErasure {

	public static void main(String[] args) {
		ArrayList<String> a = new ArrayList<>();
		ArrayList<Integer> b = new ArrayList<>();
		System.out.println(a.getClass() == b.getClass());   // (A) 先預測 true 還 false

		Object obj = new ArrayList<String>();
		System.out.println(obj instanceof List);            // 這行可以，會印什麼？
		// (C) 解除下面這行的註解 → 會編譯紅，看完錯誤訊息再註解回去：
		// System.out.println(obj instanceof List<String>);
	}

}

/* === 觀察結果（寫完填這裡）===

Q1

Q2

Q3

Q4

*/
