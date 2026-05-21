package tw.org.iii.practice.generics;

import java.util.*;

// 題 5 — 不變性與協變（invariance / covariance）：printAll
// 目標：親手撞到「List<Integer> 不是 List<Number>」這道牆，再用上界萬用字元解開。
//
// 情境：
//   第一步：寫一個 static 方法 printAll，參數型別「先寫死」成 List<Number>，
//           把 list 裡每個元素印出來。
//   第二步：跑下面的 main —— 你會看到 printAll(ints) 那行編譯紅。
//           這就是「不變性」：List<Integer> 不是 List<Number> 的子型別。
//   第三步：改 printAll 的參數型別，讓它能收下 List<Integer>、List<Double>、
//           List<Number> 全部。但不准改成 raw List、也不准改成 List<Object>
//           —— 那是逃避不是解（觀察重點 Q4 會問你為什麼）。
//
//   參數最後該寫成什麼 —— 本題考點，不給。先想：printAll 只「讀」list 不「寫」，
//   它對那個 list 來說是 producer 還是 consumer？
//
// 完成後 main 跑起來應該是：
//   三個 list 的元素都印得出來，不再有編譯紅。
//
// 觀察重點（寫完回答，填到檔案最下面的觀察結果區）：
//   1. 第一版 List<Number> 為什麼收不下 List<Integer>？「不變性」是什麼意思？
//   2. 你最後參數寫成什麼？為什麼那樣就能收下子型別的 list？
//   3. 在 printAll 裡面，你能不能對那個 list 做 .add(...)？試試看，解釋結果。
//   4. 參數改成 raw List 也「能編譯」，為什麼那是壞解、不是好解？

public class Ex05_Invariance {

	public static void main(String[] args) {
		List<Integer> ints = List.of(1, 2, 3);
		List<Double> doubles = List.of(1.5, 2.5);
		List<Number> numbers = List.of(10, 20.0, 30);

		printAll(ints);
		printAll(doubles);
		printAll(numbers);
	}

}

/* === 觀察結果（寫完填這裡）===

Q1

Q2

Q3

Q4

*/
