import java.time.LocalDateTime;

public class Transaction {
    String transactionId;
    double amount;
    String type;
    Account fromAccount;
    Account towardAccount;
    String status;
    public Transaction(String transactionId, double amount, String type, Account fromAccount, Account towardAccount, String status) {
        this.transactionId = transactionId;
        this.amount = amount;
        this.type = type;
        this.fromAccount = fromAccount;
        this.towardAccount = towardAccount;
        this.status = status;
    }
    public String getTransactionId() {
        return transactionId;
    }

    public Account getFromAccount() {
        return fromAccount;
    }
    public Account getTowardAccount() {
        return towardAccount;
    }
    public String getStatus() {
        return status;

    }

    public String getType() {
        return type;
    }
    public double getAmount() {
        return amount;
    }
}




