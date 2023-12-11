package banking;

class CreateProcessor extends CommandProcessorBase {
    public CreateProcessor(Bank bank) {
        super(bank);
    }

    @Override
    public boolean process(String[] commandData) {
        String accountType = commandData[1].toLowerCase();
        String accountId = commandData[2];
        float aprFloat = Float.parseFloat(commandData[3]);

        Account accountToCreate;
        if (accountType.equals("savings")) {
            accountToCreate = new Savings(aprFloat);
            bank.addAccount(accountId, accountToCreate);
        } else if (accountType.equals("checking")) {
            accountToCreate = new Checking(aprFloat);
            bank.addAccount(accountId, accountToCreate);
        } else {
            float cdInitBalanceFloat = Float.parseFloat(commandData[4]);
            accountToCreate = new CD(aprFloat, cdInitBalanceFloat);
            bank.addAccount(accountId, accountToCreate);
        }
        accountToCreate.setInitialWithdrawHold(bank.getTime());
        return true;
    }
}