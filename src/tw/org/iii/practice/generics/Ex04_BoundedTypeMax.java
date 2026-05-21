package tw.org.iii.practice.generics;

import java.util.*;

// 題 4 — 有界型別參數（bounded type parameter）：泛型 max
//        ★ 這是 todo 5/31 的練習之一
// 目標：寫一個泛型方法，吃一個 List，回傳裡面「最大」的元素，
//       並用「界」(bound) 限制呼叫端只能傳「元素能互相比較」的 List。
//
// 情境：
//   寫一個 static 方法 max，參數是一個 List，回傳 list 裡最大的那個元素。
//   - 「最大」用元素自己的 compareTo 決定 —— 不准用 > （> 不能用在物件上）。
//   - 限制：呼叫端只能傳「元素能互相比較」的 List。
//           傳一個元素不能比較的 List 進來 → 要「編譯期」就紅，不是執行期才爆。
//   - 空 List 怎麼處理由你決定（丟例外 / 回 null 都行），但要說得出理由。
//
//   方法的型別參數怎麼宣告、要不要加界、界要寫成什麼 —— 本題考點，
//   不給，你自己從零寫。（提示：你 Ex03 自己寫的 Comparable，就是這題要的界。）
//
// 完成後 main 跑起來應該是：
//   max(List.of(3, 9, 1, 7))                   → 9
//   max(List.of("banana","apple","cherry"))    → cherry
//
// 觀察重點（寫完回答，填到檔案最下面的觀察結果區）：
//   1. 型別參數只寫 <T> 不夠，為什麼？不加界時 compareTo 那行會發生什麼事？
//   2. 加界之後，界裡的 <T> 跟方法自己的 <T> 是不是同一個？為什麼要這樣綁？
//   3. 比大小為什麼一定要用 compareTo，不能用 > ？
//   4. 空 List 你選擇怎麼處理？理由是什麼？

public class Ex04_BoundedTypeMax {

	public static void main(String[] args) {
		System.out.println(max(List.of(3, 9, 1, 7)));                  // 預期 9
		System.out.println(max(List.of("banana", "apple", "cherry"))); // 預期 cherry
	}

}

/* === 觀察結果（寫完填這裡）===

Q1

Q2

Q3

Q4

*/
