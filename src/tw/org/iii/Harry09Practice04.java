package tw.org.iii;


/* 
題目 給一個值 X
判斷 1-X 間 有幾個質數 並列出來
*/

public class Harry09Practice04 {

	public static void main(String[] args) {

		int randomNubmer = (int) (Math.random() * 100 + 1);
		boolean isFizz = (randomNubmer % 3 == 0);
		boolean isBuzz = (randomNubmer % 5 == 0);

		System.out.println(randomNubmer);

		if (isFizz == true) {
			if (isBuzz == true) {
				System.out.println("FizzBuzz");
			} else {
				System.out.println("Fizz");
			}
		} else if (isBuzz == true) {
			System.out.println("Buzz");
		} else {
			System.out.println(randomNubmer);
		}

	}

}
