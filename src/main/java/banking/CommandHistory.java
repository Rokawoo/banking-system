package banking;

import java.util.ArrayList;
import java.util.List;

public class CommandHistory {
    private final CommandValidator commandValidator;
    private final List<String> invalidCommandHistoryList = new ArrayList<>();
    private final Bank bank;

    public CommandHistory(Bank bank) {
        this.bank = bank;
        commandValidator = new CommandValidator(bank);
    }

    public boolean storeValidTransactionCommand(String command) {
        if (!commandValidator.validate(command)) {
            return false;
        }

        String[] commandData = command.split("\\s+");
        String action = commandData[0].toLowerCase();

        if (action.equals("deposit") || action.equals("withdraw")) {
            String id = commandData[1];
            bank.retrieveAccount(id).addCommandToTransactionHistory(command);
        } else if (action.equals("transfer")) {
            String fromId = commandData[1];
            String toId = commandData[2];
            bank.retrieveAccount(fromId).addCommandToTransactionHistory(command);
            bank.retrieveAccount(toId).addCommandToTransactionHistory(command);
        }

        return true;
    }

    public boolean storeInvalidCommand(String command) {
        if (commandValidator.validate(command)) {
            return false;
        }

        invalidCommandHistoryList.add(command);
        return true;
    }

    public List<String> retrieveAllStored() {
        List<String> allStoredData = new ArrayList<>();

        List<String> accountInformation = bank.printOpenAccountInformation();
        allStoredData.addAll(accountInformation);

        allStoredData.addAll(invalidCommandHistoryList);

        return allStoredData;
    }

}
