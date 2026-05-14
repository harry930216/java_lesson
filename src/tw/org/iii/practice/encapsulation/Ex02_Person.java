package tw.org.iii.practice.encapsulation;

// 題 2 — setter 加驗證
// 目標：setter 裡加防護，擋掉不合理的值。
// 情境：Person 有年齡欄位，年齡不能是負數或超過 150。

public class Ex02_Person {

    public static void main(String[] args) {
    	new Person(200);
    }
}

class Person {
	private int age;

	Person(){
		this(0);
	}

	Person(int age){
		// age < 150 會擋掉 150，題目說不超過 150 應改為 age <= 150
		// 實務上另外寫 setAge(int age) 方法，讓物件建好後還能修改年齡
		if (age >= 0 && age <= 150) {
			this.age = age;
		}else {
			System.out.println("輸入錯誤");
		}
	}
}
