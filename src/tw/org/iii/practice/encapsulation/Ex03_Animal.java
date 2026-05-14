package tw.org.iii.practice.encapsulation;

// 題 3 — 封裝 + 繼承
// 目標：父類欄位 private，子類透過父類的 getter/setter 存取，不能直接碰欄位。
// 情境：Animal 有 name，Dog 繼承它，Dog 的建構子透過父類方法設定名字，然後印出來。

public class Ex03_Animal {

    public static void main(String[] args) {
    	new Dog("我是好狗");
    }
}

class Animal {
	private String name;

	public Animal() {
		this("無名");
	}

	public Animal(String name) {
		this.name = name;
	}

	// private 欄位必須透過 getter 才能讓外部（包含子類）讀取
	public String getName() {
		return name;
	}
}

class Dog extends Animal {
	Dog(String name){
		super(name);
		// 沒有覆寫 getName()，super. 是多餘的，直接 getName() 即可
		System.out.println(getName());
	}

}
