package tw.org.iii;

public class Harry07 {

	public static void main(String[] args) {
		
		int i = 0;		
		for (initial(); i < 10; contin()) {
			System.out.printf("步驟2第 + %d + 次",i+1);
		}

	}
	
	public static void initial() {
		System.out.println("初始值必定執行-步驟1");
	}
	
	public static void contin() {
		i++;
		System.out.printf("步驟3第 + %d + 次",i+1);
	}
}
