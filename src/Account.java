import java.util.ArrayList;

public abstract class  Account implements Reportable {
    private String accountNumber;
    private double balance;
    private String status;
    private Customer owner;
    ArrayList<Transaction> transactions;


    public Account(String accountNumber, Customer owner) {
        this.accountNumber = accountNumber;
        this.owner = owner;
        this.status = "Online";
        this.transactions = new ArrayList<>();
    }


    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public String getStatus() {
        return status;
    }

    public Customer getOwner() {
        return owner;
    }


    public void setStatus(String status) {
        if (status.equalsIgnoreCase("Active") || status.equalsIgnoreCase("Inactive")
                || status.equalsIgnoreCase("Frozen")) {
            this.status = status;
        } else System.out.println("RESELECT");
    }


    public void deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Deposit can't be Zero or lower.");
        } else {
            balance += amount;
            System.out.println(amount + " got deposited successfully.");
        }
    }

    public abstract boolean withdraw(double amount,String transactionId);

    protected void setBalance(double balance) {
        if (balance >= 0) {
            this.balance = balance;
        } else {
            System.out.println("Balance cannot be set to a negative number");
        }
    }
    public void recordTransaction(double amount,String transactionId,Account towardAccount ) {
        balance += amount;
        Transaction t =new Transaction(amount,towardAccount,transactionId);
        this.transactions.add(t);
    }
    protected void recordOutgoingTransfer(double amount, String toAccount, String transactionId,String accountNumber) {
        balance -= amount;
        Transaction t = new Transaction(transactionId, "TRANSFER_OUT", amount,
                accountNumber, toAccount, "COMPLETED");
        transactions.add(t);
}

}


