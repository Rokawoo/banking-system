public class CD extends Account{
    public CD(double apr, double balance) {
        super("cd", apr, balance);
    }

    @Override
    public boolean isValidDeposit(float amount) {
        return false;
    }
}
