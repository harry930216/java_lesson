package tw.Harry.apis;

public class Bike extends Object { 
	// Object為所有java的父類別 所以共同有方法
	// has-a
	protected double speed;
	
	/*
	 * private : 本類別
	 * 沒寫 : 相同package
	 * protected : 子類別 + 相同package
	 * public : 全世界
	 */
	
	public void upSpeed() {
		speed = speed < 1? 1:speed * 1.4;
	}
	
	public void downSpeed() {
		speed = speed < 1? 0:speed / 1.4;
	}
	
	public double getSpeed() {
		return speed;
	}
}
