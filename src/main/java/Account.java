import java.util.concurrent.atomic.AtomicInteger;

public abstract class Account {
    private double APR;
    private double balance;

    public Account(double apr, double balance) {
        this.APR = apr;
        this.balance = balance;
    }

    public double getAPR() {return APR;}

    public double getBalance() {return balance;}

    public void deposit(double depositAmount) {
        this.balance += depositAmount;
    }

    public void withdraw(double withdrawAmount) {
        this.balance -= withdrawAmount;
        if (this.balance < 0) {
            this.balance = 0;
        }
    }
}
