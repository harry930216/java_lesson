package tw.org.iii;

import tw.Harry.apis.Bike;

public class Harry14Bike {

	public static void main(String[] args) {
		Bike b1 = new Bike();
		System.out.println(b1.getSpeed());
		while(b1.getSpeed() < 10) {
			b1.upSpeed();
		}
		System.out.println(b1.getSpeed());
		
		System.out.println("-------------------");
		
		Bike b2 = new Bike();
		b2.upSpeed();
		b2.upSpeed();
		b2.upSpeed();
		b2.upSpeed();
		b2.upSpeed();
		b2.upSpeed();
		b2.downSpeed();
		b2.downSpeed();
		System.out.println(b2.getSpeed());

	}

}
