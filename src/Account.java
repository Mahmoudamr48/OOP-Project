import java.util.ArrayList;

public abstract class Account {
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

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }


    public void setStatus(String status) {
        if (status.equalsIgnoreCase("Active") || status.equalsIgnoreCase("Inactive")
                || status.equalsIgnoreCase("Frozen")) {
            this.status = status;
        } else System.out.println("RESELECT");
    }

    public void deposit(double amount, String transactionId) {
        if (amount <= 0) {
            System.out.println("Deposit can't be Zero or lower.");
        } else {
            balance += amount;
            System.out.println(amount + " got deposited successfully.");
            Transaction t = new Transaction(transactionId, amount, "Deposit", this, null, "COMPLETED");
            this.transactions.add(t);
        }
    }

    public abstract boolean withdraw(double amount, String transactionId);

    protected void setBalance(double balance) {
        if (balance >= 0) {
            this.balance = balance;
        } else {
            System.out.println("Balance cannot be set to a negative number");
        }
    }


    public boolean transfer(String transactionId, double amount, Account towardAccount) {

        if (amount > 0 && this.balance >= amount) {
            this.setBalance(this.balance - amount);
            towardAccount.deposit(amount, transactionId);
            Transaction transaction = new Transaction(transactionId, amount, "Transfer", this, towardAccount, "COMPLETED");

            this.transactions.add(transaction);
            towardAccount.getTransactions().add(transaction);
            System.out.println(transaction.getTransactionDetails());

            return true;

        } else {
            System.out.println("Account balance is too low or invalid amount, Transaction Failed");
            return false;
        }
    }


    public void getTransactionHistory() {
        System.out.println("Recent Transactions for Account: " + this.accountNumber );

        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }

        int startIndex = 0;
        if (transactions.size() > 10) {
            startIndex = transactions.size() - 10;
        }

        for (int i = startIndex; i < transactions.size(); i++) {
            System.out.println(transactions.get(i).getTransactionDetails());
        }
    }
}


