package banking;

class CreateValidator extends CommandValidatorBase {
    public CreateValidator(Bank bank) {
        super(bank);
    }

    @Override
    public boolean validate(String[] commandData) {
        if (commandData.length != 4 && commandData.length != 5) {
            return false;
        }

        String accountType = commandData[1].toLowerCase();
        String accountId = commandData[2];
        String aprStr = commandData[3];

        if (!ValidationUtils.isValidNumber(accountId) || !ValidationUtils.isValidFloat(aprStr)) {
            return false;
        }

        if (!ValidationUtils.isValidAccountId(accountId)) {
            return false;
        }

        float apr = Float.parseFloat(aprStr);

        if (ValidationUtils.accountExists(bank, accountId) || apr < 0 || apr > 10) {
            return false;
        }

        if (accountType.equals("savings") || accountType.equals("checking")) {
            return commandData.length == 4;
        } else if (accountType.equals("cd") && commandData.length == 5) {
            String cdInitBalanceStr = commandData[4];

            if (!ValidationUtils.isValidFloat(cdInitBalanceStr)) {
                return false;
            }

            float cdInitBalance = Float.parseFloat(cdInitBalanceStr);

            return cdInitBalance >= 1000 && cdInitBalance <= 10000;
        }

        return false;
    }
}