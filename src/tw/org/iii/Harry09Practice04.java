package tw.org.iii;

/* 
題目 給一個值 X
判斷 1-X 間 有幾個質數 並列出來
*/

import java.util.ArrayList;

public class Harry09Practice04 {

	final static int X = 100;

	public static void main(String[] args) {

		ArrayList<Integer> list = new ArrayList<>();
		list.add(2);
		
		
		for (int i = 3; i <= X; i++) {
			
			int limit = (int)Math.sqrt(i) + 1;      
			boolean isPrime = true;
			
			for (int j : list) {
				if (j >= limit) { break;}
				if (i % j == 0) {
					isPrime = false;
					break;
				}
			}
			if (isPrime) list.add(i);
		}
		System.out.println("共 " + list.size() + " 個");
		System.out.println(list);

	}

//	static boolean isPrime(int n) {
//
//		for (int i = 2; i < n; i++) {
//			if (n % i == 0) {
//				return false;
//			}
//		}
//		return true;
//	}
}
