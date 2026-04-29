package tw.org.iii;

public class Harry05 {
	public static void main(String[] args) {
		// 成績出現亂數0-100 做判斷式
		double temp = Math.random();
		System.out.println(temp);
		int score = (int) (temp * 101);
		System.out.println(score);

		if (score >= 60) {
			System.out.println("Pass");
		} else {
			System.out.println("failed");
		}

		// 90 a 80 b 70 c 60 d then failed
		// 不可用if else
		double temp1 = Math.random();
		System.out.println(temp1);
		int score1 = (int) (temp1 * 101);
		System.out.println(score1);

		int score2 = (int) (score1 / 10);
		System.out.println(score2);
		switch (score2) {
		case 10:
		case 9:
			System.out.println("A");
			break;
		case 8:
			System.out.println("B");
			break;
		case 7:
			System.out.println("C");
			break;
		case 6:
			System.out.println("D");
			break;
		case 5:
		case 4:
		case 3:
		case 2:
		case 1:
		case 0:
			System.out.println("failed");
			break;

		default:
			break;
		}

		//if  else if 巢狀結構
		if (score >= 90) {
			System.out.println("A");
		}else if (score >= 80){
			System.out.println("B");
		}else if (score >= 70){
			System.out.println("C");
		}else if (score >= 60){
			System.out.println("D");
		}else {
			System.out.println("failed");
		}    
			
		
	}

}
