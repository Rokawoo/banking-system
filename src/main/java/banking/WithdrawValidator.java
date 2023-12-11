package banking;

public class WithdrawValidator extends CommandValidatorBase {
    public WithdrawValidator(Bank bank) {
        super(bank);
    }

    @Override
    public boolean validate(String[] commandData) {
        boolean isValid = commandData.length == 3;
        String accountId = commandData[1];
        String balanceToWithdrawStr = commandData[2];

        if (!ValidationUtils.isValidInt(accountId) || !ValidationUtils.isValidFloat(balanceToWithdrawStr)) {
            isValid = false;
        }

        if (!ValidationUtils.isValidAccountId(accountId) && ValidationUtils.accountExists(bank, accountId)) {
            isValid = false;
        }

        Account account = bank.retrieveAccount(accountId);

        if (account == null) {
            return false;
        }

        float balanceToWithdraw = Float.parseFloat(balanceToWithdrawStr);

        if (!account.isValidWithdraw(balanceToWithdraw, bank.getTime())) {
            isValid = false;
        }

        return isValid;
    }
}