package tw.harry.tutor;

public class Jdbc01 {
    public static void main(String[] args) {
        // 1. Load Driver => Connector
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("OK");
        } catch (ClassNotFoundException e) {
            System.out.println(e);
        }
    }
}
