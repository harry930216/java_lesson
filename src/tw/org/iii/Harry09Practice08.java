/*
題目 8：完全數（綜合題，類似質數的進階版）
難度：⭐⭐⭐⭐ 中偏難

需求：
找出 1~10000 之間所有的「完全數」並列出來。

什麼是完全數（Perfect Number）：
- 一個數，它的「真因數」（不含自己）總和等於它本身
- 例：
  - 6 = 1 + 2 + 3                  ← 完全數
  - 28 = 1 + 2 + 4 + 7 + 14         ← 完全數
  - 12 = 1 + 2 + 3 + 4 + 6 = 16     ← 不是（總和 16 ≠ 12）
- 1~10000 之間只有 4 個完全數，自己跑跑看是哪些

需求細節：
1. 寫 static 方法 sumOfFactors(int n) 回傳 n 的真因數總和
   - sumOfFactors(6) = 1+2+3 = 6
   - sumOfFactors(28) = 1+2+4+7+14 = 28
   - sumOfFactors(12) = 1+2+3+4+6 = 16
2. 寫 static 方法 isPerfect(int n) 回傳 boolean
3. main 裡找 1~10000 全部完全數，存進 ArrayList，最後印出

期待輸出範例：
1~10000 的完全數：
[6, 28, 496, 8128]
共 4 個

提示：
- 真因數：能整除 n 且小於 n 的正整數（包含 1，不含 n 本身）
- sumOfFactors 思路：for i = 1 到 n-1，如果 n % i == 0 就加進總和
- 跑 1~10000 會慢，但能跑就好（之後再學優化）
- 進階優化：因數成對出現（像 28 = 4×7），其實只要跑到 √n 就夠 → 但這題先別優化，純粹練綜合

陷阱：
- 1 不是完全數（因為它沒有真因數，sumOfFactors(1) = 0 ≠ 1）
- sumOfFactors 的 for 上限要寫 i < n（不含自己）

涉及觀念：兩個 static 方法協作、ArrayList 蒐集、% 整除判斷、雙層迴圈

寫完後想想：
- 你的程式跑 1~10000 大約幾秒？
- 如果改成 1~1000000，跑得完嗎？
- 怎麼用 √n 優化？（提示：因數對稱性）
*/
package tw.org.iii;

import java.util.ArrayList;

public class Harry09Practice08 {

	final int X = 10000;
	
	public static void main(String[] args) {
		
	}

	public static int sumOfFactors(int n) {
		ArrayList<Integer> list = new ArrayList<>();
		for (int i = 1; i < n; i++) {
			if (n % i == 0) {
				list.add(i);
			}
		}
		int sum = 0;
		for (int j : list) {
			sum += j;
		}
		return sum;
	}

	public static boolean isPerfect(int n) {
		return sumOfFactors(n) == n;
	}

}
