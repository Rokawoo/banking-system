import java.util.ArrayList;
import java.util.List;

public class CommandHistory {
    private final CommandValidator commandValidator;
    private final List<String> commandHistory = new ArrayList<>();

    public CommandHistory() {
        commandValidator = new CommandValidator(new Bank());
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
