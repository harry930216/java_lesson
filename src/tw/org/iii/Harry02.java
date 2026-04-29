package tw.org.iii;

import java.util.Scanner;

public class Harry02 {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		System.out.print("x = ");
		int x = scanner.nextInt();
		System.out.print("y = ");
		int y = scanner.nextInt();
		System.out.println("x+y=" + (x + y));
		System.out.println("x-y=" + (x - y));
		System.out.println("x*y=" + (x * y));
		System.out.println("x/y=" + (x / y));
		
		System.out.print("a = ");
		int a = scanner.nextInt();
		System.out.print("b = ");
		int b = scanner.nextInt();
		int r1 = a + b;
		int r2 = a - b;
		int r3 = a * b;
		int r4 = a / b;
		int r5 = a % b;
		
		System.out.printf("%d + %d = %d \n", a, b, r1);
		System.out.printf("%d - %d = %d \n", a, b, r2);
		System.out.printf("%d * %d = %d \n", a, b, r3);
		System.out.printf("%d / %d = %d...%d \n", a, b, r4, r5);
		
	}
}
