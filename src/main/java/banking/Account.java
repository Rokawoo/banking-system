package banking;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public abstract class Account {
    private final String type;
    private final double apr;
    private double balance;
    protected int withdrawHoldUntil;
    private final List<String> transactionHistoryList = new ArrayList<>();

    private static final DecimalFormat df = new DecimalFormat("#.00");

    protected Account(String type, double apr, double balance) {
        this.type = type;
        this.apr = Double.parseDouble(df.format(apr));
        this.balance = Double.parseDouble(df.format(balance));
        this.withdrawHoldUntil = -1;
    }

    public String getType() {
        return type;
    }

    public double getApr() {
        return Double.parseDouble(df.format(apr));
    }

    public double getBalance() {
        return Double.parseDouble(df.format(balance));
    }

    public int getWithdrawHoldUntil() {return withdrawHoldUntil;}

    public void deposit(double depositAmount) {
        this.balance += depositAmount;
    }

    public double withdraw(double withdrawAmount) {
        double actualWithdrawal = Math.min(withdrawAmount, this.balance);
        this.balance -= actualWithdrawal;

        if (this.balance < 0) {
            this.balance = 0;
        }

        return Double.parseDouble(df.format(actualWithdrawal));
    }

    public abstract boolean isValidDeposit(float amount);

    public abstract boolean isValidWithdraw(float amount, int currentMonth);

    public abstract void setInitialWithdrawHold(int currentMonth);

    public abstract void updateWithdrawHold(int currentMonth);

    public void addCommandToTransactionHistory(String command) {
        transactionHistoryList.add(command);
    }

    public List<String> retrieveTransactionHistory() {
        return transactionHistoryList;
    }
}
