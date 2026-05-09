import java.util.ArrayList;
import java.util.List;

public class Bank {


    private List<User> users;
    private List<Account> accounts;
    private List<Transaction> transactions;

    public Bank() {
        this.users = new ArrayList<>();
        this.accounts = new ArrayList<>();
        this.transactions = new ArrayList<>();
    }

    public List<User> getUsers() { return users; }
    public List<Account> getAccounts() { return accounts; }
    public List<Transaction> getTransactions() { return transactions; }

    public void createUser(User user) {
        if (user != null) {
            users.add(user);
            System.out.println("System: " + user.getName() + " has been added to the bank's user database.");
        }
    }


    public User findUser(String userName) {
        for (User user : users) {
            if (user.getUserName().equalsIgnoreCase(userName)) {
                return user; // Found them!
            }
        }
        System.out.println("Error: User '" + userName + "' not found in the system.");
        return null;
    }

    // Helper Method: We need this to search for accounts by their account number
    public Account findAccount(String accountNumber) {
        for (Account acc : accounts) {
            if (acc.getAccountNumber().equals(accountNumber)) {
                return acc;
            }
        }
        return null;
    }

    public void createAccount(Account account, Customer customer) {
        if (account != null && customer != null) {
            this.accounts.add(account);
            customer.openAccount(account);
        }
    }

    public boolean processTransaction(String sourceAccountNumber, String type, double amount, String toAccountNumber, String transactionId) {

        Account source = findAccount(sourceAccountNumber);

        if (source == null) {
            System.out.println("Error: Source account not found.");
            return false;
        }

        boolean success = false;
        if (type.equalsIgnoreCase("Deposit")) {
            source.deposit(amount, transactionId);
            success = true;

        } else if (type.equalsIgnoreCase("Withdrawal")) {
            success = source.withdraw(amount, transactionId);

        } else if (type.equalsIgnoreCase("Transfer")) {
            Account to = findAccount(toAccountNumber);
            if (to != null) {
                success = source.transfer(transactionId, amount, to);
            } else {
                System.out.println("Error: Destination account not found.");
                success = false;
            }

        } else {
            System.out.println("Error: Unknown transaction type.");
            success = false;
        }

        if (success) {
            int lastIndex = source.getTransactions().size() - 1;
            Transaction newReceipt = source.getTransactions().get(lastIndex);
            this.transactions.add(newReceipt);
        }

        return success;
    }
}