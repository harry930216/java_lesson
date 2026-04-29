package tw.org.iii;

public class Harry01 {

	public static void main(String[] args) {
		// byte 2^8 , short 2^16, int 2^32, long 2^64
		byte var1; // [a-zA-Z$_] [a-zA-Z0-9$_]* $_這兩盡量不要用
		var1 = 126;
//		var1 = var1 + 1; 因為byte/short/int運算都先存成int
//		
		byte var2 = 1;
		int var3;
		var3 = var1 + var2;
		System.out.println(var3);
		}

}
