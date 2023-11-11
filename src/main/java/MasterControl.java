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
            System.out.println(command);
            System.out.println(commandValidator.validate(command));
            if (commandValidator.validate(command)) {
                System.out.println("pro");
                commandProcessor.process(command);
            } else {
                System.out.println("sto");
                commandHistory.storeCommand(command);
            }
        }
        System.out.println(commandHistory.retrieveAllStored());
        return commandHistory.retrieveAllStored();
    }
}
