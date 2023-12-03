package banking;

public class Savings extends Account{
    public Savings(double apr) {
        super("savings", apr, 0);
    }

    @Override
    public boolean isValidDeposit(float amount) {
        return amount >= 0 && amount <= 2500;
    }
}
