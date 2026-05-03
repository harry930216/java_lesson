package tw.org.iii;

public class Harry10Practice {

	// 九九乘法表
	
	
	

	public static void main(String[] args) {
		
		final int COWS = 10;
		final int COLS = 10;
		final int START = 1;
		
		for (int k = 0; k < COWS; k++) {
			for (int j = 1; j <= 9; j++) {
				for (int i = START; i <= COLS; i++) {
					int newi = i + COLS * k;
					int r = newi * j;
					System.out.printf("%d * %d = %d\t", newi, j, r);
				}
				System.out.println();
			}
			System.out.println();
		}
		
	}

}
