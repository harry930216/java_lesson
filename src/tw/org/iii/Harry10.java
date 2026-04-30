package tw.org.iii;

public class Harry10 {

	// 九九乘法表

	public static void main(String[] args) {

//		for (int b = 1; b<= 9; b++) {
//			for (int a = 1; a <= 9; a++) {
//				int c = a * b;
//				System.out.printf("%d * %d = %d\t", a, b, c);
//			}
//			System.out.println();
//		}

		// 下一步 來玩我爽印幾行幾列

		final int ROWS = 1;
		final int COLS = 3;
		final int START = 1;

		for (int d = 0; d < ROWS; d++) {
			for (int b = 1; b <= 9; b++) {
				for (int a = START; a <= COLS; a++) {
					int newa = COLS * d + a;
					int c = newa * b;
					System.out.printf("%d * %d = %d\t", newa, b, c);
				}
				System.out.println();
			}
		}
	}

}
