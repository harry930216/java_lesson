package tw.org.iii;

import tw.Harry.apis.Bike;
import tw.Harry.apis.Scooter;

public class Harry14Scooter {

	public static void main(String[] args) {
		Scooter b1 = new Scooter();
		b1.upSpeed();
		b1.changeGear(1);
		b1.upSpeed();
		b1.upSpeed();
		b1.changeGear(4);
		b1.upSpeed();
		System.out.println(b1.getSpeed());
		
		b1.downSpeed();
		System.out.println(b1.getSpeed());
	}

}
