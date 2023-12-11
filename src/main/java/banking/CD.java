package banking;

public class CD extends Account {
    public CD(double apr, double balance) {
        super("cd", apr, balance);
    }

    @Override
    public boolean isValidDeposit(float amount) {
        return false;
    }

    @Override
    public boolean isValidWithdraw(float amount, int currentMonth) {
        return amount == this.getBalance() && currentMonth >= this.withdrawHoldUntil;
    }

    @Override
    public void setInitialWithdrawHold(int currentMonth) {
        this.withdrawHoldUntil = currentMonth + 12;
    }

    @Override
    public void updateWithdrawHold(int currentMonth) {
        // The updateWithdrawHold method is intentionally left empty for functionality
        // CD accounts have withdraw holds 12 months after their creation
    }
}
