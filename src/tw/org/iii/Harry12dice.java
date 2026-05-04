package tw.org.iii;

public class Harry12dice {
	public static void main(String[] args) {
		int[] p = new int [7];
		
		for (int i = 0; i < 1000000; i++) {
			int point = (int)(Math.random() * 6) + 1;
			
			if (point >= 1 && point <= 6) p[point]++;
		}
		for (int j = 1 ; j <= 6; j++) {
				System.out.printf("骰出%d點:共%d次",j,p[j]);
				System.out.println();
		}
	}
}
