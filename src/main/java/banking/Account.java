package banking;

public abstract class Account {
    private String type;
    private double apr;
    private double balance;
    protected int withdrawHoldUntil;

    protected Account(String type, double apr, double balance) {
        this.type = type;
        this.apr = apr;
        this.balance = balance;
        this.withdrawHoldUntil = -1;
    }

    public String getType() { return type;}

    public double getApr() {return apr;}

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
    public abstract boolean isValidDeposit(float amount);

    public abstract boolean isValidWithdraw(float amount, int currentMonth);

    public abstract void updateWithdrawHold(int currentMonth);

}
