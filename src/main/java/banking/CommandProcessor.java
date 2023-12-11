package banking;

import java.util.HashMap;
import java.util.Map;

public class CommandProcessor {
    private final Map<String, CommandProcessorBase> commandProcessors = new HashMap<>();

    public CommandProcessor(Bank bank) {
        initializeProcessors(bank, commandProcessors);
    }

    private void initializeProcessors(Bank bank, Map<String, CommandProcessorBase> processors) {
        processors.put("pass", new PassTimeProcessor(bank));
        processors.put("deposit", new DepositProcessor(bank));
        processors.put("create", new CreateProcessor(bank));
        processors.put("withdraw", new WithdrawProcessor(bank));
        processors.put("transfer", new TransferProcessor(bank));
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
