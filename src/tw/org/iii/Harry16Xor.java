package tw.org.iii;

public class Harry16Xor {
	public static void main(String[] args) {
		int a = -10, b = 3;
		
		a = a ^ b;
		b = a ^ b;
		a = a ^ b;
		
		System.out.printf("a = %d, b = %d\n", a, b);
		System.out.println(3 ^ 2);
	}
}
