import java.util.ArrayList;
import java.util.List;

public class CommandHistory {
    private CommandValidator commandValidator;
    private List<String> commandHistory = new ArrayList<>();

    public CommandHistory(Bank bank) {
        commandValidator = new CommandValidator(bank);
    }

    public boolean storeCommand(String command) {
        boolean isValidCommand = commandValidator.validate(command);

        if (isValidCommand) {
            return false;
        }
        commandHistory.add(command);
        return true;
    }

    public List<String> retrieveAllStored() {
        return commandHistory;
    }
}
