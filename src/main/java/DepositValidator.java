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

        if (!ValidationUtils.isValidNumber(accountId) || !ValidationUtils.isValidFloat(balanceToDepositStr)) {
            return false;
        }

        if (!ValidationUtils.isValidAccountId(accountId) && ValidationUtils.accountExists(bank, accountId)) {
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