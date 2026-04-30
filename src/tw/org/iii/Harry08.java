package tw.org.iii;

public class Harry08 {

	// 閏年:
	// 1.每4年多一天
	// 2.每100年當年不算
	// 3.每400年補一天
	// 判斷平閏年
	
	
	public static void main(String[] args) {
		
		
		// 補一下隨機取年
		
		int randomYear = (int)(Math.random() * 10001);
		System.out.println(randomYear);
		
		// 我自己寫的
		
		int year = randomYear;
		boolean isTrue = false;
		
		if (year % 400 == 0) {
			isTrue = true;
		}else if(year % 100 == 0){
		}else if(year % 4 == 0) {
			isTrue = true;
		}
		System.out.printf("%d年是%s年", year, isTrue?"閏":"平");
		
		//老師版本
		
		System.out.println();
		boolean isLeap = false;
		
		if (year % 4 == 0) {
			if (year % 100 == 0) {
				if (year % 400 == 0) {
					isLeap = true;
				}else {
					isLeap = false;
				}
			}else {
				isLeap = true;
			}
		}else {
			isLeap = false;
		}
		//--------------------
		if (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0)) {
			isLeap = true;
		}else {
			isLeap = false;
		}
		
		System.out.printf("%d年是%s年", year, isLeap?"閏":"平");
		System.out.println();
		
		// 試試看三元
		
		boolean isR = ((year % 400 == 0) || (year % 4 == 0 && year % 100 != 0 ));
		System.out.printf("%d年是%s年", year, isR?"閏":"平");
	}
	
	
	
	
}
