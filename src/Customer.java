import java.util.ArrayList;

public class Customer extends User{
    String Address;
    ArrayList<Account> accounts;

    public Customer(String userId, String userName, String password, String name, String email,String Address) {
        super(userId, userName, password, name, email);
        this.Address = Address;
        accounts = new ArrayList<>();
    }
}
