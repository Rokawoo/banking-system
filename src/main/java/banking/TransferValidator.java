package banking;

class TransferValidator extends CommandValidatorBase {
    public TransferValidator(Bank bank) {
        super(bank);
    }

    @Override
    public boolean validate(String[] commandData) {
        if (commandData.length != 4) {
            return false;
        }

        String fromAccountId = commandData[1];
        String toAccountId = commandData[2];
        String balanceToTransferStr = commandData[3];

        if (!ValidationUtils.isValidInt(fromAccountId) || !ValidationUtils.isValidInt(toAccountId) || !ValidationUtils.isValidFloat(balanceToTransferStr)) {
            return false;
        }

        if (!ValidationUtils.isValidAccountId(fromAccountId) || !ValidationUtils.isValidAccountId(toAccountId)) {
            return false;
        }

        if (fromAccountId.equals(toAccountId)) {
            return false;
        }

        if (!ValidationUtils.accountExists(bank, fromAccountId) || !ValidationUtils.accountExists(bank, toAccountId)) {
            return false;
        }

        Account fromAccount = bank.retrieveAccount(fromAccountId);
        Account toAccount = bank.retrieveAccount(toAccountId);

        if (fromAccount == null || toAccount == null || fromAccount.getType().equals("cd") || toAccount.getType().equals("cd")) {
            return false;
        }

        float amount = Float.parseFloat(balanceToTransferStr);

        if (amount < 0) {
            return false;
        }

        return fromAccount.isValidWithdraw(amount, bank.getTime()) && toAccount.isValidDeposit(amount);
    }
}