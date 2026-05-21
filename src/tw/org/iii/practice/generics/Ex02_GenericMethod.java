package tw.org.iii.practice.generics;

import java.util.*;

// 題 2 — 泛型 method（generic method）：reversed
// 目標：寫一個泛型「方法」（不是 class），型別參數加在方法上、由呼叫端自動推論。
//
// 情境：
//   寫一個 static 方法 reversed，吃一個 List，回傳一個「新的」List，
//   元素順序跟原本相反，原本那個 List 不要動到。
//   reversed 要能處理任何型別的 List：List<Integer>、List<String> 都通，
//   而且回傳的 List 型別要「跟著」進來的型別走（進 List<String> 就回 List<String>）。
//
//   方法的型別參數宣告在哪、回傳型別怎麼寫 —— 本題考點，不給，你自己從零寫。
//
// 完成後 main 跑起來應該是：
//   reversed([1, 2, 3])   → [3, 2, 1]
//   reversed([a, b, c])   → [c, b, a]
//
// 觀察重點（寫完回答，填到檔案最下面的觀察結果區）：
//   1. 方法的 <T> 宣告在哪個位置？（提示：跟回傳型別的相對位置）
//   2. 呼叫 reversed(...) 時你沒寫 T 是什麼，編譯器怎麼知道 T？這個機制叫什麼？
//   3. 如果方法不寫 <T>、參數和回傳都寫 raw 的 List，呼叫端會少掉什麼？
//   4. 回傳的那個新 List，你是用什麼型別 new 出來的？

public class Ex02_GenericMethod {
	
	public static void main(String[] args) {
		System.out.println(reversed(List.of(1, 2, 3)));         // 預期 [3, 2, 1]
		System.out.println(reversed(List.of("a", "b", "c")));   // 預期 [c, b, a]
		
	}
	
//	public static <T> List<T> reversed(List<T> l) {
//		List<T> newList = new ArrayList<>(); 
//		for (int i, i<) {
//			
//		}
//		return List<T>;
//	}

}



/* === 觀察結果（寫完填這裡）===

Q1

Q2

Q3

Q4

*/
