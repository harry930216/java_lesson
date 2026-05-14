package tw.org.iii.practice.superkeyword;

// 題 4 — super.field vs this.field vs field 三方對決
// 目標：親手證明「欄位是靠宣告型別找的」,並深化 super.x 永遠指父類欄位。
//
// 情境：
//   Parent04 有 String name = "父"
//   Child04 extends Parent04,也宣告 String name = "子"   ← 遮蔽（shadow）父類的 name
//
//   Child04 內部寫一個方法 show()：
//     依序印出三種寫法：
//       System.out.println(name);
//       System.out.println(this.name);
//       System.out.println(super.name);
//
//   在 main：
//     - new Child04().show();
//     - 進階對照：用父類視角看
//         Parent04 p = new Child04();
//         System.out.println(p.name);
//
// 觀察重點（寫完回答）：
//   1. name / this.name / super.name 三個分別印什麼？
//   2. 為什麼 name 跟 this.name 印一樣？「省略 this 是不是等於不寫 this」？
//   3. p.name 印什麼？跟 super.name 一樣嗎？為什麼？
//   4. 連結 polymorphism/Ex05_FieldVsMethod：「欄位看宣告型別」這條規則,在這題哪幾行可以驗證？
//   5. 如果有 GrandChild04 extends Child04 並再宣告 name = "孫",那 Child04 裡的 super.name 會變嗎？為什麼？

public class Ex04_SuperField {

	public static void main(String[] args) {

	}
}

class Parent04 {

}

class Child04 extends Parent04 {

}

/* === 觀察結果（寫完填這裡）===
name        =
this.name   =
super.name  =
p.name      =

Q1 三個寫法分別印什麼：

Q2 為什麼 name 跟 this.name 一樣（省略 this 是不是等於不寫）：

Q3 p.name 跟 super.name 一樣嗎為什麼：

Q4 「欄位看宣告型別」在這題哪幾行驗證：

Q5 加 GrandChild04 後 Child04 的 super.name 會變嗎為什麼：

*/
