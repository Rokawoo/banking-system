package banking;

class DepositProcessor extends CommandProcessorBase {
    public DepositProcessor(Bank bank) {
        super(bank);
    }

    @Override
    public void process(String[] commandData) {
        String accountId = commandData[1];
        float balanceToDepositFloat = Float.parseFloat(commandData[2]);
        bank.bankDeposit(accountId, balanceToDepositFloat);
    }
}