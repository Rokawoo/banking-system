package banking;

import java.util.HashMap;
import java.util.Map;

public class Bank {

    private Map<String, Account> accounts;
    private int month;

    Bank() {
        this.accounts = new HashMap<>();
        this.month = 0;
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

    public boolean passTime(int monthAmount) {
        if (monthAmount >= 1 && monthAmount <= 60) {
            this.month += monthAmount;
            return true;
    } else {
            return false;
        }
    }

    public int getTime() {return this.month;}
}