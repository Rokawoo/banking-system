package banking;

public class Checking extends Account {
    public Checking(double apr) {
        super("checking", apr, 0);
    }

    @Override
    public boolean isValidDeposit(double amount) {
        return amount >= 0 && amount <= 1000;
    }

    @Override
    public boolean isValidWithdraw(double amount, int currentMonth) {
        return amount >= 0 && amount <= 400 && currentMonth >= this.withdrawHoldUntil;
    }

    @Override
    public void setInitialWithdrawHold(int currentMonth) {
        this.withdrawHoldUntil = -1;
    }

    @Override
    public void updateWithdrawHold(int currentMonth) {
        // The updateWithdrawHold method is intentionally left empty for functionality
        // Checking accounts do not have withdraw holds
    }
}
