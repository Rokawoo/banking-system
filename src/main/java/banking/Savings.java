package banking;

public class Savings extends Account {
    public Savings(double apr) {
        super("savings", apr, 0);
    }

    @Override
    public boolean isValidDeposit(double amount) {
        return amount >= 0 && amount <= 2500;
    }

    @Override
    public boolean isValidWithdraw(double amount, int currentMonth) {
        return amount >= 0 && amount <= 1000 && currentMonth >= this.withdrawHoldUntil;
    }

    @Override
    public void setInitialWithdrawHold(int currentMonth) {
        this.withdrawHoldUntil = -1;
    }

    @Override
    public void updateWithdrawHold(int currentMonth) {
        this.withdrawHoldUntil = currentMonth + 1;
    }
}
