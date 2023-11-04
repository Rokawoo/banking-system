import java.util.HashMap;
import java.util.Map;

@FunctionalInterface
interface ValidateMethod {
    boolean execute(String[] commandData);
}

public class CommandValidator {
    private Map<String, ValidateMethod> commandDictionary;
    private Bank bank;

    public CommandValidator(Bank bank) {
        this.bank = bank;
        commandDictionary = new HashMap<>();
        setupCommandDictionary();
    }

    public boolean validate(String commandToValidate) {
        String[] parts = commandToValidate.split("\\s+");
        String action = parts[0].toLowerCase();

        ValidateMethod validationMethod = commandDictionary.get(action);

        if (validationMethod != null) {
            boolean result = validationMethod.execute(parts);
            return result;
        } else {
            System.out.println("Action not found: " + action);
            return false;
        }
    }

    private void setupCommandDictionary() {
        commandDictionary.put("create", (String[] commandData) -> {
            if (commandData.length != 4 && commandData.length != 5) {
                return false;
            }

            String accountType = commandData[1].toLowerCase();
            String accountNumber = commandData[2];
            float apr;

            try {
                Integer.parseInt(commandData[2]);
                apr = Float.parseFloat(commandData[3]);
            } catch (NumberFormatException e) {
                return false;
            }

            if (accountNumber.length() != 8 || bank.retrieveAccount(accountNumber) != null ||
                    apr < 0 || apr > 10) {
                return false;
            }

            if (accountType.equals("savings") || accountType.equals("checking")) {
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
        });

        commandDictionary.put("deposit", (String[] commandData) -> {
            if (commandData.length != 3) {
                return false;
            }

            String accountNumber = commandData[1];
            float balanceToDeposit;

            try {
                Integer.parseInt(commandData[1]);
                balanceToDeposit = Float.parseFloat(commandData[2]);
            } catch (NumberFormatException e) {
                return false;
            }

            Account account = bank.retrieveAccount(accountNumber);

            if (account == null || accountNumber.length() != 8) {
                return false;
            }

            if (account.getClass() == Savings.class && balanceToDeposit >= 0 && balanceToDeposit <= 2500) {
                return true;
            } else if (account.getClass() == Checking.class && balanceToDeposit >= 0 && balanceToDeposit <= 1000) {
                return true;
            }

            return false;
        });
    }
}

