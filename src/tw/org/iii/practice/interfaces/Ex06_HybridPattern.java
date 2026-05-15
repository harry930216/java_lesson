package tw.org.iii.practice.interfaces;

// 題 6 — 混合骨幹模式（Skeletal Implementation Pattern）
// 目標：學 Java 標準庫到處用的「interface + abstract 骨幹 + concrete」三層架構。
//
// 真實對應：List → AbstractList → ArrayList
//          Map  → AbstractMap  → HashMap
// 你寫完這題，看 Java 源碼會直接懂為什麼這樣排。
//
// 情境：
//   第 1 層 Animal2 介面（純契約）：
//     - String getName();
//     - void eat();
//     - void sleep();
//
//   第 2 層 AbstractAnimal 抽象類（骨幹：把共通行為先寫好）：
//     - implements Animal2
//     - 有 name 欄位 + 建構子收 name
//     - 實作 getName() 回傳 name
//     - 實作 sleep() 印 "name 睡覺了"   ← 所有動物睡覺都長一樣，先寫好
//     - eat() 保持 abstract           ← 每種動物吃的東西不同，留給子類
//
//   第 3 層 Dog2 具體類：
//     - extends AbstractAnimal
//     - 建構子 Dog2(String name) 用 super(name)
//     - eat() 印 "name 吃骨頭"
//
// 在 main：new Dog2("小白")，呼叫 getName()、eat()、sleep()。
//
// 觀察重點（寫完回答）：
//   1. Dog2 只寫了一個 eat() 方法，為什麼還能 getName() 和 sleep()？
//   2. 如果不用 AbstractAnimal 骨幹，直接 Dog2 implements Animal2，會多寫什麼？
//   3. 為什麼介面定契約、抽象類提供骨幹、具體類補空缺，是「最少重複 + 最大彈性」的組合？

public class Ex06_HybridPattern {

	public static void main(String[] args) {
		Animal2 white = new Dog2("小白");
		System.out.println(white.getName());
		white.eat();
		white.sleep();
	}
}

interface Animal2 {
  String getName();
  void eat();
  void sleep();
}

abstract class AbstractAnimal implements Animal2 {
	protected String name;
	
	public AbstractAnimal(String name) {
		this.name = name;
	}
	
	@Override
	public String getName() {
		
		return name;
	}
	
	@Override
	public void sleep() {
		System.out.printf("%s 睡覺了\n",getName());
	}
	
	public abstract void eat();
}

class Dog2 extends AbstractAnimal {
	public Dog2(String name) {
		super(name);
	}
	
	@Override
	public void eat() {
		System.out.printf("%s 吃骨頭\n",getName());
	}

}

/* === 觀察結果 ===
getName() = 小白
eat()     = 小白 吃骨頭
sleep()   = 小白 睡覺了

Q1 Dog2 只寫一個 eat() 為什麼還能用其他方法：
   用Animal2視角看

   [Claude 補充] partial — 方向偏，「視角」是講「怎麼被呼叫到」(dispatch)，
                          題目問「Dog2 為什麼擁有」(possess)
   關鍵字是 inheritance（繼承）：
     Animal2（介面，定契約）
        ↑ implements
     AbstractAnimal（提供 getName() 和 sleep() 實作）
        ↑ extends
     Dog2（繼承上面所有東西，只補 eat()）
   → Dog2 透過 extends AbstractAnimal「繼承」了 getName() 和 sleep() 實作。
     這些方法已經是 Dog2 的一部分，就算用 Dog2 型別（不是 Animal2）呼叫也能用：
        Dog2 d = new Dog2("小白");
        d.getName();   // ✅ 跟 Animal2 視角無關
   → 答案核心：繼承（不是視角）。

Q2 不用 AbstractAnimal 骨幹會多寫什麼：
   這個例子不太好 因為子代少 如果子代多 睡覺就得重複寫

   [Claude 補充] ✅ kernel 對 — 列出具體重複內容更實際
   如果 Dog2 直接 implements Animal2，每個動物子類都要重抄這四件事：
     1. private String name;                                     （field）
     2. public Dog2(String name) { this.name = name; }           （constructor）
     3. public String getName() { return name; }                 （getter）
     4. public void sleep() { ... }                              （sleep 邏輯）
   → 加 Cat2、Bird2、Tiger2... 每個都要再抄一次。
   → 骨幹層 = DRY (Don't Repeat Yourself) 原則的實踐。

Q3 三層架構為什麼是「最少重複 + 最大彈性」：
   因為可以靈活運用 介面無限實作 類別選麻煩的來繼承

   [Claude 補充] 抓到一點，但缺核心洞察「兩條路都通」
   Animal2（介面）
       ├─ 路 A: 直接 implements Animal2 → 自己刻所有方法（適合極簡 / 第三方）
       └─ 路 B: extends AbstractAnimal  → 借骨幹，只填空缺（適合大多數人）
   - interface 不強迫你走 abstract 那條路 — 想偷懶就借骨幹，想自由就自己刻
   - abstract 不會「劫持」契約 — Animal2 是契約，AbstractAnimal 只是「順手提供的便利實作」
   - 兩條路產生的物件對外看都是 Animal2（多型統一）

   →「最少重複」= 路 B 把共通的寫一次
   →「最大彈性」= 路 A 還在，沒被擋掉；新人/特殊情境可以繞過骨幹

   Java 標準庫實例（看到這個你會直接懂）：
   | 介面          | 骨幹                  | 具體實作                          |
   |--------------|----------------------|----------------------------------|
   | List         | AbstractList         | ArrayList, LinkedList, Vector    |
   | Map          | AbstractMap          | HashMap, TreeMap                 |
   | Set          | AbstractSet          | HashSet, TreeSet                 |
   | Collection   | AbstractCollection   | 以上全部                          |

   這是 Joshua Bloch（Effective Java 作者）推廣的 "Skeletal Implementation" pattern，
   整個 Java Collections Framework 都這樣設計。

[Claude 風格小提醒 — 跟對錯無關]
   AbstractAnimal 裡的 `public abstract void eat();` 可以省略不寫。
   AbstractAnimal implements Animal2 但沒給 eat() body → eat() 自動「繼承為 abstract」。
   寫不寫都對，明寫的好處是讀的人看得清楚，省略的好處是少抄。
   Java 標準庫 AbstractList 那邊是省略寫法（ArrayList 自己會被強迫實作 abstract method）。
*/
