package tw.Harry.model;

public class CalsModel {
	private int x, y;
	public CalsModel(String x, String y) {
		this.x = Integer.parseInt(x);
		this.x = Integer.parseInt(y);
	}
	
	public String add() {
		return x + y + "";
	}
}
