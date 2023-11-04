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

    public CommandValidatorBase(Bank bank) {
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
        float apr;

        try {
            Integer.parseInt(accountNumber);
            apr = Float.parseFloat(commandData[3]);
        } catch (NumberFormatException e) {
            return false;
        }

        if (accountNumber.length() != 8 || bank.retrieveAccount(accountNumber) != null || apr < 0 || apr > 10) {
            return false;
        }

        if (Arrays.asList("savings", "checking").contains(accountType)) {
            return commandData.length == 4;
        } else if (accountType.equals("cd") && commandData.length == 5) {
            try {
                float cdInitBalance = Float.parseFloat(commandData[4]);
                return cdInitBalance >= 1000 && cdInitBalance <= 10000;
            } catch (NumberFormatException e) {
                return false;
            }
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
        float balanceToDeposit;

        try {
            Integer.parseInt(accountNumber);
            balanceToDeposit = Float.parseFloat(commandData[2]);
        } catch (NumberFormatException e) {
            return false;
        }

        Account account = bank.retrieveAccount(accountNumber);

        if (account == null || accountNumber.length() != 8) {
            return false;
        }

        if (account instanceof Savings && balanceToDeposit >= 0 && balanceToDeposit <= 2500) {
            return true;
        } else if (account instanceof Checking && balanceToDeposit >= 0 && balanceToDeposit <= 1000) {
            return true;
        }

        return false;
    }
}