package tw.harry.tutor;

import tw.harry.apis.BCrypt;

public class Jdbc14 {

	public static void main(String[] args) {
		String password = "123456";
		String hash_pass = BCrypt.hashpw(password, BCrypt.gensalt());
		System.out.println(hash_pass);
		
		String input = "123456";
		if (BCrypt.checkpw(input, hash_pass)) {
		    System.out.println("OK");
		}else {
		    System.out.println("XX");
		}
	}

}
