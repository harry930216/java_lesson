package tw.org.iii;

public class Harry13 {

	public static void main(String[] args) {
		
		int[][] a = new int [3][4];
		
		int[][] b = new int [3][];
		b[0] = new int [2];
		b[1] = new int [3];
		b[2] = new int [4];
		
		System.out.println(b[1][1]);
		System.out.println(b.length);
		System.out.println(b[0].length);
		System.out.println(b[1].length);
		System.out.println(b[2].length);
	}

}
