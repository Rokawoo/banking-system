import java.util.HashMap;
import java.util.Map;

public class CommandProcessor {
    private final Map<String, CommandProcessorBase> commandProcessors = new HashMap<>();

    public CommandProcessor(Bank bank) {
        commandProcessors.put("create", new CreateProcessor(bank));
        commandProcessors.put("deposit", new DepositProcessor(bank));
    }

    public void process(String commandToprocess) {
        String[] parts = commandToprocess.split("\\s+");
        String action = parts[0].toLowerCase();

        CommandProcessorBase Processor = commandProcessors.get(action);

        if (Processor != null) {
            boolean result = Processor.process(parts);
            if (!result) {
                System.out.println("Process failed for action: " + action);
            }
        } else {
            System.out.println("Action not found: " + action);
        }
    }
}

class CommandProcessorBase {
    protected Bank bank;

    protected CommandProcessorBase(Bank bank) {
        this.bank = bank;
    }

    public boolean process(String[] commandData) {
        return false;
    }
}
