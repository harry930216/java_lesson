package tw.org.iii;

public class Harry09 {
	public static void main(String[] args) {
		// && & || |差異
		// 以及看範例了解
		int a = 10, b = 3;

		if (a++ <= 10 & --b >= 3) {
			System.out.printf("OK: a = %d, b = %d", a, b);
		} else {
			System.out.printf("XX: a = %d, b = %d", a, b);
		}
	}
}
