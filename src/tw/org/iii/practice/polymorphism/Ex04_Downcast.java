package tw.org.iii.practice.polymorphism;

// 題 4 — 向下轉型
// 目標：從父類型別變數取出子類物件，轉型後呼叫子類專屬方法。
// 情境：Vehicle 是父類，Car 多一個 honk() 方法，Truck 多一個 loadCargo() 方法。
//       main 裡用 Vehicle[] 裝，取出時轉型成對應子類再呼叫專屬方法。

public class Ex04_Downcast {

	public static void main(String[] args) {
		Vehicle2[] vehicle = { new Car2(), new Truck() };
		for (Vehicle2 i : vehicle) {
			// instanceof 先判斷型別，確認後再向下轉型，避免 ClassCastException
			if (i instanceof Car2) {
				((Car2) i).honk();
			} else if (i instanceof Truck) {
				((Truck) i).loadCargo();
			}
		}

	}
}

abstract class Vehicle2 {

}

class Car2 extends Vehicle2 {
	void honk() {
		System.out.println("車專屬");
	}
}

class Truck extends Vehicle2 {
	void loadCargo() {
		System.out.println("卡車專屬");
	}
}
