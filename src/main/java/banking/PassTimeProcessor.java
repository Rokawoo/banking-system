package banking;

class PassTimeProcessor extends CommandProcessorBase {
    public PassTimeProcessor(Bank bank) {
        super(bank);
    }

    @Override
    public boolean process(String[] commandData) {
        int monthAmount = Integer.parseInt(commandData[1]);
        bank.passTime(monthAmount);

        bank.closeZeroBalanceAccounts();
        bank.applyMinimumBalanceFee();
        bank.accrueAPR(monthAmount);
        return true;
    }
}