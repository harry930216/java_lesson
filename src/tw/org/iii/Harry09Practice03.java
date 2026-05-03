package tw.org.iii;


/* 
題目 3：三角形判斷（方法 + 隨機）
需求：

寫一個 static 方法 isTriangle(int a, int b, int c)，回傳 boolean
規則：能組成三角形 ⇔ 任兩邊和 > 第三邊（三組都要成立）
在 main 裡：
隨機產生 a, b, c（範圍 1~20）
用迴圈跑 5 次
每次印 "a=?, b=?, c=? → 能/不能 組成三角形"
期待輸出範例：

a=3, b=4, c=5 → 能 組成三角形
a=1, b=2, c=10 → 不能 組成三角形
a=7, b=7, c=7 → 能 組成三角形
...
陷阱：

三邊和的條件要同時成立 → 用 && 串接
isTriangle 不要在 main 裡寫，單獨一個方法
印出時用三元印 "能"/"不能"
進階思考：a + b > c && a + c > b && b + c > a 三條，能不能用短路省一條？想想如果第一條 false，整體就 false 了，後面自動跳過——所以寫完三條沒效能損失。

涉及觀念：static 方法、boolean 回傳、&&、隨機、三元、for 
*/

public class Harry09Practice03 {

	public static void main(String[] args) {
		
		for ( int i = 0; i < 5; i++) {
			int a = (int)(Math.random()*20+1);
			int b = (int)(Math.random()*20+1);
			int c = (int)(Math.random()*20+1);
			System.out.printf("a=%d, b=%d, c=%d → %s 組成三角形",a,b,c,isTriangle(a,b,c)?"能":"不能");
			System.out.println();
		}
	}
	
	public static boolean isTriangle(int a, int b, int c) {
		return a + b > c && a + c > b && b + c > a;
	}
	
}
