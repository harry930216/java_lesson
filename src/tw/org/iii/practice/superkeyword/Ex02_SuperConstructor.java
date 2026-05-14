package tw.org.iii.practice.superkeyword;

// 題 2 — super(...) 建構子鏈（單層）
// 目標：父類有「有參建構子」，子類用 super(...) 把該傳的參數傳上去，自己再加自己的欄位初始化。
// 情境：Person 父類有 name 欄位，建構子 Person(String name) 負責設定 name。
//       Student 繼承 Person，多一個 studentId 欄位。
//
// 【用戶思路】Student 收 2 參 → super 傳 name → studentID 自己處理
// 【補充重點】這就是「職責分離」：
//   - 父類管的欄位 → 用 super(...) 丟給父類建構子處理
//   - 自己多出來的欄位 → 用 this.x = x 自己處理
//   不要在子類重複寫 this.name = name，那等於繞過繼承、白寫父類建構子。

public class Ex02_SuperConstructor {

	public static void main(String[] args) {
		Student s = new Student("小明", "S001");
		s.show();
		// 預期輸出：name=小明, studentId=S001
	}
}

class Person {
	String name;

	Person(String name) {
		this.name = name;     // 父類專心管 name
	}
}

class Student extends Person {
	String studentId;

	Student(String name, String studentId) {
		super(name);                 // ← 必須是建構子第一行！name 交給父類處理
		this.studentId = studentId;  // 自己管 studentId
	}

	void show() {
		System.out.println("name=" + name + ", studentId=" + studentId);
	}
}

// 三條鐵律：
// 1. super(...) 必須是子類建構子的第一行（連空行都不能在前面，只能有註解）
// 2. super(...) 跟 this(...) 互斥（兩個都要佔第一行，物理上選一個）
// 3. 不寫 super(...)，編譯器自動補 super() 無參版 → 父類沒無參就炸（見 Ex03）
