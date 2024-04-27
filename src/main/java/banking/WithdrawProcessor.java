package banking;

class WithdrawProcessor extends CommandProcessorBase {
    public WithdrawProcessor(Bank bank) {
        super(bank);
    }

    @Override
    public void process(String[] commandData) {
        String accountId = commandData[1];
        float balanceToDepositFloat = Float.parseFloat(commandData[2]);
        bank.bankWithdraw(accountId, balanceToDepositFloat);
        bank.bankUpdateWithdrawHold(accountId);
    }
}