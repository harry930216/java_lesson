package tw.org.iii;

public class Harry15important {

	public static void main(String[] args) {
		Harry151 a1 = new Harry151(1);
		a1.m1();
		Harry152 a2 = new Harry152();
	}
}

/*
 * 1. 所有類別一定有建構式 => 有定義照你的，無定義 => 編譯器補 default constructor (no args)
 * 2. 所有 constructor 第一道敘述句 => 沒寫 super(...) 或 this(...) 才自動補 super()
 *    目的：把 super 初始化（祖先只能被初始化一次）
 * 3. 建構式沒有在繼承（但子類強制呼叫父類建構式）
 */

class Harry151 extends Object {
	Harry151(int a) {
		super();
		System.out.println("Harry151()");
	}

	void m1() {
		System.out.println("Harry:m1()");
	}
}

// 建構式永遠需要存在 如無抓父元素的無傳參數建構式
// 編譯器會幫忙抓無傳參數建構式

class Harry152 extends Harry151 {
	Harry152() {
		super(1); // java就在做這事 super是隱含做這事 呼叫祖宗十八代 一代傳一代
		System.out.println("Harry152()");
	}
}

// 注意建構式順序