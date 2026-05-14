package tw.org.iii.encapsulation;

// 題 1 — 基本封裝
// 目標：把欄位藏起來，只透過方法存取和修改。
// 情境：BankAccount 有餘額，外部只能存款、提款、查餘額，不能直接改數字。

public class Ex01_BankAccount {

	public static void main(String[] args) {
		BankAccount my = new BankAccount();
		my.addMoney(20000);
		System.out.println(my.seeMoney());
		my.takeMoney(15000);
	}
}

class BankAccount {
	private int money;

	public int seeMoney() {
		return money;
	}

	public void addMoney(int m) {
		money += m;
		System.out.printf("存入%d 目前%d元\n",m ,money);
	}

	public void takeMoney(int m) {
		// 實務上應加防護，封裝的價值在於驗證邏輯集中在方法裡，外部無法破壞
		// if (m > money) { System.out.println("餘額不足"); return; }
		money -= m;
		System.out.printf("提款%d 目前%d元\n",m ,money);
	}

}
