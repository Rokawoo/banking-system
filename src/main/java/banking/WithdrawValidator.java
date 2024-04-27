package banking;

public class WithdrawValidator extends CommandValidatorBase {
    public WithdrawValidator(Bank bank) {
        super(bank);
    }

    @Override
    public boolean validate(String[] commandData) {
        if (commandData.length != 3) {
            return false;
        }

        String accountId = commandData[1];
        String balanceToWithdrawStr = commandData[2];

        if (!ValidationUtils.validateAccountAndBalance(accountId, balanceToWithdrawStr, bank)) {
            return false;
        }

        Account account = bank.retrieveAccount(accountId);

        if (account == null) {
            return false;
        }

        float balanceToWithdraw = Float.parseFloat(balanceToWithdrawStr);

        return account.isValidWithdraw(balanceToWithdraw, bank.getTime());
    }
}