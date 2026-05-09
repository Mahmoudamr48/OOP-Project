public abstract class CheckingAccount extends Account {


    public CheckingAccount(String accountNumber, Customer owner) {
        super(accountNumber, owner);
    }

    public boolean withdraw(double amount) {
        if (getBalance() >= amount) {

        }
    }
}