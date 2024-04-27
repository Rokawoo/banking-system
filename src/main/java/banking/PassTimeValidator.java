package banking;

public class PassTimeValidator extends CommandValidatorBase {
    public PassTimeValidator(Bank bank) {
        super(bank);
    }

    @Override
    public boolean validate(String[] commandData) {
        if (commandData.length != 2) {
            return false;
        }

        if (!ValidationUtils.isValidInt(commandData[1])) {
            return false;
        }

        int monthAmount = Integer.parseInt(commandData[1]);
        return monthAmount >= 1 && monthAmount <= 60;
    }
}