public class SavingsAccount extends Account {
    double interestRate;
    double minimumBalance;
    double withdrawalLimit;
    int withdrawalsThisMonth;

    public SavingsAccount(String accountNumber, Customer owner) {
        super(accountNumber, owner);
        interestRate = 0.02;
        minimumBalance = 100;
        this.withdrawalLimit = 3;
        this.withdrawalsThisMonth = 0;
    }


    public void setWithdrawalLimit(int limit) {
        if (limit > 0) {
            this.withdrawalLimit = limit;
        }
    }


    @Override
    public boolean withdraw(double amount, String transactionId) {
        if (withdrawalLimit > withdrawalsThisMonth && (getBalance() - amount) >= minimumBalance) {
            setBalance(getBalance() - amount);
            System.out.println(amount + " was withdrawn.");
            withdrawalsThisMonth++;
            return true;
        } else if ((getBalance() - amount) >= minimumBalance) {
            System.out.println("This amount cant be withdrawn because the account reached the minimum balance value.");
            return false;

        } else {
            System.out.println("Monthly Withdrawing limit reached, Process can not be continued.");
            return false;
        }
    }


    public double calcInterest() {
        return getBalance() * (interestRate / 12);
    }

    public void MonthlyInterest() {
        withdrawalsThisMonth = 0;
        double interest = calcInterest();
        setBalance(getBalance() + interest);

        System.out.println("Monthly interest applied is " + interest);
    }
}

