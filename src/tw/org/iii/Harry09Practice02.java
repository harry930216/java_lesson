package tw.org.iii;


/* 
題目 2：3 的倍數總和
需求：

寫一個 for 迴圈跑 1~100
找出能被 3 整除、且不能被 5 整除的數字
把它們全部印出來，並累計總和
結束後印 "總和 = X"
期待輸出（前幾個）：

3
6
9
12
18
21
...
（15 跳過，因為能被 5 整除）

陷阱：

累加變數要在迴圈外宣告（迴圈內每次重新宣告會被歸零）
&& 跟 & 自己選一個（提示：判斷邏輯永遠用什麼？）
涉及觀念：for、&&、累加變數、變數作用域 
*/

public class Harry09Practice02 {

	public static void main(String[] args) {

		int sum = 0;		
		for (int i = 1; i <= 100; i++) {
			if( i % 3 == 0 && i % 5 != 0) {
				System.out.println(i);
				sum += i ;
			}
		}
		System.out.printf("總和 = %d", sum);

	}

}
