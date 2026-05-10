import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileManager {
    private final String USERS_FILE = "users.txt";
    private final String ACCOUNTS_FILE = "accounts.txt";
    private final String TRANSACTIONS_FILE = "transactions.txt";

    public void saveUsers(List<User> users) {
        try {
            File output = new File(USERS_FILE);
            PrintWriter writeToFile = new PrintWriter(output);

            for (User user : users) {
                writeToFile.println(user.getUserId() + "," + user.getUserName() + "," + user.getPassword() + "," +
                        user.getName() + "," + user.getEmail());
            }
            writeToFile.close();
            System.out.println("Users successfully saved to " + USERS_FILE);

        } catch (FileNotFoundException e) {
            System.out.println("Saving user was unSuccessful: " + e.getMessage());
        }
    }

    public List<User> loadUsers() {
        List<User> loadedUsers = new ArrayList<>();
        try {
            File myFile = new File(USERS_FILE);

            if (myFile.exists()) {
                Scanner input = new Scanner(myFile);

                while (input.hasNextLine()) {
                    String line = input.nextLine();
                    String[] data = line.split(",");

                    if (data.length == 5) {
                        Customer customer = new Customer(data[0], data[1], data[2], data[3], data[4], "Address Not Set");
                        loadedUsers.add(customer);
                    }
                }
                input.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Users file not found. Starting with an empty user list.");
        }
        return loadedUsers;
    }


    public void saveAccounts(List<Account> accounts) {
        try {
            File output = new File(ACCOUNTS_FILE);
            PrintWriter writeToFile = new PrintWriter(output);

            for (Account acc : accounts) {
                String accountType = (acc instanceof SavingsAccount) ? "Savings" : "Checking";

                writeToFile.println(acc.getAccountNumber() + "," +
                        acc.getBalance() + "," +
                        acc.getStatus() + "," +
                        acc.getOwner().getUserId() + "," +
                        accountType);
            }
            writeToFile.close();
            System.out.println("Accounts successfully saved to " + ACCOUNTS_FILE);

        } catch (FileNotFoundException e) {
            System.out.println("Saving accounts was unsuccessful.");
        }
    }

    public List<Account> loadAccounts(List<User> existingUsers) {
        List<Account> loadedAccounts = new ArrayList<>();
        try {
            File myFile = new File(ACCOUNTS_FILE);

            if (myFile.exists()) {
                Scanner input = new Scanner(myFile);

                while (input.hasNextLine()) {
                    String line = input.nextLine();
                    String[] data = line.split(",");

                    if (data.length == 5) {
                        String accNum = data[0];
                        double balance = Double.parseDouble(data[1]);
                        String status = data[2];
                        String ownerId = data[3];
                        String type = data[4];

                        Customer owner = null;
                        for (User u : existingUsers) {
                            if (u.getUserId().equals(ownerId) && u instanceof Customer) {
                                owner = (Customer) u;
                                break;
                            }
                        }

                        if (owner != null) {
                            Account acc;
                            if (type.equals("Savings")) {
                                acc = new SavingsAccount(accNum, owner);
                            } else {
                                acc = new CheckingAccount(accNum, owner);
                            }

                            acc.setStatus(status);
                            loadedAccounts.add(acc);
                            owner.getAccounts().add(acc);
                        }
                    }
                }
                input.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Accounts file was not found. Starting with an empty account list.");
        }
        return loadedAccounts;
    }



    public void recordTransaction(Transaction transaction) {
        try {
            FileWriter fw = new FileWriter(TRANSACTIONS_FILE, true);

            String fromAcc = (transaction.getFromAccount() != null) ? transaction.getFromAccount().getAccountNumber() : "NULL";
            String towardAcc = (transaction.gettowardAccount() != null) ? transaction.gettowardAccount().getAccountNumber() : "NULL";

            String formattedTx = transaction.getTransactionId() + "," + transaction.getTimestamp() + "," + transaction.getType() + "," +
                    transaction.getAmount() + "," + fromAcc + "," + towardAcc + "," + transaction.getStatus() + "\n";

            fw.write(formattedTx);
            fw.close();

        } catch (IOException e) {
            System.out.println("Saving Transaction into the records failed: " + e.getMessage());
        }
    }
}