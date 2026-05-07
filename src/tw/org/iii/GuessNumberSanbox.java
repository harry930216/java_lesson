package tw.org.iii;

import java.util.ArrayList;
import java.util.List;

class Customer {
    String name;
    List<Order> orders = new ArrayList<>();
    
    void addOrder(Order o) {
        orders.add(o);
    }
}

class Order {
    String item;
    Customer customer;
    
    void assignTo(Customer c) {
        this.customer = c;          // ① 我記住客人
        c.addOrder(this);           // ② 讓客人也記住「我」
    }
}
public class Test {
    public static void main(String[] args) {
        Customer alice = new Customer();
        alice.name = "Alice";
        
        Order o1 = new Order();
        o1.item = "Book";
        
        o1.assignTo(alice);
        
        System.out.println(o1.customer.name);          // Alice
        System.out.println(alice.orders.get(0).item);  // Book
    }
}
