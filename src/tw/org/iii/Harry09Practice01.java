package tw.org.iii;


/* 題目 1：FizzBuzz 變體（暖身）
需求：

隨機產生一個 1~100 的整數
判斷它屬於哪一類，印出字串：
能被 15 整除 → "FizzBuzz"
能被 3 整除 → "Fizz"
能被 5 整除 → "Buzz"
都不能 → 印該數字本身
輸入範例：(隨機，假設拿到 30) → 輸出 30: FizzBuzz
輸入範例：(假設拿到 9) → 輸出 9: Fizz
輸入範例：(假設拿到 25) → 輸出 25: Buzz
輸入範例：(假設拿到 7) → 輸出 7: 7

陷阱：判斷順序不對 → 30 會被當成 Fizz（因為 30 也能被 3 整除）。先判斷最嚴格的條件。

涉及觀念：隨機、%、if/else if、字串輸出 */

public class Harry09Practice01 {

	public static void main(String[] args) {

		int randomNumber = (int) (Math.random() * 100 + 1);
		boolean isFizz = (randomNumber % 3 == 0);
		boolean isBuzz = (randomNumber % 5 == 0);
		
		if (isFizz) {
			if (isBuzz) {
				System.out.println(randomNumber + ": FizzBuzz");
			} else {
				System.out.println(randomNumber + ": Fizz");
			}
		} else if (isBuzz) {
			System.out.println(randomNumber + ": Buzz");
		} else {
			System.out.println(randomNumber + ": " + randomNumber);
		}
		
		// ------------------------------------
		
		if (isFizz && isBuzz) {
			System.out.println(randomNumber + ": FizzBuzz");
		}else if(isFizz) {
			System.out.println(randomNumber + ": Fizz");
		}else if(isBuzz) {
			System.out.println(randomNumber + ": Buzz");
		}else {
			System.out.println(randomNumber + ": " + randomNumber);
		}
		
		
	}

}
