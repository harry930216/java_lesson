package tw.org.iii.practice.polymorphism;

// 題 2 — 多型 + 參數
// 目標：寫一個方法，參數型別是父類，傳進不同子類物件會跑出不同行為。
// 情境：Payment 是父類，CreditCard 和 Cash 繼承它，Checkout 類別有 pay(Payment p) 方法處理結帳。

public class Ex02_Payment {

	public static void main(String[] args) {
		
		Checkout check1 = new Checkout();
		check1.pay(new Cash());
		check1.pay(new CreditCard());
 	}
}

// abstract 防止直接 new Payment()，強制子類實作 rePayment()
abstract class Payment {
	abstract void rePayment();
}

class CreditCard extends Payment {
	void rePayment() {
		System.out.println("我刷卡");
	}
}

class Cash extends Payment {
	void rePayment() {
		System.out.println("我付現");
	}
}

class Checkout {
	// 參數型別是父類，傳進任何子類都能執行，新增付款方式不用改這裡
	void pay(Payment p){
		p.rePayment();
	}
}
