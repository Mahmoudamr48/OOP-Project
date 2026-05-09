public abstract class CheckingAccount extends Account {


    public CheckingAccount(String accountNumber, Customer owner) {
        super(accountNumber, owner);
    }


    public boolean withdraw(double amount) {
        if (getBalance() >= amount) {
            setBalance(getBalance() - amount);
            System.out.println("Withdrawn was successful!!! ,"+ amount +" was withdrawn.");
            return true;}
        else {System.out.println("Withdraw failed *probably* due to the" +
                                 " non existence of requested amount.");
            return false;}
    }


}