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


