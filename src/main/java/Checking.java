public class Checking extends Account{
    public Checking(double apr) {
        super("checking", apr, 0);
    }

    @Override
    public boolean isValidDeposit(float amount) {
        return amount >= 0 && amount <= 1000;
    }
}
