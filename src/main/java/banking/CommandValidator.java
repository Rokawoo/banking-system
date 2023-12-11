package banking;

import java.util.HashMap;
import java.util.Map;

public class CommandValidator {
    private final Map<String, CommandValidatorBase> commandValidators = new HashMap<>();


    public CommandValidator(Bank bank) {
        commandValidators.put("create", new CreateValidator(bank));
        commandValidators.put("deposit", new DepositValidator(bank));
        commandValidators.put("withdraw", new WithdrawValidator(bank));
        commandValidators.put("transfer", new TransferValidator(bank));
        commandValidators.put("pass", new PassTimeValidator(bank));
    }

    public boolean validate(String commandToValidate) {
        String[] commandData = commandToValidate.split("\\s+");
        String action = commandData[0].toLowerCase();

        CommandValidatorBase validator = commandValidators.get(action);

        if (validator != null) {
            return validator.validate(commandData);
        } else {
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

class ValidationUtils {
    private ValidationUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static boolean isValidInt(String str) {
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
