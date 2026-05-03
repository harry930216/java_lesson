/*
題目 6：判斷閏年（多重布林條件）
難度：⭐⭐ 簡單偏中

需求：
1. 寫一個 static 方法 isLeapYear(int year) 回傳 boolean
2. 閏年規則（重要）：
   - 能被 4 整除 → 可能是閏年
   - 但能被 100 整除 → 不是閏年（例外）
   - 但能被 400 整除 → 還是閏年（例外的例外）
   - 範例：
     - 2000 是閏年（被 400 整除）
     - 1900 不是閏年（被 100 整除，但沒被 400 整除）
     - 2024 是閏年（被 4 整除，沒被 100 整除）
     - 2025 不是閏年（不被 4 整除）
3. main 裡列出 2000~2030 之間所有閏年

期待輸出範例：
2000~2030 之間的閏年：
2000
2004
2008
2012
2016
2020
2024
2028
共 8 個

提示：
- 一行寫法：return year % 4 == 0 && (year % 100 != 0 || year % 400 == 0);
- 想清楚 && 跟 || 的優先順序，括號要對
- 可以拆成三個 if 寫得更直觀，但練一行寫法比較有挑戰

陷阱：
- 「能被 100 整除但不是 400 倍數 → 不是閏年」這個例外最常被忽略
- 試試輸入 1900 看你的答案對不對

涉及觀念：boolean 方法、%、&&、||、多重條件、優先順序
*/
package tw.org.iii;

public class Harry09Practice06 {

	public static void main(String[] args) {
		System.out.println("2000~2030 之間的閏年：");
		int k = 0;
		for (int i = 2000; i <= 2030 ; i++) {
			if (isLeapYear(i)) {
				System.out.println(i);
				k++;
			}
		}
		System.out.printf("共%d個",k);
	}
	 public static boolean isLeapYear(int year) { 
		return (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0));
	 }

}
