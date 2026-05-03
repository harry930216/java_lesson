/*
題目 7：數字反轉（% 跟 / 的應用）
難度：⭐⭐⭐ 中

需求：
1. 寫一個 static 方法 reverse(int n) 回傳反轉後的整數
2. 範例：
   - reverse(12345) → 54321
   - reverse(100)   → 1（注意，前導 0 不見了）
   - reverse(7)     → 7
3. main 裡：
   - 印 reverse(12345)
   - 印 reverse(987654321)
   - 印 reverse(100)
   - 印 reverse(7)

期待輸出範例：
12345 反轉是 54321
987654321 反轉是 123456789
100 反轉是 1
7 反轉是 7

提示（重點）：
- 用 while 迴圈
- 每次取 n 的最後一位數：個位數 = n % 10
- 把這位數累加到結果：result = result * 10 + 個位數
- n 砍掉最後一位：n = n / 10
- 直到 n 變 0 為止

範例追蹤（n = 123）：
   start: n=123, result=0
   第1圈: 個位=3, result=0*10+3=3, n=12
   第2圈: 個位=2, result=3*10+2=32, n=1
   第3圈: 個位=1, result=32*10+1=321, n=0
   結束: 回傳 321

陷阱：
- while 條件用 n > 0 還是 n != 0？兩個都行（不會輸入負數的話）
- result 一開始要設 0，不是設 1

涉及觀念：while 迴圈、% 取餘、/ 整除、變數累積
*/
package tw.org.iii;

import java.util.Scanner;

public class Harry09Practice07 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		System.out.print("請輸入要反轉的數字：");
		int n = sc.nextInt();

		System.out.println(n + " 反轉是 " + reverse(n));

		sc.close();
	}

	public static int reverse(int n) {
		int reverseNumber = 0;
		while (n != 0) {
			int smallNumber = n % 10;
			reverseNumber = reverseNumber * 10 + smallNumber;
			n = n / 10;
		}
		return reverseNumber;
	}

}
