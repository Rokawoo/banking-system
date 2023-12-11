package banking;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Bank {

    private final Map<String, Account> accounts;
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

    public double bankWithdraw(String id, double withdrawAmount) {
        double amountWithdrawn = accounts.get(id).withdraw(withdrawAmount);
        return amountWithdrawn;
    }

    public void bankUpdateWithdrawHold(String id) {
        accounts.get(id).updateWithdrawHold(this.month);
    }

    public int getTime() {
        return this.month;
    }

    public void passTime(int monthAmount) {
        this.month += monthAmount;
    }

    public void closeZeroBalanceAccounts() {
        Iterator<Map.Entry<String, Account>> iterator = accounts.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<String, Account> entry = iterator.next();
            Account account = entry.getValue();

            if (account.getBalance() == 0) {
                iterator.remove();
            }
        }
    }

    public void applyMinimumBalanceFee() {
        for (Map.Entry<String, Account> entry : accounts.entrySet()) {
            Account account = entry.getValue();

            if (account.getBalance() < 100) {
                account.withdraw(25);
            }
        }
    }

    public void accrueAPR(int monthsPassed) {
        for (Map.Entry<String, Account> entry : accounts.entrySet()) {
            Account account = entry.getValue();
            double apr = account.getApr() / 100;

            double totalInterest = 0.0;

            if (account.getType().equals("cd")) {
                for (int month = 0; month < monthsPassed; ++month) {
                    for (int i = 0; i < 4; ++i) {
                        double monthlyRate = apr / 12;
                        double interest = account.getBalance() * monthlyRate;
                        totalInterest += interest;
                    }
                }
            } else {
                for (int month = 0; month < monthsPassed; ++month) {
                    double monthlyRate = apr / 12;
                    double interest = account.getBalance() * monthlyRate;
                    totalInterest += interest;
                }
            }

            account.deposit(totalInterest);
        }
    }
}