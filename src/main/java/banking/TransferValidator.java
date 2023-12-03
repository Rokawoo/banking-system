package banking;

class TransferValidator extends CommandValidatorBase {
    public TransferValidator(Bank bank) {
        super(bank);
    }

    @Override
    public boolean validate(String[] commandData) {
        return false;
    }}