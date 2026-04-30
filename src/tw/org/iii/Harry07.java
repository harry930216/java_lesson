package tw.org.iii;

public class Harry07 {

	static int i = 1;

	public static void main(String[] args) {

		for (initial(); i < 10; contin()) {
			System.out.printf("步驟2第%d次", i);
			System.out.println();
		}

	}

	public static void initial() {
		System.out.println("初始值必定執行-步驟1");
	}

	public static void contin() {
		System.out.printf("步驟3第%d次", i++);
		System.out.println();
		
		
	}
	
//	public static void main(String[] args) {
//		int i = 0;
//		for (printBrad(); i < 10; printLine()) {
//			System.out.println(i++);
//		}
//
//	}
//	
//	static void printBrad() {
//		System.out.println("Brad");
//	}
//	
//	static void printLine() {
//		System.out.println("-------");
//	}
}
