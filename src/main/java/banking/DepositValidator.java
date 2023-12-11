package banking;

public class DepositValidator extends CommandValidatorBase {
    public DepositValidator(Bank bank) {
        super(bank);
    }

    @Override
    public boolean validate(String[] commandData) {
        if (commandData.length != 3) {
            return false;
        }

        String accountId = commandData[1];
        String balanceToDepositStr = commandData[2];

        if (!ValidationUtils.validateAccountAndBalance(accountId, balanceToDepositStr, bank)) {
            return false;
        }

        Account account = bank.retrieveAccount(accountId);

        if (account == null) {
            return false;
        }

        float balanceToDeposit = Float.parseFloat(balanceToDepositStr);

        return account.isValidDeposit(balanceToDeposit);
    }
}