package banking;

class CreateValidator extends CommandValidatorBase {
    public CreateValidator(Bank bank) {
        super(bank);
    }

    @Override
    public boolean validate(String[] commandData) {
        boolean isValid = false;

        if (commandData.length == 4 || commandData.length == 5) {
            String accountType = commandData[1].toLowerCase();
            String accountId = commandData[2];
            String aprStr = commandData[3];

            if (ValidationUtils.isValidInt(accountId) && ValidationUtils.isValidFloat(aprStr)) {
                if (ValidationUtils.isValidAccountId(accountId)) {
                    float apr = Float.parseFloat(aprStr);

                    if (!ValidationUtils.accountExists(bank, accountId) && apr >= 0 && apr <= 10) {
                        if ((accountType.equals("savings") || accountType.equals("checking")) && commandData.length == 4) {
                            isValid = true;
                        } else if (accountType.equals("cd") && commandData.length == 5) {
                            String cdInitBalanceStr = commandData[4];

                            if (ValidationUtils.isValidFloat(cdInitBalanceStr)) {
                                float cdInitBalance = Float.parseFloat(cdInitBalanceStr);

                                if (cdInitBalance >= 1000 && cdInitBalance <= 10000) {
                                    isValid = true;
                                }
                            }
                        }
                    }
                }
            }
        }

        return isValid;
    }
}
