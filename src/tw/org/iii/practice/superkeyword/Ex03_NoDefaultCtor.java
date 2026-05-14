package tw.org.iii.practice.superkeyword;

// 題 3 — 故意踩雷：父類沒有無參建構子
// 目標：故意製造編譯錯誤，看懂錯誤訊息，再用 super(...) 修好。
//
// 【用戶一句話點破】
// 「自動補無參 super，但父沒有無參啊」
// 也就是：編譯器永遠會偷加 super()，父類有對應的就過、沒有就炸。
//
// 【補充重點】這題的設定跟 Ex02 一樣（父類只有有參建構子），
// 唯一差別是「子類有沒有明寫 super(...)」。用反例證明規則：
//   Ex02：明寫 super(name) → 編譯過
//   Ex03：不寫 super(...) → 編譯炸 → 暴露編譯器隱式行為

public class Ex03_NoDefaultCtor {

	public static void main(String[] args) {
		Dog d = new Dog();
		d.show();
		// 預期輸出：name=狗
	}
}

class Animal {
	String name;

	Animal(String name) {       // 故意只給有參、不給無參
		this.name = name;
	}
}

class Dog extends Animal {

	/* === 原本長這樣（編譯錯）===
	Dog() {
		// 編譯器偷加 super() 在這一行
		// 錯誤訊息：Implicit super constructor Animal() is undefined.
		//          Must explicitly invoke another constructor
	}
	*/

	Dog() {
		super("狗");   // ← 修法：明寫 super(...) 餵一個值給父類有參建構子
	}

	void show() {
		System.out.println("name=" + name);
	}
}

// 規則濃縮（用戶觀察到的本質）：
// 1. 子類建構子第一行一定有 super(...) 或 this(...)，沒寫 → 編譯器自動補 super() 無參版
// 2. 因為自動補的是「無參」super()，所以父類必須提供無參建構子才能讓子類偷懶
// 3. 父類只給有參建構子？子類必須「明寫」super(...) 餵值，沒有選擇
//
// 為什麼這個雷重要：
// 後端框架（如 JPA/Hibernate）的 Entity 規定要有無參建構子，就是因為框架要靠
// new Entity() 反射建立物件，沒無參會炸 → 你會在實務遇到這個錯誤訊息。
