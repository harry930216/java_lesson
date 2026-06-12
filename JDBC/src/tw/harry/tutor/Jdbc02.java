package tw.harry.tutor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Jdbc02 {

	public static void main(String[] args) {
	    final String URL = "jdbc:mysql://localhost:3306/harry?user=root&password=root&useSSL=false";
	    final String SQL = """
	            INSERT INTO test
	            (cname, tel, birthday)
	            VALUES ("peter","123","1999-01-02")
	            """;
	    try (Connection conn = DriverManager.getConnection(URL);
	        Statement stmt = conn.createStatement();
	        ){
	        stmt.execute(SQL);
	        System.out.println("OK");
	    } catch (Exception e) {
	        System.out.println(e);
	    }
	}

}
