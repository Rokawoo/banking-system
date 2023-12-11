package banking;

import java.util.List;

public class MasterControl {
    private final CommandValidator commandValidator;
    private final CommandProcessor commandProcessor;
    private final CommandHistory commandHistory;

    public MasterControl(CommandValidator commandValidator, CommandProcessor commandProcessor,
                         CommandHistory commandHistory) {
        this.commandValidator = commandValidator;
        this.commandProcessor = commandProcessor;
        this.commandHistory = commandHistory;
    }

    public List<String> start(List<String> input) {
        for (String command : input) {
            if (commandValidator.validate(command)) {
                //store command to account
                commandProcessor.process(command);
            } else {
                commandHistory.storeInvalidCommand(command);
            }
        }
        return commandHistory.retrieveAllStored();
    }
}
