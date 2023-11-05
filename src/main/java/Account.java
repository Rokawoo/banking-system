import java.util.concurrent.atomic.AtomicInteger;

public abstract class Account {
    private String type;
    private double apr;
    private double balance;

    public Account(String type, double apr, double balance) {
        this.type = type;
        this.apr = apr;
        this.balance = balance;
    }

    public String getType() { return type;}

    public double getAPR() {return apr;}

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
