package tw.org.iii.practice.generics;

import java.util.*;

// 題 6 — 萬用字元與 PECS（wildcard / Producer Extends, Consumer Super）：泛型 copy
//        ★ 這是 todo 5/31 的練習之一
// 目標：寫一個泛型方法 copy，把一個 list 的元素全部搬進另一個 list，
//       並用萬用字元讓它「夠鬆」收下合理呼叫、「夠緊」擋掉不安全呼叫。
//
// 情境：
//   寫一個 static 方法 copy，兩個參數：一個目的 list（dest）、一個來源 list（src）。
//   行為：把 src 的所有元素，依序 add 進 dest。
//
//   下面三個呼叫，編譯結果必須「正好」是這樣 —— 這就是考點：
//     copy(List<Object>  dest, List<Integer> src)   → 要能編譯   (OK)
//     copy(List<Number>  dest, List<Integer> src)   → 要能編譯   (OK)
//     copy(List<Integer> dest, List<Number>  src)   → 要編譯不過 (FAIL)
//
//   你的方法簽章要「夠鬆」收下前兩個、又「夠緊」擋掉第三個，兩件事都做到才算對。
//   動手前先想：兩個參數，哪個是 producer（出貨）、哪個是 consumer（收貨）？
//   答案決定哪個參數用 ? extends、哪個用 ? super —— 這不給，你自己從零寫。
//
//   驗證 FAIL 那筆：方法寫好後，自己手動加一行
//   copy(List<Integer>, List<Number>) 的呼叫，確認 Eclipse 標紅，再刪掉。
//
// 完成後 main 跑起來應該是：
//   dest 一開始 [1.0, 2.0]，copy 進 [10, 20, 30] 後 → [1.0, 2.0, 10, 20, 30]
//
// 觀察重點（寫完回答，填到檔案最下面的觀察結果區）：
//   1. 兩個參數哪個是 producer、哪個是 consumer？你怎麼判斷的？
//   2. 為什麼第三個呼叫一定要編譯不過？如果讓它過，執行期會出什麼事？
//   3. src 那個參數，你能不能在 copy 裡面對它做 .add(...)？為什麼？
//   4. 如果兩個參數都只寫成 List<T>（不用萬用字元），會少接受上面哪幾個呼叫？

public class Ex06_WildcardCopy {

	public static void main(String[] args) {
		List<Integer> src = List.of(10, 20, 30);
		List<Number> dest = new ArrayList<>(List.of(1.0, 2.0));
		copy(dest, src);
		System.out.println(dest);   // 預期 [1.0, 2.0, 10, 20, 30]
	}

}

/* === 觀察結果（寫完填這裡）===

Q1

Q2

Q3

Q4

*/
