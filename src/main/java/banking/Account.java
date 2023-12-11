package banking;

import java.text.DecimalFormat;

public abstract class Account {
    private String type;
    private double apr;
    private double balance;
    protected int withdrawHoldUntil;

    private static final DecimalFormat df = new DecimalFormat("#.00");

    protected Account(String type, double apr, double balance) {
        this.type = type;
        this.apr = apr;
        this.balance = balance;
        this.withdrawHoldUntil = -1;
    }

    public String getType() { return type;}

    public double getApr() {return apr;}

    public double getBalance() {return Double.parseDouble(df.format(balance));}

    public void deposit(double depositAmount) {
        this.balance += depositAmount;
        this.balance = Double.parseDouble(df.format(this.balance));
    }

    public double withdraw(double withdrawAmount) {
        double actualWithdrawal = Math.min(withdrawAmount, this.balance);
        this.balance -= actualWithdrawal;

        if (this.balance < 0) {
            this.balance = 0;
        }

        actualWithdrawal = Double.parseDouble(df.format(actualWithdrawal));
        return actualWithdrawal;
    }

    public abstract boolean isValidDeposit(float amount);

    public abstract boolean isValidWithdraw(float amount, int currentMonth);

    public abstract void setInitialWithdrawHold(int currentMonth);

    public abstract void updateWithdrawHold(int currentMonth);
}
