import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Bank {
    private static AtomicInteger nextID = new AtomicInteger(0);
    private Map<String, Account> accounts;

    public static void resetNextID() {
        nextID.set(0);
    }

    Bank() {
        accounts = new HashMap<>();
    }

    public void addAccount(Account account) {
        accounts.put(String.format("%08d", nextID.incrementAndGet()), account);
    }

    public int getNumberOfAccounts() {
        return accounts.size();
    }

    public Account retrieveAccount(String id) {
        return accounts.get(id);
    }

    public void bankDeposit(String id, double depositAmount) {
        accounts.get(id).deposit(depositAmount);
    }

    public void bankWithdraw(String id, double withdrawAmount) {
        accounts.get(id).withdraw(withdrawAmount);
    }
}
