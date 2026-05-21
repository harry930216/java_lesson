package tw.org.iii.practice.generics;

import java.util.*;

// 題 3 — 泛型 interface（generic interface）：自己實作 Comparable<T>
// 目標：寫一個 class 去 implements JDK 的泛型介面 Comparable，
//       體驗「使用端把型別參數填成具體型別」。
//
// 情境：
//   寫一個 class Temperature，代表一個溫度，需要有：
//     - 一個 double 欄位，存攝氏溫度
//     - 一個建構式吃一個 double
//     - 一個 getCelsius() 回傳那個 double
//     - implements Comparable，並實作 compareTo（攝氏低的算「小」、高的算「大」）
//
//   implements Comparable 後面的型別參數要填什麼、compareTo 的參數型別
//   會跟著變成什麼 —— 本題考點，不給，你自己從零寫。
//   Temperature class 寫在這個檔案 public class 下面。
//
// 完成後 main 跑起來應該是：
//   Collections.sort 用你的 compareTo 把 list 由低溫排到高溫：
//   -3.0 / 10.0 / 21.5 / 37.0
//
// 觀察重點（寫完回答，填到檔案最下面的觀察結果區）：
//   1. implements Comparable 後面的 < > 你填了什麼？填「它自己」是什麼意思？
//   2. 如果填 Comparable<Object> 或寫成 raw Comparable，compareTo 參數會變怎樣？哪裡變難用？
//   3. compareTo 回傳負數 / 0 / 正數，分別代表這個物件比參數「如何」？
//   4. Collections.sort 沒有人告訴它「怎麼比溫度」，它從哪知道排序規則？

public class Ex03_GenericInterface {

	public static void main(String[] args) {
		List<Temperature> list = new ArrayList<>(List.of(
				new Temperature(21.5),
				new Temperature(-3.0),
				new Temperature(37.0),
				new Temperature(10.0)));

		Collections.sort(list);

		for (Temperature t : list) {
			System.out.println(t.getCelsius());
		}
	}

}

/* === 觀察結果（寫完填這裡）===

Q1

Q2

Q3

Q4

*/
