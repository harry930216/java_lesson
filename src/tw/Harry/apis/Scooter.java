package tw.Harry.apis;

public class Scooter extends Bike {
	
	private int gear;
	
	public int changeGear(int gear) {
		if (gear >= 0 && gear <=4) {
			this.gear = gear;
		}
		return this.gear;
	}
	// Override
	public void upSpeed() {
		speed = speed < 1? 1:speed * 3 * gear;
	}
}
