package banking;

class TransferProcessor extends CommandProcessorBase {
    public TransferProcessor(Bank bank) {
        super(bank);
    }

    @Override
    public boolean process(String[] commandData) {
        String fromAccountId = commandData[1];
        String toAccountId = commandData[2];
        float balanceToTransferFloat = Float.parseFloat(commandData[3]);
        double actualAmountWithdraw = bank.bankWithdraw(fromAccountId, balanceToTransferFloat);
        bank.bankUpdateWithdrawHold(fromAccountId);
        bank.bankDeposit(toAccountId, actualAmountWithdraw);
        return true;
    }
}