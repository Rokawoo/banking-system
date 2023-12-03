package banking;

import java.util.ArrayList;
import java.util.List;

public class CommandHistory {
    private final CommandValidator commandValidator;
    private final List<String> commandHistoryList = new ArrayList<>();
    private final Bank bank;

    public CommandHistory(Bank bank) {
        this.bank = bank;
        commandValidator = new CommandValidator(bank);
    }

    public boolean storeCommand(String command) {
        boolean isValidCommand = commandValidator.validate(command);

        if (isValidCommand) {
            return false;
        }
        commandHistoryList.add(command);
        return true;
    }

    public List<String> retrieveAllStored() {
        return commandHistoryList;
    }
}
