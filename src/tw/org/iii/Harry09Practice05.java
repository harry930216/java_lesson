
/*
題目 5：找最大值與最小值（ArrayList 鞏固）
難度：⭐⭐ 簡單

需求：
1. 用 ArrayList<Integer> 收集 10 個隨機整數（範圍 1~100）
2. 寫兩個 static 方法：
   - static int findMax(ArrayList<Integer> list) → 回傳 list 中最大值
   - static int findMin(ArrayList<Integer> list) → 回傳 list 中最小值
3. main 裡印出：
   - 整個 list
   - 最大值
   - 最小值

期待輸出範例：
[42, 17, 88, 3, 65, 91, 28, 50, 7, 33]
最大值：91
最小值：3

提示：
- 走訪 ArrayList 用增強 for（for (int x : list)）
- findMax 思路：先假設第 0 個是最大，跑過所有元素，看到比目前更大的就更新
- 不能用 Math.max() 偷吃步嗎？可以，但練「自己用迴圈找」是這題重點

涉及觀念：ArrayList、增強 for、變數追蹤、方法回傳
*/
package tw.org.iii;

import java.util.ArrayList;
//import java.util.Collections;

public class Harry09Practice05 {

	public static void main(String[] args) {
		ArrayList<Integer> list = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			int a = (int) (Math.random() * 100 + 1);
			list.add(a);
		}
		System.out.println(list);
		System.out.printf("最大值:%d",findMax(list));
		System.out.println();
		System.out.printf("最小值:%d",findMin(list));
//		另一種寫法		
//		System.out.printf("最大值:%d",Collections.max(list));
//		System.out.println();
//		System.out.printf("最小值:%d",Collections.min(list));
	}

	public static int findMax(ArrayList<Integer> list) {
		int max = list.get(0);
		for (int i : list) {
			if (max < i) {
				max = i;
			}
		}
		return max;
	}
//
	public static int findMin(ArrayList<Integer> list) {
		int min = list.get(0);
		for (int i : list) {
			if (min > i) {
				min = i;
			}
		}
		return min;
	}

}
