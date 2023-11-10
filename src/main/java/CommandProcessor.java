import java.util.HashMap;
import java.util.Map;

public class CommandProcessor {
    private Map<String, CommandProcessorBase> commandProcessors = new HashMap<>();
    private Bank bank;

    public CommandProcessor(Bank bank) {
        this.bank = bank;
        commandProcessors.put("create", new CreateProcessor(bank));
        commandProcessors.put("deposit", new DepositProcessor(bank));
    }

    public boolean process(String commandToprocess) {
        String[] parts = commandToprocess.split("\\s+");
        String action = parts[0].toLowerCase();

        CommandProcessorBase Processor = commandProcessors.get(action);

        if (Processor != null) {
            boolean result = Processor.process(parts);
            if (!result) {
                System.out.println("Process failed for action: " + action);
            }
            return result;
        } else {
            System.out.println("Action not found: " + action);
            return false;
        }
    }
}

abstract class CommandProcessorBase {
    protected Bank bank;

    protected CommandProcessorBase(Bank bank) {
        this.bank = bank;
    }

    public abstract boolean process(String[] commandData);
}

class CreateProcessor extends CommandProcessorBase {
    public CreateProcessor(Bank bank) {
        super(bank);
    }

    @Override
    public boolean process(String[] commandData) {
        String accountType = commandData[1].toLowerCase();
        String accountId = commandData[2];
        float aprFloat = Float.parseFloat(commandData[3]);

        Account accountToCreate;
        if (accountType == "savings") {
            accountToCreate = new Savings(aprFloat);
            bank.addAccount(accountId, accountToCreate);
        } else if (accountType == "checking"){
            accountToCreate = new Checking(aprFloat);
            bank.addAccount(accountId, accountToCreate);
        } else {
            float cdInitBalanceFloat = Float.parseFloat(commandData[4]);
            accountToCreate = new CD(aprFloat, cdInitBalanceFloat);
            bank.addAccount(accountId, accountToCreate);
        }
        return true;
    }
}

class DepositProcessor extends CommandProcessorBase {
    public DepositProcessor(Bank bank) {
        super(bank);
    }

    @Override
    public boolean process(String[] commandData) {
        String accountId = commandData[1];
        float balanceToDepositFloat = Float.parseFloat(commandData[2]);
        bank.bankDeposit(accountId, balanceToDepositFloat);
    return true;
    }
}