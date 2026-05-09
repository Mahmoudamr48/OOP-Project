public class SavingsAccount extends Account {
    double interestRate;
    double minimumBalance;
    double withdrawalLimit;
    int withdrawalsThisMonth;

    public SavingsAccount(String accountNumber, Customer owner, double firstDeposit) {
        super(accountNumber, owner);
        interestRate = 0.02;
        minimumBalance = 100;
        this.withdrawalLimit = 3;
        this.withdrawalsThisMonth = 0;
    }


    public boolean withdraw(double amount) {
        if (withdrawalLimit > withdrawalsThisMonth && (getBalance() - amount) >= minimumBalance) {
            setBalance(getBalance() - amount);
            System.out.println("Withdrawn was successful!!! ,"+ amount +" was withdrawn.");
            withdrawalsThisMonth++;
            return true;
        } else if ((getBalance() - amount) >= minimumBalance) {
            System.out.println("This amount cant be withdrawn because the account reached the min balance.");
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

        System.out.println("Monthly interest applied is " + interest+".");
    }
}

