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

    public void passTime(int monthAmount) {this.month += monthAmount;}
    public int getTime() {return this.month;}

    // del accs with < 100 bal
    // apr calculations
    // mov validation to val class and make processsor
}