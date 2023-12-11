package banking;

import java.util.HashMap;
import java.util.Map;

public class CommandProcessor {
    private final Map<String, CommandProcessorBase> commandProcessors = new HashMap<>();

    public CommandProcessor(Bank bank) {
        commandProcessors.put("create", new CreateProcessor(bank));
        commandProcessors.put("deposit", new DepositProcessor(bank));
        commandProcessors.put("withdraw", new WithdrawProcessor(bank));
        commandProcessors.put("transfer", new TransferProcessor(bank));
        commandProcessors.put("pass", new PassTimeProcessor(bank));
    }

    public void process(String commandToProcess) {
        String[] parts = commandToProcess.split("\\s+");
        String action = parts[0].toLowerCase();

        CommandProcessorBase Processor = commandProcessors.get(action);

        if (Processor != null) {
            Processor.process(parts);
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
