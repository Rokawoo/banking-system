package banking;

public class Checking extends Account{
    public Checking(double apr) {
        super("checking", apr, 0);
    }

    @Override
    public boolean isValidDeposit(float amount) {
        return amount >= 0 && amount <= 1000;
    }

    @Override
    public boolean isValidWithdraw(float amount, int currentMonth) {
        return amount >= 0 && amount <= 400 && currentMonth >= this.withdrawHoldUntil;
    }

    @Override
    public void updateWithdrawHold(int currentMonth) {
        return;
    }
}
