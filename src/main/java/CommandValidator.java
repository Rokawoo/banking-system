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

    protected CommandValidatorBase(Bank bank) {
        this.bank = bank;
    }

    public abstract boolean validate(String[] commandData);
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

        if (!ValidationUtils.isValidNumber(accountNumber) || !ValidationUtils.isValidFloat(aprStr)) {
            return false;
        }

        if (!ValidationUtils.isValidAccountId(accountNumber)) {
            return false;
        }

        float apr = Float.parseFloat(aprStr);

        if (ValidationUtils.accountExists(bank, accountNumber) || apr < 0 || apr > 10) {
            return false;
        }

        if (Arrays.asList("savings", "checking").contains(accountType)) {
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

        if (!ValidationUtils.isValidNumber(accountNumber) || !ValidationUtils.isValidFloat(balanceToDepositStr)) {
            return false;
        }

        if (!ValidationUtils.isValidAccountId(accountNumber) && ValidationUtils.accountExists(bank, accountNumber)) {
            return false;
        }

        if (!ValidationUtils.isValidFloat(balanceToDepositStr)) {
            return  false;
        }

        Account account = bank.retrieveAccount(accountNumber);

        if (account == null) {
            return false;
        }

        float balanceToDeposit = Float.parseFloat(balanceToDepositStr);

        if ("savings".equals(account.getType()) && (balanceToDeposit >= 0) && (balanceToDeposit <= 2500)) {
            return true;
        } else if ("checking".equals(account.getType()) && (balanceToDeposit >= 0) && (balanceToDeposit <= 1000)) {
            return true;
        }

        return false;
    }
}

class ValidationUtils {
    public static boolean isValidNumber(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isValidFloat(String str) {
        try {
            Float.parseFloat(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isValidAccountId(String accountId) {
        return accountId.matches("\\d{8}"); // Check if it's an 8-digit account ID
    }

    public static boolean accountExists(Bank bank, String accountId) {
        return bank.retrieveAccount(accountId) != null;
    }
}
