import java.util.HashMap;
import java.util.Map;

public class Bank {

    private Map<String, Account> accounts;

    Bank() {
        accounts = new HashMap<>();
    }

    public void addAccount(String id, Account account) {
        accounts.put(id, account);
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