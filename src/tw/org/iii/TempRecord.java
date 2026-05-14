package tw.org.iii;

/* ===== 題 1 =====
// 目標：子類覆寫父類方法，但先執行父類的邏輯再附加自己的行為。
// 情境：BaseLogger 會印基本訊息，AppLogger 繼承它並在後面多印一層 [APP] 標籤。

// ===== 題 2 =====
// 目標：父類有有參建構子，子類透過 super(...) 把參數傳上去，再初始化自己的欄位。
// 情境：Vehicle 有品牌(brand)欄位，Car 繼承它並多一個座位數(seats)欄位。

// ===== 題 3 =====
// 目標：父類只寫有參建構子（不寫無參），子類故意不呼叫 super(...)，觀察編譯錯誤，修好後說明為什麼會錯。
// 情境：Server 只有有參建構子接受 port，Node 繼承它但忘了傳 port 上去。
*/
public class TempRecord {

	public static void main(String[] args) {

			Car car1 = new Car("寶馬",20);
			
			System.out.println(car1.brand);
			System.out.println(car1.seats);
	}
}

class Vehicle {
	String brand;

	public Vehicle(String brand) {
		this.brand = brand;
	}

}

class Car extends Vehicle {
	int seats;

	public Car(String brand, int seats) {
//		super(brand);
		this.seats = seats;

	}
}
