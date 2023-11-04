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

import java.util.Arrays;

abstract class CommandValidatorBase {
    protected Bank bank;

    public CommandValidatorBase(Bank bank) {
        this.bank = bank;
    }

    public abstract boolean validate(String[] commandData);
}