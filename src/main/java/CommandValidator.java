import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CommandValidator {
    private Map<String, CommandValidatorBase> commandValidators = new HashMap<>();
    private Bank bank;

    public CommandValidator(Bank bank) {
        this.bank = bank;
        commandValidators.put("create", new CreateValidator(bank));
        commandValidators.put("deposit", new DepositValidator(bank));
    }

    public boolean validate(String commandToValidate) {
        String[] parts = commandToValidate.split("\\s+");
        String action = parts[0].toLowerCase();

        CommandValidatorBase validator = commandValidators.get(action);

        if (validator != null) {
            boolean result = validator.validate(parts);
            if (!result) {
                System.out.println("Validation failed for action: " + action);
            }
            return result;
        } else {
            System.out.println("Action not found: " + action);
            return false;
        }
    }
}

abstract class CommandValidatorBase {
    protected Bank bank;
    protected ValidationUtils validationUtils;

    public CommandValidatorBase(Bank bank) {
        this.bank = bank;
        this.validationUtils = new ValidationUtils();
    }

    public abstract boolean validate(String[] commandData);
}

class ValidationUtils {
    public boolean isValidNumber(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean isValidFloat(String str) {
        try {
            Float.parseFloat(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean isValidAccountId(String accountId) {
        return accountId.matches("\\d{8}"); // Check if it's an 8-digit account ID
    }

    public boolean accountExists(Bank bank, String accountId) {
        return bank.retrieveAccount(accountId) != null;
    }

}

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
        String accountNumber = commandData[2];
        String aprStr = commandData[3];

        if (!validationUtils.isValidNumber(accountNumber) || !validationUtils.isValidFloat(aprStr)) {
            return false;
        }

        if (!validationUtils.isValidAccountId(accountNumber)) {
            return false;
        }

        float apr = Float.parseFloat(aprStr);

        if (validationUtils.accountExists(bank, accountNumber) || apr < 0 || apr > 10) {
            return false;
        }

        if (Arrays.asList("savings", "checking").contains(accountType)) {
            return commandData.length == 4;
        } else if (accountType.equals("cd") && commandData.length == 5) {
            String cdInitBalanceStr = commandData[4];

            if (!validationUtils.isValidFloat(cdInitBalanceStr)) {
                return false;
            }

            float cdInitBalance = Float.parseFloat(cdInitBalanceStr);

            return cdInitBalance >= 1000 && cdInitBalance <= 10000;
        }

        return false;
    }
}

class DepositValidator extends CommandValidatorBase {
    public DepositValidator(Bank bank) {
        super(bank);
    }

    @Override
    public boolean validate(String[] commandData) {
        if (commandData.length != 3) {
            return false;
        }

        String accountNumber = commandData[1];
        String balanceToDepositStr = commandData[2];

        if (!validationUtils.isValidNumber(accountNumber) || !validationUtils.isValidFloat(balanceToDepositStr)) {
            return false;
        }

        if (!validationUtils.isValidAccountId(accountNumber) && validationUtils.accountExists(bank, accountNumber)) {
            return false;
        }

        Account account = bank.retrieveAccount(accountNumber);

        if (account == null) {
            return false;
        }

        if (account instanceof Savings && Float.parseFloat(balanceToDepositStr) >= 0 && Float.parseFloat(balanceToDepositStr) <= 2500) {
            return true;
        } else if (account instanceof Checking && Float.parseFloat(balanceToDepositStr) >= 0 && Float.parseFloat(balanceToDepositStr) <= 1000) {
            return true;
        }

        return false;
    }
}
