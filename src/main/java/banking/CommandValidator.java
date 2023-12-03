package banking;

import java.util.HashMap;
import java.util.Map;

public class CommandValidator {
    private final Map<String, CommandValidatorBase> commandValidators = new HashMap<>();
    private final Bank bank;

    public CommandValidator(Bank bank) {
        this.bank = bank;
        commandValidators.put("create", new CreateValidator(bank));
        commandValidators.put("deposit", new DepositValidator(bank));
        commandValidators.put("withdraw", new WithdrawValidator(bank));
        commandValidators.put("transfer", new TransferValidator(bank));
    }

    public boolean validate(String commandToValidate) {
        String[] commandData = commandToValidate.split("\\s+");
        String action = commandData[0].toLowerCase();

        CommandValidatorBase validator = commandValidators.get(action);

        if (validator != null) {
            boolean result = validator.validate(commandData);
            return result;
        } else {
            return false;
        }
    }
}

class CommandValidatorBase {
    protected Bank bank;

    protected CommandValidatorBase(Bank bank) {
        this.bank = bank;
    }

    public boolean validate(String[] commandData) {
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
