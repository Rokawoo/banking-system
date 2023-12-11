package banking;

class TransferValidator extends CommandValidatorBase {
    public TransferValidator(Bank bank) {
        super(bank);
    }

    @Override
    public boolean validate(String[] commandData) {
        boolean isValid = commandData.length == 4;
        String fromAccountId = commandData[1];
        String toAccountId = commandData[2];
        String balanceToTransferStr = commandData[3];

        if (!ValidationUtils.isValidInt(fromAccountId) || !ValidationUtils.isValidInt(toAccountId) || !ValidationUtils.isValidFloat(balanceToTransferStr)) {
            isValid = false;
        }

        if (!ValidationUtils.isValidAccountId(fromAccountId) || !ValidationUtils.isValidAccountId(toAccountId)) {
            isValid = false;
        }

        if (fromAccountId.equals(toAccountId)) {
            isValid = false;
        }

        if (!ValidationUtils.accountExists(bank, fromAccountId) || !ValidationUtils.accountExists(bank, toAccountId)) {
            isValid = false;
        }

        Account fromAccount = bank.retrieveAccount(fromAccountId);
        Account toAccount = bank.retrieveAccount(toAccountId);

        if (fromAccount == null || toAccount == null || fromAccount.getType().equals("cd") || toAccount.getType().equals("cd")) {
            isValid = false;
        }

        float amount = Float.parseFloat(balanceToTransferStr);

        if (amount < 0 || (!fromAccount.isValidWithdraw(amount, bank.getTime()) && !toAccount.isValidDeposit(amount))) {
            isValid = false;
        }

        return isValid;
    }
}