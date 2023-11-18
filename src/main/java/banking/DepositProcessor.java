package banking;

class DepositProcessor extends CommandProcessorBase {
    public DepositProcessor(Bank bank) {
        super(bank);
    }

    @Override
    public boolean process(String[] commandData) {
        String accountId = commandData[1];
        float balanceToDepositFloat = Float.parseFloat(commandData[2]);
        bank.bankDeposit(accountId, balanceToDepositFloat);
        return true;
    }
}